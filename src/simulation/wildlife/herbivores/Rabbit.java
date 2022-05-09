package simulation.wildlife.herbivores;

import simulation.wildlife.Animal;

import java.util.HashSet;
import java.util.Set;

import static simulation.Params.*;

public class Rabbit extends Herbivore {

    public Rabbit() {
        rabbitsBorn++;
        this.weight = rabbitWeight;
        this.maxInCell = rabbitsInCell;
        this.maxDistance = rabbitMaxDistance;
        init();
    }

    @Override
    protected Set<Animal> getOffspring() {
        return new HashSet<>() {{
            add(new Rabbit());
            add(new Rabbit());
        }};
    }

    @Override
    public void die() {
        super.die();
        rabbitsDied++;
    }
}
