package component.arquilliancourse.helloservice;

import com.lordofthejars.nosqlunit.annotation.UsingDataSet;
import com.lordofthejars.nosqlunit.mongodb.InMemoryMongoDb;
import com.lordofthejars.nosqlunit.mongodb.MongoDbRule;
import component.arquilliancourse.datastore.DAO;
import component.arquilliancourse.datastore.DataStoreConnectionFactory;
import component.arquilliancourse.datastore.FakeDataStoreConnectionFactory;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.*;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import java.net.URL;
import java.util.Locale;

import static com.lordofthejars.nosqlunit.mongodb.InMemoryMongoDb.InMemoryMongoRuleBuilder.newInMemoryMongoDbRule;
import static com.lordofthejars.nosqlunit.mongodb.MongoDbRule.MongoDbRuleBuilder.newMongoDbRule;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.post;
import static io.restassured.RestAssured.when;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.from;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;

@RunWith( Arquillian.class )
public class HelloResourceTest extends AbstractTest{

    @ClassRule
    public static final InMemoryMongoDb IN_MEMORY_MONGO_DB = newInMemoryMongoDbRule().build();

    @Rule
    public MongoDbRule mongoDbRule = newMongoDbRule().defaultEmbeddedMongoDb("test");

    @Deployment
    public static WebArchive createDeployment () {

        WebArchive war = createBasicDeployment()
                .addClasses(
                    GreetingDAO.class,
                    Greeting.class,
                    DAO.class,
                    FakeDataStoreConnectionFactory.class
                )
                .addClass( AbstractTest.class )
                .addAsResource(
                    HelloResourceTest.class.getResource("HelloResourceTest.json"),
                    HelloResourceTest.class.getPackage().getName().replace(".", "/") + "/HelloResourceTest.json"
                );
        System.out.println( war.toString(true) );
        return war;
    }

    @Inject
    FakeDataStoreConnectionFactory fakeDataStoreConnectionFactory;

    @Inject
    GreetingDAO greetingDAO;

    @Before
    public void setupTest(){
        fakeDataStoreConnectionFactory.setMongoDatabase( mongoDbRule.getDatabaseOperation().connectionManager().getDatabase( "test" ) );
    }

    //    POST a greeting
    @Test
    @InSequence(1)
    public void shouldInsertGreetingToFongoDS(){

        // Given
        final Greeting greeting = new Greeting( "Salam", "ar" );

        // When
        final boolean isInserted = greetingDAO.insert(greeting);

        // Then

        assertThat(isInserted).isEqualTo(true);
        final List<Greeting> all = greetingDAO.findAll();
        assertThat(all)
            .hasSize(1)
            .containsExactly(greeting);
    }

    //    GET All Greetings
    @Test
    @UsingDataSet(locations = "HelloResourceTest.json")
    @InSequence(2)
    public void shouldReturnJsonRepresentationOfMockedGreeting() throws Exception {

        // Given
        final Greeting en = new Greeting("hello", "en");
        final Greeting ar= new Greeting("salam", "ar");
        final Greeting sv = new Greeting("hej", "sv");

        // When
        final List<Greeting> all = greetingDAO.findAll();

        // Then
        assertThat(all)
            .hasSize(3)
            .containsExactlyInAnyOrder(en, ar, sv);
    }

    /**@Test
    @InSequence(3)
    public void shouldReturnForbiddenIfGreetingIDAlreadyExistsInDataStore(){

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

    @Test
    public void shouldReturnGreetingGivenAGreetingLanguage(){
        //GIVEN


        //WHEN
        given()
            .contentType( "application/json" )
            .queryParam( "locale", "en" )
        .when()
            .get(deploymentURL + "rest/greetings")
        .then()
            .statusCode( 404 );

        //THEN


    }

    //    PUT a greeting
    **/

}
