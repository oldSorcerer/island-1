package simulation.wildlife.predators;

import simulation.wildlife.Animal;

import java.util.HashSet;
import java.util.Set;

import static simulation.Params.*;

public class Bear extends Predator {

    public Bear() {
        super(bearWeight, bearsInCell, bearMaxDistance, bearDiet);
        bearsBorn++;
    }

    @Override
    protected Set<Animal> getOffspring() {
        return new HashSet<>() {{
            add(new Bear());
        }};
    }

    @Override
    public void die() {
        super.die();
        bearsDied++;
    }
}
