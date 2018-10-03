package component.arquilliancourse.helloservice;

import com.mongodb.Mongo;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import component.arquilliancourse.datastore.DAO;
import org.bson.Document;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static com.mongodb.client.model.Filters.eq;

@ApplicationScoped
public class GreetingDAO implements DAO<Greeting> {

    private static final String COLLECTION_NAME = "greetings";

    @Inject
    MongoDatabase mongoDatabase;

    @Override
    public List< Greeting > findAll () {
        List < Greeting > greetingList = new ArrayList();
        try( MongoCursor< Document > cursor = mongoDatabase.getCollection( COLLECTION_NAME ).find().iterator()) {
            while(cursor.hasNext()){
                Document doc = cursor.next();
                System.out.println( doc.toJson() );
                greetingList.add( Greeting.fromJson( doc.toJson() ) );
            }
        }

        if(greetingList.size() == 0)
            throw new NoSuchElementException( "No elements found inside of the datastore" );
        return greetingList;
    }

    @Override
    public Greeting findById ( String greeting ) {
        MongoCollection< Document > collection = mongoDatabase.getCollection( COLLECTION_NAME );
        try ( MongoCursor< Document > cursor = collection.find( eq( "greeting", greeting ) ).iterator() ) {
            if ( cursor.hasNext() )
                return Greeting.fromJson( cursor.next().toJson() );
        }
        throw new NoSuchElementException( "No element with ID " + greeting + " found" );
    }

    public Greeting findByLanguage ( String locale ) {
        MongoCollection< Document > collection = mongoDatabase.getCollection( COLLECTION_NAME );
        try ( MongoCursor< Document > cursor = collection.find( eq( "greetingLanguage", locale ) ).iterator() ) {
            if ( cursor.hasNext() )
                return Greeting.fromJson( cursor.next().toJson() );
        }
        throw new NoSuchElementException( "No element with ID " + locale + " found" );
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
    public boolean deleteById ( String id ) {
        return false;
    }
}
