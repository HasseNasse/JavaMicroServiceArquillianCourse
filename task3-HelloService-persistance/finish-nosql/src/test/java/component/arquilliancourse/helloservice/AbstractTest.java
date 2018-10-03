package component.arquilliancourse.helloservice;

import component.arquilliancourse.JaxRsResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jboss.shrinkwrap.resolver.api.maven.PomEquippedResolveStage;


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
        String assertJ          = "org.assertj:assertj-core:3.10.0";

        final PomEquippedResolveStage pomEquippedResolveStage = Maven.resolver().loadPomFromFile("pom.xml");

        return ShrinkWrap.create( WebArchive.class )
                .addClasses(
                    JaxRsResource.class
                )
                .addAsLibraries( pomEquippedResolveStage.resolve(jsonbApi).withTransitivity().asFile() )
                .addAsLibraries( pomEquippedResolveStage.resolve(yasson).withTransitivity().asFile() )
                .addAsLibraries( pomEquippedResolveStage.resolve(javaxJson).withTransitivity().asFile() )
                .addAsLibraries( pomEquippedResolveStage.resolve(mongoDriver).withTransitivity().asFile() )
                .addAsLibraries( pomEquippedResolveStage.resolve(restAssured).withTransitivity().asFile() )
                .addAsLibraries( pomEquippedResolveStage.resolve(fongo).withTransitivity().asFile() )
                .addAsLibraries( pomEquippedResolveStage.resolve(mockito).withTransitivity().asFile() )
                .addAsLibraries( pomEquippedResolveStage.resolve(nosqlUnit).withTransitivity().asFile() )
                .addAsLibraries( pomEquippedResolveStage.resolve(slf4j).withTransitivity().asFile() )
                .addAsLibraries( pomEquippedResolveStage.resolve(assertJ).withTransitivity().asFile() )
                .addAsWebInfResource( "test-beans.xml", "beans.xml" )
                .addAsResource( "microprofile-test-config.properties", "META-INF/microprofile-config.properties" );
    }
}
