package domain.animals.herbivores;

import domain.animals.Animal;

import java.util.HashSet;
import java.util.Set;

import static domain.Params.*;

public class Sheep extends Herbivore {

    public Sheep() {
        sheepsBorn++;
        this.weight = sheepWeight;
        this.maxInCell = sheepsInCell;
        init();
    }

    @Override
    protected Set<Animal> getOffspring() {
        return new HashSet<>() {{
            add(new Sheep());
        }};
    }

    @Override
    public void die() {
        super.die();
        sheepsDied++;
    }
}
