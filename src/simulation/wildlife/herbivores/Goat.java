package simulation.wildlife.herbivores;

import simulation.wildlife.Animal;

import java.util.HashSet;
import java.util.Set;

import static simulation.Params.*;

public class Goat extends Herbivore {

    public Goat() {
        super(goatWeight, goatsInCell, goatMaxDistance, null);
        goatsBorn++;
    }

    @Override
    protected Set<Animal> getOffspring() {
        return new HashSet<>() {{
            add(new Goat());
        }};
    }

    @Override
    public void die() {
        super.die();
        goatsDied++;
    }
}
