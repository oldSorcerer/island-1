package domain.animals.predators;

import domain.animals.Animal;

import java.util.HashSet;
import java.util.Set;

import static domain.Params.*;

public class Wolf extends Predator {

    public Wolf() {
        wolvesBorn++;
        this.weight = wolfWeight;
        this.maxInCell = wolvesInCell;
        init();
        this.diet = wolfDiet;
    }

    @Override
    protected Set<Animal> getOffspring() {
        return new HashSet<>() {{
            add(new Wolf());
        }};
    }

    @Override
    public void die() {
        super.die();
        wolvesDied++;
    }
}
