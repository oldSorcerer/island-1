package simulation.wildlife;

import simulation.terrain.Cell;
import simulation.terrain.Direction;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static simulation.Params.PLANT_WEIGHT;

public abstract class Animal {

    private boolean dead;
    private final double maxSaturation;
    private double saturation;
    private boolean reproduced;
    private final double weight;
    private final int maxInCell;
    private final int maxDistance;
    private final Map<Class, Integer> diet;

    public Animal(double weight, int maxInCell, int maxDistance, Map<Class, Integer> diet) {
        this.weight = weight;
        this.maxInCell = maxInCell;
        this.maxDistance = maxDistance;
        this.diet = diet;

        this.maxSaturation = this.weight * 2 + 1;
        this.saturation = this.weight + 1;
    }

    protected void die() {
        this.dead = true;
    }

    private boolean isReadyToReproduce() {
        return !reproduced && saturation > maxSaturation / 2;
    }

    protected void decreaseSaturation(Cell cell) {
        if (dead) {
            return;
        }

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

    public abstract void feed(Cell cell);

    protected void pinchGrass(Cell cell) {
        if (!cell.plants.isEmpty()) {
            cell.plants.remove(cell.plants.iterator().next());
            Plant.plantsEaten++;
            increaseSaturation(PLANT_WEIGHT);
        } else {
            decreaseSaturation(cell);
        }
    }

    protected void hunt(Cell cell) {
        if (saturation >= maxSaturation) {
            decreaseSaturation(cell);
            return;
        }

        do {
            Optional<Animal> prey = findPray(cell);
            if (prey.isPresent()) {
                Animal snack = prey.get();
                cell.animals.remove(snack);
                snack.die();
                increaseSaturation(snack.weight);
                feed(cell);
            } else {
                break;
            }
        } while (saturation < maxSaturation / 2);

        decreaseSaturation(cell);
    }

    private Optional<Animal> findPray(Cell cell) {
        for (Animal candidate : cell.animals) {
            if (diet.containsKey(candidate.getClass())) {
                int random = ThreadLocalRandom.current().nextInt(100);
                if (random < diet.get(candidate.getClass())) {
                    return Optional.of(candidate);
                }
            }
        }

        return Optional.empty();
    }

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
            otherAnimal.reproduced = true;
            return getOffspring();
        }

        return Collections.emptySet();
    }

    protected abstract Set<Animal> getOffspring();
}
