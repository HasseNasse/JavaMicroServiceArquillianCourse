package component.arquilliancourse.simplebeanmeshapp;


import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class ChildBean {

    @Inject
    private ChildOfChildBean childOfChildBean;

    public String runChildAction () {
        return childOfChildBean.runChildOfChildAction();
    }
}