package component.arquilliancourse.helloservice;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.util.NoSuchElementException;

@RequestScoped
public class GreetingService {

    private GreetingDAO greetingDAO;

    @Inject
    public void setGreetingDAO ( GreetingDAO greetingDAO ) {
        this.greetingDAO = greetingDAO;
    }

    public boolean doInsertion( Greeting greeting) throws NoSuchElementException{
        try {
            Greeting findGreeting = greetingDAO.findByID( greeting.getGreeting() );
        }catch ( NoSuchElementException ex ){
            return greetingDAO.insert( greeting );
        }
        return false;
    }
}
