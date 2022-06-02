package simulation.terrain;

import simulation.wildlife.Animal;
import simulation.wildlife.Plant;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Cell {
    private final Island island;
    public final int x;
    public final int y;
    public List<Plant> plants = new CopyOnWriteArrayList<>();
    public List<Animal> animals = new CopyOnWriteArrayList<>();

    public Cell(Island island, int x, int y) {
        this.island = island;
        this.x = x;
        this.y = y;
    }

    public class CellLifeCycle implements Callable<Map<Cell, Set<Animal>>> {

        @Override
        public Map<Cell, Set<Animal>> call() {
            Map<Cell, Set<Animal>> forResettlement = new HashMap<>();
            Set<Animal> newLivestock = new HashSet<>();

            Collections.shuffle(animals, ThreadLocalRandom.current());
            for (Animal animal : animals) {
                // едим
                animal.feed(Cell.this);

                // размножаемся
                Set<Animal> reproduced = animal.reproduce(Cell.this);
                newLivestock.addAll(reproduced);

                // передвигаемся
                List<Direction> directions = animal.getDirection();
                Cell nextCell = island.getDestinationCell(Cell.this, directions);
                if (nextCell != Cell.this) {
                    forResettlement.computeIfAbsent(nextCell, (v) -> new HashSet<>()).add(animal);
                    animals.remove(animal);
                }
            }

            animals.addAll(newLivestock);
            return forResettlement;
        }
    }
}
