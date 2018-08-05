package net.nazarsit.javamicroservicearquilliancourse.HelloService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@RequestScoped
public class GreetingService {

    @Inject
    private GreetingDAO greetingDAO;

    public boolean validateAndSaveGreeting(Greeting greeting){

        return greetingDAO.insert( greeting ) ?
                true : false;
    }

}
