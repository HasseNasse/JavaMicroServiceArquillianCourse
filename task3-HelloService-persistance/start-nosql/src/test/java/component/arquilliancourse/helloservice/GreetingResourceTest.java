package component.arquilliancourse.helloservice;

import component.arquilliancourse.AbstractTest;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.net.URL;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNull.notNullValue;

@RunWith( Arquillian.class )
public class GreetingResourceTest extends AbstractTest {


    @Deployment
    public static WebArchive createDeployment () {
        return createBasicDeployment()
                .addClasses(
                        //Models
                        Greeting.class
                ).addClasses(
                        //Resources
                        GreetingResource.class
                ).addClasses(
                        //Services

                ).addClasses(
                        //Misc
                        AbstractTest.class
                );
    }

    @ArquillianResource
    private URL deploymentUrl;

    @Test
    public void shouldGETallGreetingsUponRequest(){
        given()
                .when()
                .get(deploymentUrl + "rest/greetings/")
                .then()
                .body("greeting", notNullValue())
                .statusCode( 200 )
                .contentType( "application/json" );

        given()
                .when()
                .get(deploymentUrl + "rest/greetings/").prettyPrint();
    }

    @Test
    public void shouldGetGreetingGivenGreetingLanguage(){
        given()
                .queryParam( "locale", "en" )
        .when()
                .get(deploymentUrl + "rest/greetings")
        .then()
                .body( "greetingLanguage", is(equalTo( "en" )) )
                .statusCode( 200 );
    }

    @Test
    public void shouldGetGreetingGivenGreetingID(){
        given()
                .pathParam( "id", "hello" )
        .when()
                .get(deploymentUrl + "rest/greetings/{id}")
        .then()
                .contentType( "application/json" )
                .body( "greeting", is(equalTo( "hello" )) )
                .statusCode( 200 );
    }
}
