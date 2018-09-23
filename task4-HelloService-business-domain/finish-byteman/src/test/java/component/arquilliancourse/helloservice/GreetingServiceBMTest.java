package component.arquilliancourse.helloservice;

import component.arquilliancourse.datastore.DAO;
import component.arquilliancourse.datastore.FakeDataStoreConnectionFactory;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.extension.byteman.api.BMRule;
import org.jboss.arquillian.extension.byteman.api.BMRules;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith( Arquillian.class )
public class GreetingServiceBMTest extends AbstractTest {

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
    @BMRules({
        @BMRule(
            name = "GreetingDAO insert fails insertion",
            targetClass = "GreetingDAO",
            targetMethod = "insert",
            action = "return false"
        ),
        @BMRule(
                name = "GreetingDAO findbyId does not find any element",
                targetClass = "GreetingDAO",
                targetMethod = "findByID",
                action = "return null"
        )}
    )
    public void shouldReturnFalseIfFindByIdReturnsNullAndInsertionFails(){
        Greeting dummyGreeting = new Greeting(  );
        boolean res = greetingService.attemptInsertion( dummyGreeting );
        assertThat( res )
                .isEqualTo( false );
    }

    @Test
    @BMRules({
            @BMRule(
                    name = "GreetingDAO insert passes",
                    targetClass = "GreetingDAO",
                    targetMethod = "insert",
                    action = "return true"
            ),
            @BMRule(
                    name = "GreetingDAO findbyId returns null",
                    targetClass = "GreetingDAO",
                    targetMethod = "findByID",
                    action = "return null"
            )}
    )
    public void shouldReturnTrueIfNoGreetingExistsAndInsertionSucceeded(){
        Greeting dummyGreeting = new Greeting(  );
        boolean res = greetingService.attemptInsertion( dummyGreeting );
        assertThat( res )
                .isEqualTo( true );
    }

//    @Test
//    @BMRules(
//            @BMRule(
//                    name = "GreetingDAO findbyId finds element in db",
//                    targetClass = "GreetingDAO",
//                    targetMethod = "findByID",
//                    action = "return new Greeting"
//            )
//    )
//    public void shouldReturnFalseIfFindByIdReturnsNonNullValue(){
//        Greeting dummyGreeting = new Greeting(  );
//        boolean res = greetingService.attemptInsertion( dummyGreeting );
//        assertThat( res )
//                .isEqualTo( false );
//    }
}
