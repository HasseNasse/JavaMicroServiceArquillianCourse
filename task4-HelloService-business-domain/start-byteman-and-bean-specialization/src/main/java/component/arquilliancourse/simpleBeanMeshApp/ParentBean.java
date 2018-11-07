package component.arquilliancourse.simpleBeanMeshApp;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class ParentBean {

    @Inject
    private ChildBean childBean;

    public String doAction () {
        return childBean.runChildAction();
    }
}
