package component.arquilliancourse.helloservice;

import jdk.nashorn.internal.runtime.regexp.joni.exception.ValueException;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.util.NoSuchElementException;

@RequestScoped
public class GreetingService {

    @Inject
    private GreetingDAO greetingDAO;

    public boolean validateAndSaveGreeting(Greeting greeting){
        greeting.setGreeting( greeting.getGreeting().toLowerCase() );

        try{
            Greeting possibleDuplicate = greetingDAO.findByID( greeting.getGreeting() );
            if(possibleDuplicate != null)
                throw new ValueException( "Document with doc-id: " + greeting.getGreeting() + " already exists!");
        }catch ( NoSuchElementException ex ){
            ex.printStackTrace();
            return greetingDAO.insert( greeting );
        }
        return false;
    }

}
