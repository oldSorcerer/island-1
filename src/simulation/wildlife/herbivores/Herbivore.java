package simulation.wildlife.herbivores;

import simulation.terrain.Cell;
import simulation.wildlife.Animal;

import java.util.Map;

public abstract class Herbivore extends Animal {

    public Herbivore(double weight, int maxInCell, int maxDistance, Map<Class, Integer> diet) {
        super(weight, maxInCell, maxDistance, diet);
    }

    @Override
    public void feed(Cell cell) {
        pinchGrass(cell);
    }
}
