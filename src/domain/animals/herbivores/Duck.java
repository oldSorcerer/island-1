package domain.animals.herbivores;

import domain.animals.Animal;

import java.util.HashSet;
import java.util.Set;

import static domain.Params.*;

public class Duck extends Herbivore {

    public Duck() {
        ducksBorn++;
        this.weight = duckWeight;
        this.maxInCell = ducksInCell;
        init();
    }

    @Override
    protected Set<Animal> getOffspring() {
        return new HashSet<>() {{
            add(new Duck());
        }};
    }

    @Override
    public void die() {
        super.die();
        ducksDied++;
    }
}
