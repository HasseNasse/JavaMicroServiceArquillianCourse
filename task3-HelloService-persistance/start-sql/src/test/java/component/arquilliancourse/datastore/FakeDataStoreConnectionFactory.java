package component.arquilliancourse.datastore;

import com.mongodb.client.MongoDatabase;
import org.mockito.Mockito;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Alternative;
import javax.enterprise.inject.Produces;
import javax.inject.Singleton;

@ApplicationScoped
@Alternative
public class FakeDataStoreConnectionFactory {

    private MongoDatabase mongoDatabase;

    @PostConstruct
    public void init(){
        this.mongoDatabase = establishMongoDBConnection();
    }

    protected MongoDatabase establishMongoDBConnection(){
        return Mockito.mock(MongoDatabase.class);
    }

    @Produces
    public MongoDatabase getMongoDatabase () {
        return this.mongoDatabase;
    }

    public void setMongoDatabase ( MongoDatabase mongoDatabase ) {
        this.mongoDatabase = mongoDatabase;
    }
}
