package simulation.wildlife.predators;

import simulation.wildlife.Animal;

import java.util.HashSet;
import java.util.Set;

import static simulation.Params.*;

public class Eagle extends Predator {

    public Eagle() {
        super(eagleWeight, eaglesInCell, eagleMaxDistance, eagleDiet);
        eaglesBorn++;
    }

    @Override
    protected Set<Animal> getOffspring() {
        return new HashSet<>() {{
            add(new Eagle());
        }};
    }

    @Override
    public void die() {
        super.die();
        eaglesDied++;
    }
}