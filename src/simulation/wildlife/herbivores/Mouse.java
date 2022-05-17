package simulation.wildlife.herbivores;

import simulation.terrain.Cell;
import simulation.wildlife.Animal;

import java.util.HashSet;
import java.util.Set;

import static simulation.Params.*;

public class Mouse extends Herbivore {

    public Mouse() {
        super(mouseWeight, miceInCell, mouseMaxDistance, mouseDiet);
        miceBorn++;
    }

    @Override
    protected Set<Animal> getOffspring() {
        return new HashSet<>() {{
            add(new Mouse());
        }};
    }

    @Override
    public void die() {
        super.die();
        miceDied++;
    }

    @Override
    public void feed(Cell cell) {
        super.feed(cell);
        hunt(cell);
    }
}
