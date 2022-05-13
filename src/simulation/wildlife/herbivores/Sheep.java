package simulation.wildlife.herbivores;

import simulation.wildlife.Animal;

import java.util.HashSet;
import java.util.Set;

import static simulation.Params.*;

public class Sheep extends Herbivore {

    public Sheep() {
        super(sheepWeight, sheepsInCell, sheepMaxDistance, null);
        sheepsBorn++;
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
