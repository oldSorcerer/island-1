package domain.animals.herbivores;

import domain.animals.Animal;
import domain.plants.Plant;
import domain.terrain.Cell;

import static domain.Params.plantWeight;

public abstract class Herbivore extends Animal {

    @Override
    public void pinchGrass(Cell cell) {
        if (!cell.plants.isEmpty()) {
            cell.plants.remove(cell.plants.iterator().next());
            Plant.plantsEaten++;
            increaseSaturation(plantWeight);
        } else {
            decreaseSaturation(cell);
        }
    }

    @Override
    protected void hunt(Cell cell) {
    }
}
