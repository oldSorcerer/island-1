package domain.terrain;

import domain.animals.Animal;
import domain.plants.Plant;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.ThreadLocalRandom;

import static java.util.Objects.nonNull;

public class Cell {
    private final int x;
    private final int y;
    public Set<Plant> plants = new CopyOnWriteArraySet<>();
    public Set<Animal> animals = new CopyOnWriteArraySet<>();

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public class LifeCycle implements Callable<Map<Cell, Set<Animal>>> {

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

                Cell destinationCell = animal.getDestinationCell(x, y);
                if (nonNull(destinationCell)) {
                    forResettlement.computeIfAbsent(destinationCell, (v) -> new HashSet<>()).add(animal);
                    animals.remove(animal);
                }
            }

            animals.addAll(newLivestock);
            return forResettlement;
        }

        private void resetReproduction() {
            animals.forEach(a -> a.setReproduced(false));
        }

        private void feed(Animal animal) {
            if (!plants.isEmpty()) {
                plants.remove(plants.iterator().next());
                animal.decreaseHunger();
                Island.plantsEaten++;
            } else {
                animal.increaseHunger();
                if (animal.isDead()) {
                    animals.remove(animal);
                    Island.animalsDied++;
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
                return new Animal();
            }

            return null;
        }
    }
}
