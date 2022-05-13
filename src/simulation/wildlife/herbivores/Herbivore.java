package simulation.wildlife.herbivores;

import simulation.terrain.Cell;
import simulation.wildlife.Animal;
import simulation.wildlife.Plant;

import java.util.Map;

import static simulation.Params.PLANT_WEIGHT;

public abstract class Herbivore extends Animal {

    public Herbivore(double weight, int maxInCell, int maxDistance, Map<Class, Integer> diet) {
        super(weight, maxInCell, maxDistance, diet);
    }

    @Override
    public void pinchGrass(Cell cell) {
        if (!cell.plants.isEmpty()) {
            cell.plants.remove(cell.plants.iterator().next());
            Plant.plantsEaten++;
            increaseSaturation(PLANT_WEIGHT);
        } else {
            decreaseSaturation(cell);
        }
    }

    @Override
    protected void hunt(Cell cell) {
    }
}
