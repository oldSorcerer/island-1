package domain.animals.herbivores;

import domain.animals.Animal;

import java.util.HashSet;
import java.util.Set;

import static domain.Params.*;

public class Buffalo extends Herbivore {

    public Buffalo() {
        buffaloesBorn++;
        this.weight = buffaloWeight;
        this.maxInCell = buffaloesInCell;
        init();
    }

    @Override
    protected Set<Animal> getOffspring() {
        return new HashSet<>() {{
            add(new Buffalo());
        }};
    }

    @Override
    public void die() {
        super.die();
        buffaloesDied++;
    }
}
