package component.arquilliancourse.helloservice.specializations;

import component.arquilliancourse.helloservice.Greeting;
import component.arquilliancourse.helloservice.GreetingDAO;

import javax.enterprise.inject.Specializes;

@Specializes
public class GreetingDAOFailingInsertionSpecialization extends GreetingDAO {



    @Override
    public Greeting findByID ( String greeting ) {
        return null;
    }

    @Override
    public boolean insert ( Greeting obj ) {
        return false;
    }
}
