package domain.animals.herbivores;

import domain.animals.Animal;

import java.util.HashSet;
import java.util.Set;

import static domain.Params.*;

public class Goat extends Herbivore {

    public Goat() {
        goatsBorn++;
        this.weight = goatWeight;
        this.maxInCell = goatsInCell;
        this.maxDistance = goatMaxDistance;
        init();
    }

    @Override
    protected Set<Animal> getOffspring() {
        return new HashSet<>() {{
            add(new Goat());
        }};
    }

    @Override
    public void die() {
        super.die();
        goatsDied++;
    }
}
