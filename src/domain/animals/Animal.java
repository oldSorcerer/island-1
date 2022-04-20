package domain.animals;

import domain.terrain.Cell;
import domain.terrain.Direction;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Animal {

    private boolean dead;
    private int hungerLevel = 9;
    protected int maxHungerLevel = 10;
    private int reproduceHungerLevel = 5;
    private boolean reproduced;
    protected int weight;

    public int getWeight() {
        return weight;
    }

    public void die() {
        this.dead = true;
    }

    public void setReproduced(boolean reproduced) {
        this.reproduced = reproduced;
    }

    private boolean isReadyToReproduce() {
        return !reproduced && hungerLevel < reproduceHungerLevel;
    }

    protected void increaseHunger(Cell cell) {
        if (dead) {
            return;
        }
        hungerLevel++;
        if (hungerLevel >= maxHungerLevel) {
            die();
            cell.animals.remove(this);
        }
    }

    protected void decreaseHunger(int points) {
        if (dead || hungerLevel <= 0) {
            return;
        }
        hungerLevel -= points;
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

    public Set<Animal> reproduce(Cell cell) {
        if (!isReadyToReproduce()) {
            return Collections.emptySet();
        }
        setReproduced(true);

        boolean b = ThreadLocalRandom.current().nextBoolean();
        if (b) {
            return Collections.emptySet();
        }

        for (Animal otherAnimal : cell.animals) {
            if (!otherAnimal.isReadyToReproduce() || !this.getClass().equals(otherAnimal.getClass())) {
                continue;
            }
            otherAnimal.setReproduced(true);
            return getChild();
        }

        return Collections.emptySet();
    }

    protected abstract Set<Animal> getChild();
}
