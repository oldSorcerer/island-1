package simulation.wildlife.herbivores;

import simulation.wildlife.Animal;

import java.util.HashSet;
import java.util.Set;

import static simulation.Params.*;

public class Deer extends Herbivore {

    public Deer() {
        deerBorn++;
        this.weight = deerWeight;
        this.maxInCell = deerInCell;
        this.maxDistance = deerMaxDistance;
        init();
    }

    @Override
    protected Set<Animal> getOffspring() {
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
