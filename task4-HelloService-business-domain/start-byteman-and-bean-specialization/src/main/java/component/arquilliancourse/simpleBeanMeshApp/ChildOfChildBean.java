package component.arquilliancourse.simplebeanmeshapp;

import javax.ejb.Stateless;

@Stateless
public class ChildOfChildBean {
    public String runChildOfChildAction () {
        return "Hello World";
    }
}
