package domain.animals.predators;

import domain.animals.Animal;
import domain.terrain.Cell;

import java.util.HashSet;
import java.util.Set;

public class Wolf extends Predator {

    public static int wolvesBorn;
    public static int wolvesDied;

    public Wolf() {
        wolvesBorn++;
        this.maxHungerLevel = 20;
    }

    @Override
    protected Set<Animal> getChild() {
        return new HashSet<>() {{
            add(new Wolf());
        }};
    }

    @Override
    public void die() {
        super.die();
        wolvesDied++;
    }

    @Override
    public void increaseHunger(Cell cell) {
        super.increaseHunger(cell);
    }
}
