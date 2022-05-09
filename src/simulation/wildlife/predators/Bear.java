package simulation.wildlife.predators;

import simulation.wildlife.Animal;

import java.util.HashSet;
import java.util.Set;

import static simulation.Params.*;

public class Bear extends Predator {

    public Bear() {
        bearsBorn++;
        this.weight = bearWeight;
        this.maxInCell = bearsInCell;
        this.maxDistance = bearMaxDistance;
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
