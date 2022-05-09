package simulation.wildlife;

import simulation.terrain.Cell;
import simulation.terrain.Direction;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public abstract class Animal {

    private boolean dead;
    protected double maxSaturation;
    protected double saturation;
    private boolean reproduced;
    protected double weight;
    protected int maxInCell;
    protected int maxDistance;
    protected Map<Class, Integer> diet;

    protected void init() {
        maxSaturation = weight * 2 + 1;
        saturation = weight + 1;
    }

    public double getWeight() {
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
//        saturation--;
        saturation = saturation - (0.05 * maxSaturation);
        if (saturation <= 0) {
            die();
            cell.animals.remove(this);
        }
    }

    protected void increaseSaturation(double points) {
        if (dead || saturation <= 0) {
            return;
        }
        saturation = Math.min(saturation + points, maxSaturation);
        reproduced = false;
    }

    public void feed(Cell cell) {
        pinchGrass(cell);
        hunt(cell);
    }

    protected abstract void pinchGrass(Cell cell);

    protected abstract void hunt(Cell cell);

    public List<Direction> getDirection() {
        if (dead) {
            return Collections.emptyList();
        }

        List<Direction> result = new ArrayList<>();
        for (int i = 0; i < maxDistance; i++) {
            int random = ThreadLocalRandom.current().nextInt(100);
            if (random < 10) {
                result.add(Direction.LEFT);
            } else if (random < 20) {
                result.add(Direction.RIGHT);
            } else if (random < 30) {
                result.add(Direction.UP);
            } else if (random < 40) {
                result.add(Direction.DOWN);
            } else {
                result.add(Direction.NONE);
            }
        }

        return result;
    }

    public Set<Animal> reproduce(Cell cell) {
        if (!isReadyToReproduce() || ThreadLocalRandom.current().nextBoolean()) {
            return Collections.emptySet();
        }

        Set<Animal> candidates = cell.animals.stream()
                .filter(animal -> animal.getClass().equals(getClass()))
                .collect(Collectors.toSet());

        if (candidates.size() >= maxInCell) {
            return Collections.emptySet();
        }

        for (Animal otherAnimal : candidates) {
            if (!otherAnimal.isReadyToReproduce()) {
                continue;
            }
            otherAnimal.setReproduced(true);
            return getOffspring();
        }

        return Collections.emptySet();
    }

    protected abstract Set<Animal> getOffspring();
}
