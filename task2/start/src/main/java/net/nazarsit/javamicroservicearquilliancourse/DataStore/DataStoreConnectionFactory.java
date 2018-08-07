package net.nazarsit.javamicroservicearquilliancourse.DataStore;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Produces;
import javax.inject.Singleton;


@Singleton
public class DataStoreConnectionFactory {
    private MongoDatabase mongoDatabase;


    @PostConstruct
    public void init(){
        this.mongoDatabase = establishConnection();
    }

    protected MongoDatabase establishConnection(){
        final Config config = ConfigProvider.getConfig();
        final String DB_PATH    = config.getValue( "mongodb.path", String.class );
        final int DB_PORT       = config.getValue( "mongodb.port", Integer.class );
        final String DB_NAME    = config.getValue( "mongodb.dbname", String.class );

        return new MongoClient( DB_PATH, DB_PORT ).getDatabase( DB_NAME );
    }

    @Produces
    public MongoDatabase getMongoDatabase(){
        return this.mongoDatabase;
    }

}
