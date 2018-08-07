package net.nazarsit.javamicroservicearquilliancourse.HelloService;

import com.lordofthejars.nosqlunit.annotation.UsingDataSet;
import com.lordofthejars.nosqlunit.core.LoadStrategyEnum;
import net.nazarsit.javamicroservicearquilliancourse.DataStore.DAO;
import net.nazarsit.javamicroservicearquilliancourse.DataStore.DataStoreConnectionFactory;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import java.net.URL;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.hasItems;

@RunWith( Arquillian.class )
public class HelloResourceTest extends AbstractTest{

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    private static Jsonb jsonb = JsonbBuilder.newBuilder().build();

    @Deployment
    public static WebArchive createDeployment () {
        String mockitoCore = "org.mockito:mockito-core:2.18.0";
        String restAssured = "io.rest-assured:rest-assured:3.0.7";
        String noSqlUnit = "com.lordofthejars:nosqlunit-mongodb:1.0.0-rc.5";

        WebArchive war = createBasicDeployment()
                .addClasses(
                    HelloResource.class,
                    GreetingDAO.class,
                    GreetingService.class,
                    Greeting.class,
                    DAO.class,
                    DataStoreConnectionFactory.class
                )
                .addClass( AbstractTest.class )
                .addAsLibraries( Maven.resolver().resolve(mockitoCore).withTransitivity().asFile() )
                .addAsLibraries( Maven.resolver().resolve(restAssured).withTransitivity().asFile() )
                .addAsLibraries( Maven.resolver().resolve(noSqlUnit).withTransitivity().asFile() );
        System.out.println( war.toString(true) );
        return war;
    }

    @ArquillianResource
    private URL deploymentURL;


//    @Mock
//    MongoDatabase mockMongoDatabase;
//
//    @InjectMocks
//    GreetingDAO greetingDAO;


    // TEST START ---------------

    @Before
    public void setupTest(){
//        Mockito.when(mockMongoDatabase.getCollection( Mockito.anyString() )).thenReturn( getFongoDataBase().getCollection( "greetings" ) );
    }

    @Test
    @UsingDataSet(locations = "initialData.json", loadStrategy = LoadStrategyEnum.CLEAN_INSERT)
    public void shouldReturnJsonRepresentationOfMockedGreetings() throws Exception {

        given()
                .when().get(deploymentURL + "rest/greetings").prettyPrint();

        given()
                .when().get(deploymentURL + "rest/greetings")
                .then().statusCode( 200 )
                .body( "greeting", hasItems( "Hallå", "Hello" ) );

    }

    //    GET All Greetings

    //    POST a greeting

    //    PUT a greeting

}
