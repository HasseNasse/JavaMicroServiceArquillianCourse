package net.nazarsit.javamicroservicearquilliancourse.HelloService;

import net.nazarsit.javamicroservicearquilliancourse.JaxRsResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;

import java.io.File;


public class AbstractTest {


    public static WebArchive createBasicDeployment () {
        File configFile = new File("src/test/resources/microprofile-test-config.properties");
        String jsonbApi = "javax.json.bind:javax.json.bind-api:1.0";
        String yasson = "org.eclipse:yasson:1.0";
        String javaxJson = "org.glassfish:javax.json:1.1";
        String mongoDriver = "org.mongodb:mongodb-driver:3.5.0";

        return ShrinkWrap.create( WebArchive.class )
                .addClasses(
                    JaxRsResource.class
                )
                .addAsLibraries( Maven.resolver().resolve(jsonbApi).withTransitivity().asFile() )
                .addAsLibraries( Maven.resolver().resolve(yasson).withTransitivity().asFile() )
                .addAsLibraries( Maven.resolver().resolve(javaxJson).withTransitivity().asFile() )
                .addAsLibraries( Maven.resolver().resolve(mongoDriver).withTransitivity().asFile() )
                .addAsWebInfResource( EmptyAsset.INSTANCE, "beans.xml" )
                .addAsResource( configFile, "META-INF/microprofile-config.properties" );
    }
}
