package net.nazarsit.javamicroservicearquilliancourse;

import javax.enterprise.context.RequestScoped;
import java.util.Random;

@RequestScoped
public class SimpleInjectable {
    Random random =  new Random(  );

    public int doRandomCalculation(int bound){
        return random.nextInt() * random.nextInt(bound);
    }
}
