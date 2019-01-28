package component.arquilliancourse;

import component.arquilliancourse.simplebeanmeshapp.ParentBean;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

@Startup
@Singleton
public class MainStartUpBean {

    @Inject
    private ParentBean parentBean;


    @PostConstruct
    private void postInit(){
        String res = parentBean.doAction();
        System.out.println( "res = " + res );
    }

}
