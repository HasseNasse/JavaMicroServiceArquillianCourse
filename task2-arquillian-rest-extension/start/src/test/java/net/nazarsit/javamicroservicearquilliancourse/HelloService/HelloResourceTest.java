package net.nazarsit.javamicroservicearquilliancourse.HelloService;

import com.lordofthejars.nosqlunit.annotation.UsingDataSet;
import com.lordofthejars.nosqlunit.mongodb.InMemoryMongoDb;
import com.lordofthejars.nosqlunit.mongodb.MongoDbRule;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import net.nazarsit.javamicroservicearquilliancourse.DataStore.DAO;
import net.nazarsit.javamicroservicearquilliancourse.DataStore.DataStoreConnectionProducer;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
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

import static io.restassured.RestAssured.*;

@RunWith( Arquillian.class )
@RunAsClient
@UsingDataSet(locations = "HelloResourceTest.json")
public class HelloResourceTest extends AbstractTest{

    private static final String DB_NAME     = "test";

    // TEST SETUP ---------------

    @ClassRule
    public static final InMemoryMongoDb inMemoryMongoDb =
            newInMemoryMongoDbRule().build();
    @Rule
    public MongoDbRule embeddedMongoDbRule = newMongoDbRule()
            .defaultEmbeddedMongoDb(DB_NAME);

    @ArquillianResource
    private URL deploymentURL;

    @Inject
    MongoClient mongoClient;

    private static Jsonb jsonb = JsonbBuilder.newBuilder().build();

    @Deployment(testable = false)
    public static WebArchive createDeployment () {
        WebArchive war = createBasicDeployment()
                .addClasses(
                    HelloResource.class,
                    GreetingDAO.class,
                    GreetingService.class,
                    Greeting.class,
                    DAO.class,
                    DataStoreConnectionProducer.class
                );
        System.out.println( war.toString(true) );
        return war;
    }

    // TEST START ---------------

    @Before
    public void setupTest(){
    }

    //    GET All Greetings
    @Test
    @RunAsClient
    public void helloResource_ShouldReturnAll() throws Exception{
        given()
            .when().get(deploymentURL + "rest/greetings").prettyPrint();
    }

    //    POST a greeting

    //    PUT a greeting

    private MongoDatabase getFongoDataBase(){
        return mongoClient.getDatabase( DB_NAME );
    }
}
