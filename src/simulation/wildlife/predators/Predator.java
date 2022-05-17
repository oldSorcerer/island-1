package simulation.wildlife.predators;

import simulation.terrain.Cell;
import simulation.wildlife.Animal;

import java.util.Map;

public abstract class Predator extends Animal {

    public Predator(double weight, int maxInCell, int maxDistance, Map<Class, Integer> diet) {
        super(weight, maxInCell, maxDistance, diet);
    }

    @Override
    public void feed(Cell cell) {
        hunt(cell);
    }
}
