package component.arquilliancourse.greetingservice;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.net.URL;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith( Arquillian.class )
public class GreetingServiceEndpointTest extends AbstractTest {

    @Deployment
    public static WebArchive createDeployment () {
        return createBasicDeployment()
                .addClasses(
                    //Models
                    Greeting.class
                )
                .addClasses(
                    //Resources
                    GreetingResource.class
                )
                .addClasses(
                    //Services
                )
                .addClasses(
                    //MISC
                    AbstractTest.class
                );
    }

    @ArquillianResource
    private URL deploymentUrl;

    @Test
    public void shouldReturnAllGreetingsFromDataStore(){
        //Rest-Assured

        //GIVEN + WHEN + THEN
        given()
        .when().get(deploymentUrl + "rest/greetings")
        .then()
            .statusCode( 200 )
            .contentType( "application/json" )
            .body( "greeting", notNullValue() );

        when().get(deploymentUrl + "rest/greetings")
                .prettyPrint();
    }
}
