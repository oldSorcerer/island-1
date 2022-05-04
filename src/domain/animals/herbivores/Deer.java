package domain.animals.herbivores;

import domain.animals.Animal;

import java.util.HashSet;
import java.util.Set;

import static domain.Params.*;

public class Deer extends Herbivore {

    public Deer() {
        deerBorn++;
        this.weight = deerWeight;
        init();
    }

    @Override
    protected Set<Animal> getChild() {
        return new HashSet<>() {{
            add(new Deer());
        }};
    }

    @Override
    public void die() {
        super.die();
        deerDied++;
    }
}
