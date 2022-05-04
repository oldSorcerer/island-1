package domain.animals;

import domain.terrain.Cell;
import domain.terrain.Direction;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Animal {

    private boolean dead;
    protected int maxSaturation;
    protected int saturation;
    private boolean reproduced;
    protected int weight;

    protected void init() {
        maxSaturation = weight * 2;
        saturation = weight;
    }

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
        return !reproduced && saturation > maxSaturation / 2;
    }

    protected void decreaseSaturation(Cell cell) {
        if (dead) {
            return;
        }
        saturation--;
        if (saturation <= 0) {
            die();
            cell.animals.remove(this);
        }
    }

    protected void increaseSaturation(int points) {
        if (dead || saturation <= 0) {
            return;
        }
        saturation = Math.min(saturation + points, maxSaturation);
    }

    public void feed(Cell cell) {
        pinchGrass(cell);
        hunt(cell);
    }

    protected abstract void pinchGrass(Cell cell);

    protected abstract void hunt(Cell cell);

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
        if (random < getWeight()) {
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
