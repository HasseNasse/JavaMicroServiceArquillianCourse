package component.arquilliancourse.datastore;

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
    private void init(){
        this.mongoDatabase = establishConnection();
    }

    @Produces
    protected MongoDatabase establishConnection(){
        Config config = ConfigProvider.getConfig();
        String DBPATH = config.getValue( "mongodb.path" ,String.class );
        int DBPORT = config.getValue( "mongodb.port", Integer.class );
        String DBNAME = config.getValue( "mongodb.name", String.class );
        return new MongoClient( DBPATH, DBPORT ).getDatabase( DBNAME );
    }
}
