package simulation.wildlife.herbivores;

import simulation.wildlife.Animal;
import simulation.terrain.Cell;

import java.util.HashSet;
import java.util.Set;

import static simulation.Params.*;

public class Caterpillar extends Herbivore {

    public Caterpillar() {
        caterpillarsBorn++;
        this.weight = caterpillarWeight;
        this.maxInCell = caterpillarsInCell;
        this.maxDistance = caterpillarMaxDistance;
        init();
    }

    @Override
    protected Set<Animal> getOffspring() {
        return new HashSet<>() {{
            add(new Caterpillar());
        }};
    }

    @Override
    public void die() {
        super.die();
        caterpillarsDied++;
    }

    @Override
    public void pinchGrass(Cell cell) {
        if (!cell.plants.isEmpty()) {
            increaseSaturation(PLANT_WEIGHT);
        } else {
            decreaseSaturation(cell);
        }
    }
}
