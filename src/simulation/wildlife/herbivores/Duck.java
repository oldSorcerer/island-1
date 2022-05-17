package simulation.wildlife.herbivores;

import simulation.terrain.Cell;
import simulation.wildlife.Animal;

import java.util.HashSet;
import java.util.Set;

import static simulation.Params.*;

public class Duck extends Herbivore {

    public Duck() {
        super(duckWeight, ducksInCell, duckMaxDistance, duckDiet);
        ducksBorn++;
    }

    @Override
    protected Set<Animal> getOffspring() {
        return new HashSet<>() {{
            add(new Duck());
        }};
    }

    @Override
    public void die() {
        super.die();
        ducksDied++;
    }

    @Override
    public void feed(Cell cell) {
        super.feed(cell);
        hunt(cell);
    }
}
