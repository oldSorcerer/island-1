package simulation.wildlife.predators;

import simulation.wildlife.Animal;

import java.util.HashSet;
import java.util.Set;

import static simulation.Params.*;

public class Boa extends Predator {

    public Boa() {
        super(boaWeight, boasInCell, boaMaxDistance, boaDiet);
        boasBorn++;
    }

    @Override
    protected Set<Animal> getOffspring() {
        return new HashSet<>() {{
            add(new Boa());
        }};
    }

    @Override
    public void die() {
        super.die();
        boasDied++;
    }
}