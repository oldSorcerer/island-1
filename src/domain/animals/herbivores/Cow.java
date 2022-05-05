package domain.animals.herbivores;

import domain.animals.Animal;

import java.util.HashSet;
import java.util.Set;

import static domain.Params.*;

public class Cow extends Herbivore {

    public Cow() {
        cowsBorn++;
        this.weight = cowWeight;
        this.maxInCell = cowsInCell;
        init();
    }

    @Override
    protected Set<Animal> getOffspring() {
        return new HashSet<>() {{
            add(new Cow());
        }};
    }

    @Override
    public void die() {
        super.die();
        cowsDied++;
    }
}
