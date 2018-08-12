package component.arquilliancourse.helloservice;

import com.lordofthejars.nosqlunit.mongodb.InMemoryMongoDb;
import com.lordofthejars.nosqlunit.mongodb.MongoDbRule;
import component.arquilliancourse.datastore.DAO;
import component.arquilliancourse.datastore.FakeDataStoreConnectionFactory;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import javax.ws.rs.NotAllowedException;

import java.util.NoSuchElementException;

import static com.lordofthejars.nosqlunit.mongodb.InMemoryMongoDb.InMemoryMongoRuleBuilder.newInMemoryMongoDbRule;
import static com.lordofthejars.nosqlunit.mongodb.MongoDbRule.MongoDbRuleBuilder.newMongoDbRule;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith( Arquillian.class )
public class GreetingServiceTest extends AbstractTest {

    @ClassRule
    public static InMemoryMongoDb inMemoryMongoDb = newInMemoryMongoDbRule().build();

    @Rule
    public MongoDbRule mongoDbRule = newMongoDbRule().defaultEmbeddedMongoDb("test");

    @Deployment
    public static WebArchive createDeployment () {
        return createBasicDeployment()
                .addClasses(
                        //Models
                        Greeting.class
                ).addClasses(
                        //Data
                        DAO.class,
                        GreetingDAO.class,
                        FakeDataStoreConnectionFactory.class
                ).addClasses(
                        //Services
                        GreetingService.class
                ).addClasses(
                        //Misc
                        AbstractTest.class
                );
    }

    @Inject
    GreetingService greetingService;

    @Inject
    FakeDataStoreConnectionFactory fakeDataStoreConnectionFactory;

    @Before
    public void setup(){
        fakeDataStoreConnectionFactory.setMongoDatabase(
                mongoDbRule.getDatabaseOperation().connectionManager().getDatabase( "test" )
        );
    }

    @Test
    @InSequence(1)
    public void shouldAllowInsertion() throws Exception {
        //GIVEN
        Greeting greeting = new Greeting( "salam", "ar" );

        //WHEN
        boolean res = greetingService.doInsertion( greeting );

        //THEN
        assertThat(res)
                .isEqualTo( true );
    }

    @Test
    @InSequence(2)
    public void shouldInvalidateInsertionIfGreetingWithGreetingIdAlreadyExists() throws Exception {

        //GIVEN
        Greeting greeting = new Greeting( "salam", "ar" );

        //WHEN + THEN
        boolean res = greetingService.doInsertion( greeting );

        assertThat( res )
                .isEqualTo( false );

    }

}
