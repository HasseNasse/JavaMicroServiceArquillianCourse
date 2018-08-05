package component;

import net.nazarsit.javamicroservicearquilliancourse.SimpleInjectable;
import net.nazarsit.javamicroservicearquilliancourse.SimpleService;
import org.hamcrest.core.IsInstanceOf;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith( Arquillian.class )
public class SimpleServiceTest {

    @Deployment
    public static WebArchive createDeployment () {
        WebArchive war = ShrinkWrap.create( WebArchive.class )
                .addClasses( SimpleService.class, SimpleInjectable.class )
                .addAsManifestResource( EmptyAsset.INSTANCE, "beans.xml" );

        System.out.println( war.toString(true) );
        return war;
    }

    @Inject
    private SimpleInjectable simpleInjectable;

    @Inject
    private SimpleService simpleService;

    @Test
    public void simpleService_simpleInjectable_isNotNull(){
        assertThat(simpleInjectable, is(notNullValue()));
    }

    @Test
    public void simpleService_simpleService_isNotNull(){
        assertThat(simpleService, is(notNullValue()));
    }

    @Test
    public void simpleService_simpleServiceAction_shouldReturnIntegerType(){
        int value = simpleService.simpleServiceAction( 20 );
        assertThat( value, is( IsInstanceOf.<Integer>instanceOf( Integer.class )) );
        System.out.println( value );
    }
}
