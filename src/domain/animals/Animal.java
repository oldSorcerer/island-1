package domain.animals;

import domain.terrain.Cell;
import domain.terrain.Direction;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Animal {

    private boolean dead;
    protected int maxHungerLevel = 10;
    protected int hungerLevel = maxHungerLevel / 2;
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
        return !reproduced && hungerLevel < maxHungerLevel / 4;
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
        hungerLevel = hungerLevel > points ? hungerLevel - points : 0;
    }

    public abstract void feed(Cell cell);

    public Direction getDirection() {
        if (dead) {
            return Direction.NONE;
        }

        int random = ThreadLocalRandom.current().nextInt(100);
        if (random < 10) {
            return Direction.LEFT;
        } else if (random < 20) {
            return Direction.RIGHT;
        } else if (random < 30) {
            return Direction.UP;
        } else if (random < 40) {
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

        int random = ThreadLocalRandom.current().nextInt(100);
        if (random < 100 - getWeight()) {
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
