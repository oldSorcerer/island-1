package simulation.wildlife.herbivores;

import simulation.wildlife.Animal;

import java.util.HashSet;
import java.util.Set;

import static simulation.Params.*;

public class Buffalo extends Herbivore {

    public Buffalo() {
        buffaloesBorn++;
        this.weight = buffaloWeight;
        this.maxInCell = buffaloesInCell;
        this.maxDistance = buffaloMaxDistance;
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
