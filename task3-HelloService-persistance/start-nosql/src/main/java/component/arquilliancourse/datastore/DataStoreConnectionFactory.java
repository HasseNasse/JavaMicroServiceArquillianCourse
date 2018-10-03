package component.arquilliancourse.datastore;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

@ApplicationScoped
public class DataStoreConnectionFactory {

    private MongoDatabase mongoDatabase;

    @PostConstruct
    public void init(){
        this.mongoDatabase =  establishMongoDBConnection();
    }


    private MongoDatabase establishMongoDBConnection () {
        Config config = ConfigProvider.getConfig();
        String DBPATH = config.getValue("mongodb.path", String.class);
        int DBPORT = config.getValue("mongodb.port", Integer.class);
        String DBNAME = config.getValue("mongodb.dbname", String.class);

        return new MongoClient(DBPATH, DBPORT).getDatabase( DBNAME );
    }

    @Produces
    public MongoDatabase getMongoDatabase () {
        return this.mongoDatabase;
    }
}
