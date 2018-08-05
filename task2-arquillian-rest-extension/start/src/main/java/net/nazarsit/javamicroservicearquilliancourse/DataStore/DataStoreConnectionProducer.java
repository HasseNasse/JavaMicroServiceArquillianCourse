package net.nazarsit.javamicroservicearquilliancourse.DataStore;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;


@ApplicationScoped
public class DataStoreConnectionProducer {
    private MongoClient mongoClient;

    private static final Config config = ConfigProvider.getConfig();

    @Produces
    public MongoDatabase createMongoClient(){
        final String DB_PATH    = config.getValue( "mongodb.path", String.class );
        final int DB_PORT       = config.getValue( "mongodb.port", Integer.class );
        final String DB_NAME    = config.getValue( "mongodb.dbname", String.class );

        if( DB_NAME.equals( "test" ) )
            return new MongoClient(  ).getDatabase( DB_NAME );
        else
            return new MongoClient( DB_PATH, DB_PORT ).getDatabase( DB_NAME );
        
    }
}
