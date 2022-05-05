package domain.animals.herbivores;

import domain.animals.Animal;

import java.util.HashSet;
import java.util.Set;

import static domain.Params.*;

public class Kangaroo extends Herbivore {

    public Kangaroo() {
        kangaroosBorn++;
        this.weight = kangarooWeight;
        this.maxInCell = kangaroosInCell;
        init();
    }

    @Override
    protected Set<Animal> getOffspring() {
        return new HashSet<>() {{
            add(new Kangaroo());
        }};
    }

    @Override
    public void die() {
        super.die();
        kangaroosDied++;
    }
}
