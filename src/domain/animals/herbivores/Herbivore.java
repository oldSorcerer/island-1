package domain.animals.herbivores;

import domain.animals.Animal;
import domain.plants.Plant;
import domain.terrain.Cell;

public abstract class Herbivore extends Animal {

    @Override
    public void feed(Cell cell) {
        if (!cell.plants.isEmpty()) {
            cell.plants.remove(cell.plants.iterator().next());
            Plant.plantsEaten++;
            decreaseHunger(1);
        } else {
            increaseHunger(cell);
        }
    }
}
