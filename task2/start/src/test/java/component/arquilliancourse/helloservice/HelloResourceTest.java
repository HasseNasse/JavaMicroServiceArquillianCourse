package component.arquilliancourse.helloservice;

import com.lordofthejars.nosqlunit.mongodb.InMemoryMongoDb;
import com.lordofthejars.nosqlunit.mongodb.MongoDbRule;
import component.arquilliancourse.datastore.DAO;
import component.arquilliancourse.datastore.DataStoreConnectionFactory;
import component.arquilliancourse.datastore.FakeDataStoreConnectionFactory;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.*;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import java.net.URL;

import static com.lordofthejars.nosqlunit.mongodb.InMemoryMongoDb.InMemoryMongoRuleBuilder.newInMemoryMongoDbRule;
import static com.lordofthejars.nosqlunit.mongodb.MongoDbRule.MongoDbRuleBuilder.newMongoDbRule;
import static io.restassured.RestAssured.given;

@RunWith( Arquillian.class )
public class HelloResourceTest extends AbstractTest{

    @ClassRule
    public static final InMemoryMongoDb IN_MEMORY_MONGO_DB = newInMemoryMongoDbRule().build();

    @Rule
    public MongoDbRule mongoDbRule = newMongoDbRule().defaultEmbeddedMongoDb("test");

    private static Jsonb jsonb = JsonbBuilder.newBuilder().build();

    @Deployment
    public static WebArchive createDeployment () {

        WebArchive war = createBasicDeployment()
                .addClasses(
                    HelloResource.class,
                    GreetingDAO.class,
                    GreetingService.class,
                    Greeting.class,
                    DAO.class,
                    DataStoreConnectionFactory.class,
                    FakeDataStoreConnectionFactory.class
                )
                .addClass( AbstractTest.class );
        System.out.println( war.toString(true) );
        return war;
    }

    @ArquillianResource
    private URL deploymentURL;

    @Inject
    FakeDataStoreConnectionFactory fakeDataStoreConnectionFactory;


    @Before
    public void setupTest(){
        fakeDataStoreConnectionFactory.setMongoDatabase( mongoDbRule.getDatabaseOperation().connectionManager().getDatabase( "test" ) );

    }

    //    GET All Greetings
    @Test
    @InSequence(2)
    public void shouldReturnJsonRepresentationOfMockedGreetings() throws Exception {
        // GIVEN


        // WHEN + THEN
        given()
            .when().get(deploymentURL + "rest/greetings").prettyPrint();

    }


    //    POST a greeting
    @Test
    @InSequence(1)
    public void shouldInsertGreetingToFongoDS(){

        // GIVEN
        Greeting greeting = new Greeting( "Salam", "ar" );
        String greetingJson = greeting.toJson();

        // WHEN + THEN
        given()
            .contentType( "application/json" )
            .body( greetingJson )
        .when()
            .post(deploymentURL + "rest/greetings" )
        .then()
            .statusCode( 200 );
    }

    @Test
    @InSequence(3)
    public void shouldReturnForbiddenIfGreetingAlreadyExistsInDataStore(){

        // GIVEN
        Greeting greeting = new Greeting( "Salam", "ur" );
        String greetingJson = greeting.toJson();

        // WHEN + THEN
        given()
                .contentType( "application/json" )
                .body( greetingJson )
        .when()
                .post(deploymentURL + "rest/greetings" )
        .then()
                .statusCode( 400 );
    }

    //    PUT a greeting


}
