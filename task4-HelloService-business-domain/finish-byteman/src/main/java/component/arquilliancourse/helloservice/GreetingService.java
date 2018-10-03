package component.arquilliancourse.helloservice;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@RequestScoped
public class GreetingService {

    @Inject
    private GreetingDAO greetingDAO;

    public boolean attemptInsertion(Greeting greeting){
        if(hasDuplicate( greeting.getGreeting() ))
            return greetingDAO.insert( greeting );
        return false;
    }

    private boolean hasDuplicate(String greetingId){
        if(greetingDAO.findByID( greetingId ) != null)
            return false;
        return true;
    }

}
