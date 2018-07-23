package integration.javamicroservicearquilliancourse;

import net.nazarsit.javamicroservicearquilliancourse.SimpleInjectable;
import net.nazarsit.javamicroservicearquilliancourse.SimpleService;
import org.hamcrest.Matcher;
import org.hamcrest.core.IsInstanceOf;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.nullValue;
import static org.hamcrest.core.IsInstanceOf.any;
import static org.hamcrest.core.IsInstanceOf.instanceOf;


//Activate Arquillian
@RunWith( Arquillian.class )
public class SimpleServiceTest {

    //Create Test Archive to be injected into the Test Container
    @Deployment
    public static WebArchive createDeployment () {
        WebArchive war = ShrinkWrap.create( WebArchive.class )
                .addClasses(
                        SimpleService.class,
                        SimpleInjectable.class
                )
                .addAsManifestResource( EmptyAsset.INSTANCE, "beans.xml" );
        System.out.println( war.toString(true) );
        return war;
    }


    //Test Enrichment
    @Inject
    private SimpleInjectable simpleInjectable;

    @Test
    public void simpleService_simpleInjectable_shouldNotBeNull(){
        assertThat( simpleInjectable, is( notNullValue() ) );
    }

    @Test
    public void simpleService_simpleServiceAction_shouldReturnValue(){
        int value = simpleInjectable.doRandomCalculation( 10 );
        assertThat( value, is( IsInstanceOf.<Integer>instanceOf( Integer.class ) ) );
    }

}
