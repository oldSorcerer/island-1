package domain.animals.herbivores;

import domain.animals.Animal;

import java.util.HashSet;
import java.util.Set;

import static domain.Params.*;

public class Hamster extends Herbivore {

    public Hamster() {
        hamstersBorn++;
        this.weight = hamsterWeight;
        this.maxInCell = hamstersInCell;
        init();
    }

    @Override
    protected Set<Animal> getOffspring() {
        return new HashSet<>() {{
            add(new Hamster());
        }};
    }

    @Override
    public void die() {
        super.die();
        hamstersDied++;
    }
}
