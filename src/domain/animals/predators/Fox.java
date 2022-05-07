package domain.animals.predators;

import domain.animals.Animal;

import java.util.HashSet;
import java.util.Set;

import static domain.Params.*;

public class Fox extends Predator {

    public Fox() {
        foxesBorn++;
        this.weight = foxWeight;
        this.maxInCell = foxesInCell;
        this.maxDistance = foxMaxDistance;
        init();
        this.diet = foxDiet;
    }

    @Override
    protected Set<Animal> getOffspring() {
        return new HashSet<>() {{
            add(new Fox());
        }};
    }

    @Override
    public void die() {
        super.die();
        foxesDied++;
    }
}
