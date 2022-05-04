package domain.animals.predators;

import domain.animals.Animal;

import java.util.HashSet;
import java.util.Set;

import static domain.Params.*;

public class Wolf extends Predator {

    public Wolf() {
        wolvesBorn++;
        this.weight = wolfWeight;
        init();
        this.diet = wolfDiet;
    }

    @Override
    protected Set<Animal> getChild() {
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
