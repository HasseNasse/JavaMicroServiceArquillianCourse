package net.nazarsit.javamicroservicearquilliancourse;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class SimpleService {

    @Inject
    private SimpleInjectable simpleInjectable;

    public int simpleServiceAction(int bound){
        return simpleInjectable.doRandomCalculation(bound);
    }
}
