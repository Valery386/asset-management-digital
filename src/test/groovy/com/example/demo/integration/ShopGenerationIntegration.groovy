package com.example.demo.integration

import com.example.demo.DemoApplication
import com.example.demo.entities.Shop
import com.example.demo.service.DefaultShopService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles

/**
 * Created by damz on 6/5/2017.
 */
@SpringBootTest( classes = DemoApplication.class,
        webEnvironment=SpringBootTest.WebEnvironment.DEFINED_PORT,
        properties = "server.port:4000")
@ActiveProfiles(["sandbox", "h2"])
@DirtiesContext
class ShopGenerationIntegration extends AbstractIntegrationSpecification {

    @Autowired DefaultShopService defaultShopService

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
            def response = shopCreated.body
            response != null
            response.shopId == 1
            response.shopName == firstShop.shopName
            response.shopAddressNumber == firstShop.shopAddressNumber
            response.shopAddressPostCode == firstShop.shopAddressPostCode
            response.informationAboutVersion == "This shop is new, so it was created"
    }

    def "Update and find an existing shop" () {
        given:
            def updateShop = new Shop(
                    shopName: "My First Shop",
                    shopAddressNumber: 2,
                    shopAddressPostCode: 321
            )
        when:
            def foundShop = defaultShopService.findOneByName(updateShop.getShopName())
            def shopUpdated = postForEntity("${BASE_URL}/shop", updateShop, Shop.class)
        then:
            def response = shopUpdated.body
            response != null
            response.shopId == 1
            response.shopName == foundShop.shopName
            response.shopAddressNumber == foundShop.shopAddressNumber
            response.shopAddressPostCode == foundShop.shopAddressPostCode
            response.informationAboutVersion == "This shop already exist, so it was updated. This information is from the replaced shop."
    }
}
