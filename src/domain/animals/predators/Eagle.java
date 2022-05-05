package domain.animals.predators;

import domain.animals.Animal;

import java.util.HashSet;
import java.util.Set;

import static domain.Params.*;

public class Eagle extends Predator {

    public Eagle() {
        eaglesBorn++;
        this.weight = eagleWeight;
        this.maxInCell = eaglesInCell;
        init();
        this.diet = eagleDiet;
    }

    @Override
    protected Set<Animal> getOffspring() {
        return new HashSet<>() {{
            add(new Eagle());
        }};
    }

    @Override
    public void die() {
        super.die();
        eaglesDied++;
    }
}