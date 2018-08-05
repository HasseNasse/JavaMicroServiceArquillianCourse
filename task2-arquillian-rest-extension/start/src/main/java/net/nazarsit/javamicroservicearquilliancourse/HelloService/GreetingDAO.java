package net.nazarsit.javamicroservicearquilliancourse.HelloService;

import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import net.nazarsit.javamicroservicearquilliancourse.DataStore.DAO;
import org.bson.Document;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class GreetingDAO implements DAO<Greeting>{


    @Inject
    MongoDatabase mongoDatabase;

    private static final Config config = ConfigProvider.getConfig();
    private static final String COLLECTION_NAME = config.getValue( "mongodb.col.name", String.class );



    @Override
    public List< Greeting > findAll () {
        List<Greeting> greetingList = new ArrayList();
        try(MongoCursor<Document > cursor = mongoDatabase.getCollection( COLLECTION_NAME ).find().iterator()) {
            while ( cursor.hasNext() ){
                Greeting greeting = Greeting.fromJson( cursor.next().toJson() );
                greetingList.add( greeting );
            }

        }

        return greetingList;
    }

    @Override
    public Greeting findByID ( String id ) {

        return null;
    }

    public Greeting findByLanguage(String locale){
        return null;
    }

    @Override
    public boolean insert ( Greeting obj ) {

        try{
            mongoDatabase.getCollection( COLLECTION_NAME ).insertOne( Document.parse( obj.toJson() ) );
            return true;
        }catch ( Exception e ){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update ( Greeting obj ) {

        return false;
    }

    @Override
    public boolean delete ( Greeting obj ) {

        return false;
    }

    @Override
    public boolean deleteByID ( String id ) {

        return false;
    }
}
