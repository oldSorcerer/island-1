package domain.animals;

import domain.terrain.Cell;

public abstract class Herbivore extends Animal {

    @Override
    public void feed(Cell cell) {
        if (!cell.plants.isEmpty()) {
            eat(cell);
        } else {
            increaseHunger(cell);
        }
    }

    void eat(Cell cell) {
        cell.plants.remove(cell.plants.iterator().next());
        cell.island.plantsEaten++;
        decreaseHunger();
    }
}
