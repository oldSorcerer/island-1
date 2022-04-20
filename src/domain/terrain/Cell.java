package domain.terrain;

import domain.animals.Animal;
import domain.plants.Plant;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.ThreadLocalRandom;

import static java.util.Objects.nonNull;

public class Cell {
    private final Island island;
    private final int x;
    private final int y;
    public Set<Plant> plants = new CopyOnWriteArraySet<>();
    public Set<Animal> animals = new CopyOnWriteArraySet<>();

    public Cell(Island island, int x, int y) {
        this.island = island;
        this.x = x;
        this.y = y;
    }

    private void resetReproduction() {
        animals.forEach(a -> a.setReproduced(false));
    }

    private Cell getNextCell(Direction direction) {
        if (direction == Direction.LEFT && x > 0) {
            return island.cells[y][x - 1];
        } else if (direction == Direction.RIGHT && x < island.width - 1) {
            return island.cells[y][x + 1];
        } else if (direction == Direction.UP && y > 0) {
            return island.cells[y - 1][x];
        } else if (direction == Direction.DOWN && y < island.height - 1) {
            return island.cells[y + 1][x];
        } else {
            return null;
        }
    }

    public class AnimalLifeCycle implements Callable<Map<Cell, Set<Animal>>> {

        @Override
        public Map<Cell, Set<Animal>> call() {
            resetReproduction();

            Map<Cell, Set<Animal>> forResettlement = new HashMap<>();
            Set<Animal> newLivestock = new HashSet<>();

            for (Animal animal : animals) {
                feed(animal);
                Animal reproduced = reproduce(animal);
                if (nonNull(reproduced)) {
                    newLivestock.add(reproduced);
                }

                Direction direction = animal.getDirection(x, y);
                Cell nextCell = getNextCell(direction);
                if (nonNull(nextCell)) {
                    forResettlement.computeIfAbsent(nextCell, (v) -> new HashSet<>()).add(animal);
                    animals.remove(animal);
                }
            }

            animals.addAll(newLivestock);
            return forResettlement;
        }

        private void feed(Animal animal) {
            if (!plants.isEmpty()) {
                plants.remove(plants.iterator().next());
                animal.decreaseHunger();
                island.plantsEaten++;
            } else {
                animal.increaseHunger();
                if (animal.isDead()) {
                    animals.remove(animal);
                    island.animalsDied++;
                }
            }
        }

        private Animal reproduce(Animal animal) {
            if (!animal.isReadyToReproduce()) {
                return null;
            }
            animal.setReproduced(true);

            boolean b = ThreadLocalRandom.current().nextBoolean();
            if (b) {
                return null;
            }

            for (Animal otherAnimal : animals) {
                if (!otherAnimal.isReadyToReproduce()) {
                    continue;
                }
                otherAnimal.setReproduced(true);
                return new Animal(island);
            }

            return null;
        }
    }
}
