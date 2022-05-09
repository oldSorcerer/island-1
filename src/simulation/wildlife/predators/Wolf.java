package simulation.wildlife.predators;

import simulation.wildlife.Animal;

import java.util.HashSet;
import java.util.Set;

import static simulation.Params.*;

public class Wolf extends Predator {

    public Wolf() {
        wolvesBorn++;
        this.weight = wolfWeight;
        this.maxInCell = wolvesInCell;
        this.maxDistance = wolfMaxDistance;
        init();
        this.diet = wolfDiet;
    }

    @Override
    protected Set<Animal> getOffspring() {
        return new HashSet<>() {{
            add(new Wolf());
        }};
    }

    @Override
    public void die() {
        super.die();
        wolvesDied++;
    }
}
