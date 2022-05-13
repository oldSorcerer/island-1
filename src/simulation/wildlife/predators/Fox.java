package simulation.wildlife.predators;

import simulation.wildlife.Animal;

import java.util.HashSet;
import java.util.Set;

import static simulation.Params.*;

public class Fox extends Predator {

    public Fox() {
        super(foxWeight, foxesInCell, foxMaxDistance, foxDiet);
        foxesBorn++;
    }

    @Override
    protected Set<Animal> getOffspring() {
        return new HashSet<>() {{
            add(new Fox());
        }};
    }

    @Override
    public void die() {
        super.die();
        foxesDied++;
    }
}
