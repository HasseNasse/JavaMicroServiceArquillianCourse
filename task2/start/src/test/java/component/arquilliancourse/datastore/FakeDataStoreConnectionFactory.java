package component.arquilliancourse.datastore;

import com.mongodb.client.MongoDatabase;
import org.mockito.Mockito;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Alternative;
import javax.enterprise.inject.Produces;
import javax.inject.Singleton;

@Singleton
@Alternative
public class FakeDataStoreConnectionFactory {
    private MongoDatabase mongoDatabase;


    @PostConstruct
    public void init(){
        this.mongoDatabase = establishConnection();
    }

    protected MongoDatabase establishConnection(){
        return Mockito.mock(MongoDatabase.class);
    }

    @Produces
    public MongoDatabase getMongoDatabase(){
        return this.mongoDatabase;
    }

    public void setMongoDatabase(MongoDatabase db){
        this.mongoDatabase = db;
    }

}

