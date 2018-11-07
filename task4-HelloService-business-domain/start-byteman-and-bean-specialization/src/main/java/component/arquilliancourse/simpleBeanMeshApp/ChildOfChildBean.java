package component.arquilliancourse.simpleBeanMeshApp;

import javax.ejb.Stateless;

@Stateless
public class ChildOfChildBean {
    public String runChildOfChildAction () {
        return "Hello World";
    }
}
