package component.arquilliancourse.helloservice.specializations;

import component.arquilliancourse.helloservice.Greeting;
import component.arquilliancourse.helloservice.GreetingDAO;

import javax.enterprise.inject.Specializes;

@Specializes
public class GreetingDAOFindingObjectByIDSpecialization extends GreetingDAO {


    @Override
    public Greeting findByID ( String greeting ) {
        return new Greeting();
    }

}
