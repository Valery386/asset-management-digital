import com.example.demo.DemoApplication
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import spock.lang.Specification

/**
 * Created by damz on 6/5/2017.
 */

@SpringBootTest( classes = DemoApplication.class,
        webEnvironment=SpringBootTest.WebEnvironment.DEFINED_PORT
        ,properties = "server.port:4000")
@ActiveProfiles(["sandbox", "mysql"])
@DirtiesContext
class ShopGenerationIntegration extends Specification {

    
}