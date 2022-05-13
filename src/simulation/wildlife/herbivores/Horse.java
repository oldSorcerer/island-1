package simulation.wildlife.herbivores;

import simulation.wildlife.Animal;

import java.util.HashSet;
import java.util.Set;

import static simulation.Params.*;

public class Horse extends Herbivore {

    public Horse() {
        super(horseWeight, horsesInCell, horseMaxDistance, null);
        horsesBorn++;
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
