package domain.terrain;

import domain.animals.Animal;
import domain.plants.Plant;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;

import static java.util.Objects.nonNull;

public class Cell {
    public final Island island;
    private final int x;
    private final int y;
    public List<Plant> plants = new CopyOnWriteArrayList<>();
    public List<Animal> animals = new CopyOnWriteArrayList<>();

    public Cell(Island island, int x, int y) {
        this.island = island;
        this.x = x;
        this.y = y;
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

    public class CellLifeCycle implements Callable<Map<Cell, Set<Animal>>> {

        @Override
        public Map<Cell, Set<Animal>> call() {
            Map<Cell, Set<Animal>> forResettlement = new HashMap<>();
            Set<Animal> newLivestock = new HashSet<>();

            Collections.shuffle(animals, ThreadLocalRandom.current());
            for (Animal animal : animals) {
                animal.feed(Cell.this);
                Set<Animal> reproduced = animal.reproduce(Cell.this);
                newLivestock.addAll(reproduced);

                Direction direction = animal.getDirection();
                Cell nextCell = getNextCell(direction);
                if (nonNull(nextCell)) {
                    forResettlement.computeIfAbsent(nextCell, (v) -> new HashSet<>()).add(animal);
                    animals.remove(animal);
                }
            }

            animals.addAll(newLivestock);
            return forResettlement;
        }
    }
}
