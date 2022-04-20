package domain.animals;

import domain.terrain.Cell;
import domain.terrain.Direction;

import java.util.concurrent.ThreadLocalRandom;

public abstract class Animal {

    private boolean dead;
    int bornHungerLevel = 9;
    int maxHungerLevel = 10;
    int reproduceHungerLevel = 5;
    private boolean reproduced;

    public boolean isDead() {
        return dead;
    }

    public void die() {
        this.dead = true;
    }

    public void setReproduced(boolean reproduced) {
        this.reproduced = reproduced;
    }

    public boolean isReadyToReproduce() {
        return !reproduced && bornHungerLevel < reproduceHungerLevel;
    }

    public void increaseHunger(Cell cell) {
        if (dead) {
            return;
        }
        bornHungerLevel++;
        if (bornHungerLevel >= maxHungerLevel) {
            die();
        }

        if (isDead()) {
            cell.animals.remove(this);
        }
    }

    public void decreaseHunger() {
        if (dead || bornHungerLevel <= 0) {
            return;
        }
        bornHungerLevel--;
    }

    public abstract void feed(Cell cell);

    public Direction getDirection() {
        if (dead) {
            return Direction.NONE;
        }

        int random = ThreadLocalRandom.current().nextInt(100);
        if (random < 20) {
            return Direction.LEFT;
        } else if (random < 40) {
            return Direction.RIGHT;
        } else if (random < 60) {
            return Direction.UP;
        } else if (random < 80) {
            return Direction.DOWN;
        } else {
            return Direction.NONE;
        }
    }

    public Animal reproduce(Cell cell) {
        if (!isReadyToReproduce()) {
            return null;
        }
        setReproduced(true);

        boolean b = ThreadLocalRandom.current().nextBoolean();
        if (b) {
            return null;
        }

        for (Animal otherAnimal : cell.animals) {
            if (!otherAnimal.isReadyToReproduce() || !this.getClass().equals(otherAnimal.getClass())) {
                continue;
            }
            otherAnimal.setReproduced(true);
            return getChild();
        }

        return null;
    }

    abstract Animal getChild();
}
