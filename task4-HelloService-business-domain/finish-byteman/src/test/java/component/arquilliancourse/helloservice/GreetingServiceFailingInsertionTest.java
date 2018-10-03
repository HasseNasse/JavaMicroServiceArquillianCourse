package component.arquilliancourse.helloservice;

import component.arquilliancourse.datastore.DAO;
import component.arquilliancourse.datastore.FakeDataStoreConnectionFactory;
import component.arquilliancourse.helloservice.specializations.GreetingDAOFailingInsertionSpecialization;
import component.arquilliancourse.helloservice.specializations.GreetingDAOPassingInsertionSpecialization;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith( Arquillian.class )
public class GreetingServiceFailingInsertionTest extends AbstractTest {


    @Deployment
    public static WebArchive createDeployment () {
        return createBasicDeployment()
                .addClasses(
                    //Services
                    GreetingService.class
                ).addClasses(
                    //DAO
                    GreetingDAO.class,
                    DAO.class
                ).addClasses(
                    //Specializations
                    GreetingDAOFailingInsertionSpecialization.class
                ).addClasses(
                    //Data
                    FakeDataStoreConnectionFactory.class
                ).addClasses(
                    //Model
                    Greeting.class
                ).addClasses(
                    //Data
                    AbstractTest.class
                );
    }

    @Inject
    GreetingService greetingService;

    @Test
    public void shouldReturnFalseIfFindByIdReturnsNullAndInsertionFails(){
        Greeting dummyGreeting = new Greeting(  );
        boolean res = greetingService.attemptInsertion( dummyGreeting );
        assertThat( res )
                .isEqualTo( false );
    }

}
