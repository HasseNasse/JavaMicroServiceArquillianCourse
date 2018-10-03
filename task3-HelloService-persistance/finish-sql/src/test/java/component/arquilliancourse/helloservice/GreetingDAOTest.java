package component.arquilliancourse.helloservice;

import component.arquilliancourse.AbstractTest;
import component.arquilliancourse.common.DAO;
import component.arquilliancourse.common.FakeDataStoreConnectionFactory;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith( Arquillian.class )
public class GreetingDAOTest extends AbstractTest {

//    @ClassRule
//    public static final InMemoryMongoDb IN_MEMORY_MONGO_DB = newInMemoryMongoDbRule().build();

//    @Rule
//    public MongoDbRule mongoDbRule = newMongoDbRule().defaultEmbeddedMongoDb("test");

    @Deployment
    public static WebArchive createDeployment () {
        return createBasicDeployment()
                .addClasses(
                        //Models
                        Greeting.class
                ).addClasses(
                        //DATA
                        DAO.class,
                        GreetingDAO.class,
                        FakeDataStoreConnectionFactory.class
                ).addClasses(
                        //Misc
                        AbstractTest.class
                );
    }

    @ArquillianResource
    RdbmsPopulator populator;

    @Inject
    GreetingDAO greetingDAO;

    @Before
    public void setupTest(){
    }

    @Test(expected = NoSuchElementException.class)
    @InSequence(1)
    public void shouldReturnExceptionOnEmptyFindAll(){
        greetingDAO.findAll();
    }

    @Test
    @InSequence(2)
    public void shouldInsertGreetingsAndFindThem(){

        //GIVEN
        List<Greeting> greetingList = Arrays.asList(
                new Greeting( "salam", "ar" ),
                new Greeting( "hello", "en" ),
                new Greeting( "heji", "sv" )
        );

        //GIVEN + WHEN
        greetingDAO.insert( greetingList.get( 0 ) );
        greetingDAO.insert( greetingList.get( 1 ) );
        greetingDAO.insert( greetingList.get( 2 ) );

        //THEN
        assertThat( greetingDAO.findAll() )
                .containsExactlyInAnyOrder(
                        greetingList.get( 0 ),
                        greetingList.get( 1 ),
                        greetingList.get( 2 )
                );

    }

    @Test
    @InSequence(3)
    public void shouldFindGreetingByID(){
        //GIVEN
        String greetingID = "hello";

        //WHEN
        Greeting greeting =
                greetingDAO.findById( greetingID );

        System.out.println( greeting );
        //THEN
        assertThat( greeting )
                .isNotNull();
        assertThat( greeting.getGreeting() )
                .contains( greetingID );

    }

}
