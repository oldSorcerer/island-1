package domain.animals.predators;

import domain.animals.Animal;

import java.util.HashSet;
import java.util.Set;

import static domain.Params.*;

public class Bear extends Predator {

    public Bear() {
        bearsBorn++;
        this.weight = bearWeight;
        this.maxInCell = bearsInCell;
        init();
        this.diet = bearDiet;
    }

    @Override
    protected Set<Animal> getOffspring() {
        return new HashSet<>() {{
            add(new Bear());
        }};
    }

    @Override
    public void die() {
        super.die();
        bearsDied++;
    }
}
