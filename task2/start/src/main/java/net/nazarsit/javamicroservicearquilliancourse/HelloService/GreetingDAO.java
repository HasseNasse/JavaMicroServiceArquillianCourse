package net.nazarsit.javamicroservicearquilliancourse.HelloService;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import net.nazarsit.javamicroservicearquilliancourse.DataStore.DAO;
import org.bson.Document;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static com.mongodb.client.model.Filters.eq;


@ApplicationScoped
public class GreetingDAO implements DAO<Greeting>{

    private static final String COLLECTION_NAME = "greetings";

    @Inject
    MongoDatabase mongoDatabase;


    @Override
    public List< Greeting > findAll () {
        MongoCollection<Document> collection = getCollection();
        List<Greeting> greetingList = new ArrayList();
        try(MongoCursor<Document > cursor = collection.find().iterator()) {
            while ( cursor.hasNext() ){
                Greeting greeting = Greeting.fromJson( cursor.next().toJson() );
                greetingList.add( greeting );
            }

        }

        return greetingList;
    }

    @Override
    public Greeting findByID ( String greeting ) {
        MongoCollection<Document> collection = getCollection();
        try(MongoCursor<Document> cursor = collection.find(eq("greeting", greeting)).iterator()){
            if(cursor.hasNext())
                return Greeting.fromJson( cursor.next().toJson() );
        }

        throw new NoSuchElementException( "No document in doc-db with the id: " + greeting );
    }

    public Greeting findByLanguage(String locale){
        MongoCollection<Document> collection = getCollection();
        try(MongoCursor<Document> cursor = collection.find(eq("greetingLanguage", locale)).iterator()){
            if(cursor.hasNext())
                return Greeting.fromJson( cursor.next().toJson() );
        }

        throw new NoSuchElementException( "No document in doc-db with the language-id: " + locale );
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

    private MongoCollection getCollection () {
        return mongoDatabase.getCollection( COLLECTION_NAME );
    }
}
