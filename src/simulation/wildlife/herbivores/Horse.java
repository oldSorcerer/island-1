package simulation.wildlife.herbivores;

import simulation.wildlife.Animal;

import java.util.HashSet;
import java.util.Set;

import static simulation.Params.*;

public class Horse extends Herbivore {

    public Horse() {
        horsesBorn++;
        this.weight = horseWeight;
        this.maxInCell = horsesInCell;
        this.maxDistance = horseMaxDistance;
        init();
    }

    @Override
    protected Set<Animal> getOffspring() {
        return new HashSet<>() {{
            add(new Horse());
        }};
    }

    @Override
    public void die() {
        super.die();
        horsesDied++;
    }
}
