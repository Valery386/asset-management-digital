package com.example.demo.integration

import com.example.demo.DemoApplication
import com.example.demo.entities.Shop
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles

/**
 * Created by damz on 6/5/2017.
 */
@SpringBootTest( classes = DemoApplication.class,
        webEnvironment=SpringBootTest.WebEnvironment.DEFINED_PORT
        ,properties = "server.port:4000")
@ActiveProfiles(["sandbox", "mysql"])
@DirtiesContext
class ShopGenerationIntegration extends AbstractIntegrationSpecification {

    def "Create new Shop" () {
        given:
            def firstShop = new Shop(
                    shopName: "My First Shop",
                    shopAddressNumber: 1,
                    shopAddressPostCode: 123
            )
        when:
            def shopCreated = postForEntity("${BASE_URL}/shop", firstShop, Shop.class)
        then:
            shopCreated != null
    }
}
