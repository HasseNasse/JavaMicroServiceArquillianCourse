package component.arquilliancourse.helloservice;

import component.arquilliancourse.JaxRsResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jboss.shrinkwrap.resolver.api.maven.PomEquippedResolveStage;

public class AbstractTest {

    public static WebArchive createBasicDeployment () {
        String jsonbApi         = "javax.json.bind:javax.json.bind-api";
        String yasson           = "org.eclipse:yasson";
        String javaxJson        = "org.glassfish:javax.json";
        String mongoDriver      = "org.mongodb:mongodb-driver";
        String restAssured      = "io.rest-assured:rest-assured";
        String fongo            = "com.github.fakemongo:fongo";
        String nosqlUnit        = "com.lordofthejars:nosqlunit-mongodb";
        String mockito          = "org.mockito:mockito-core";
        String slf4j            = "org.slf4j:slf4j-api";
        String assertj          = "org.assertj:assertj-core";

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
                .addAsLibraries( pomEquippedResolveStage.resolve(assertj).withoutTransitivity().asFile() )
                .addAsWebInfResource( "test-beans.xml", "beans.xml" )
                .addAsResource( "microprofile-test-config.properties", "META-INF/microprofile-config.properties" );
    }
}
