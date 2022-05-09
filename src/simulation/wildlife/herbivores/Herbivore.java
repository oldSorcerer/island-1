package simulation.wildlife.herbivores;

import simulation.wildlife.Animal;
import simulation.wildlife.Plant;
import simulation.terrain.Cell;

import static simulation.Params.PLANT_WEIGHT;

public abstract class Herbivore extends Animal {

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
