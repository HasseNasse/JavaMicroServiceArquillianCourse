package component.arquilliancourse.greetingservice;

import component.arquilliancourse.JaxRsResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;


public class AbstractTest {

    public static WebArchive createBasicDeployment () {
        String jsonbApi         = "javax.json.bind:javax.json.bind-api:1.0";
        String yasson           = "org.eclipse:yasson:1.0";
        String javaxJson        = "org.glassfish:javax.json:1.1";
        String mongoDriver      = "org.mongodb:mongodb-driver:3.5.0";
        String restAssured      = "io.rest-assured:rest-assured:3.0.7";
        String fongo            = "com.github.fakemongo:fongo:2.1.0";
        String nosqlUnit        = "com.lordofthejars:nosqlunit-mongodb:1.0.0-rc.5";
        String mockito          = "org.mockito:mockito-core:2.18.0";
        String slf4j            = "org.slf4j:slf4j-api:1.7.5";


        return ShrinkWrap.create( WebArchive.class )
                .addClasses(
                        JaxRsResource.class
                )
                .addAsLibraries( Maven.resolver().resolve(jsonbApi).withTransitivity().asFile() )
                .addAsLibraries( Maven.resolver().resolve(yasson).withTransitivity().asFile() )
                .addAsLibraries( Maven.resolver().resolve(javaxJson).withTransitivity().asFile() )
                .addAsLibraries( Maven.resolver().resolve(mongoDriver).withTransitivity().asFile() )
                .addAsLibraries( Maven.resolver().resolve(restAssured).withTransitivity().asFile() )
                .addAsLibraries( Maven.resolver().resolve(fongo).withTransitivity().asFile() )
                .addAsLibraries( Maven.resolver().resolve(mockito).withTransitivity().asFile() )
                .addAsLibraries( Maven.resolver().resolve(nosqlUnit).withTransitivity().asFile() )
                .addAsLibraries( Maven.resolver().resolve(slf4j).withTransitivity().asFile() )
                .addAsWebInfResource( "test-beans.xml", "beans.xml" )
                .addAsResource( "microprofile-test-config.properties", "META-INF/microprofile-config.properties" );
    }
}
