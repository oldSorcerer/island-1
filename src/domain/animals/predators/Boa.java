package domain.animals.predators;

import domain.animals.Animal;

import java.util.HashSet;
import java.util.Set;

import static domain.Params.*;

public class Boa extends Predator {

    public Boa() {
        boasBorn++;
        this.weight = boaWeight;
        this.maxInCell = boasInCell;
        init();
        this.diet = boaDiet;
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