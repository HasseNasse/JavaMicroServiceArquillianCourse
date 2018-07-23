package net.nazarsit.javamicroservicearquilliancourse;


import javax.inject.Inject;

public class SimpleService {

    @Inject
    private SimpleInjectable simpleInjectable;

    public int simpleServiceAction(int bound){
        return simpleInjectable.doRandomCalculation(bound);
    }
}
