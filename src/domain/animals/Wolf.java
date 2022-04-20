package domain.animals;

import domain.terrain.Cell;

public class Wolf extends Predator {

    public static int wolvesBorn;
    public static int wolvesDied;

    public Wolf() {
        wolvesBorn++;
        this.maxHungerLevel = 20;
    }

    @Override
    Wolf getChild() {
        return new Wolf();
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
