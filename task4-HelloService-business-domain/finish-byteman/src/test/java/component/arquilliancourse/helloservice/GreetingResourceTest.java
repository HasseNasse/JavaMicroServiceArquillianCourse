package component.arquilliancourse.helloservice;

import com.lordofthejars.nosqlunit.annotation.UsingDataSet;
import com.lordofthejars.nosqlunit.core.LoadStrategyEnum;
import com.lordofthejars.nosqlunit.mongodb.InMemoryMongoDb;
import com.lordofthejars.nosqlunit.mongodb.MongoDbRule;
import component.arquilliancourse.datastore.DAO;
import component.arquilliancourse.datastore.FakeDataStoreConnectionFactory;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import javax.inject.Inject;
import java.net.URL;

import static com.lordofthejars.nosqlunit.mongodb.InMemoryMongoDb.InMemoryMongoRuleBuilder.newInMemoryMongoDbRule;
import static com.lordofthejars.nosqlunit.mongodb.MongoDbRule.MongoDbRuleBuilder.newMongoDbRule;
import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNull.notNullValue;

@RunWith( Arquillian.class )
@UsingDataSet(locations = "GreetingResourceTest.json", loadStrategy = LoadStrategyEnum.CLEAN_INSERT)
public class GreetingResourceTest extends AbstractTest {

    @ClassRule
    public static final InMemoryMongoDb IN_MEMORY_MONGO_DB = newInMemoryMongoDbRule().build();

    @Rule
    public MongoDbRule mongoDbRule = newMongoDbRule().defaultEmbeddedMongoDb("test");

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();


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
                        GreetingService.class
                ).addClasses(
                        //Data
                        GreetingDAO.class,
                        DAO.class,
                        FakeDataStoreConnectionFactory.class
                ).addClasses(
                        //Misc
                        AbstractTest.class
                ).addAsResource(
                        GreetingResourceTest.class.getResource( "GreetingResourceTest.json" ),
                        GreetingResourceTest.class.getPackage().getName()
                                .replace(".", "/") + "/GreetingResourceTest.json"
                );
    }

    @ArquillianResource
    private URL deploymentUrl;

    @Inject
    FakeDataStoreConnectionFactory fakeDataStoreConnectionFactory;

    @Before
    public void setupTest(){
        fakeDataStoreConnectionFactory.setMongoDatabase( mongoDbRule
                .getDatabaseOperation()
                .connectionManager()
                .getDatabase( "test" )
        );
    }

    @Test
    public void shouldGETallGreetingsUponRequest(){
        given()
                .when()
                .get(deploymentUrl + "rest/greetings")
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
