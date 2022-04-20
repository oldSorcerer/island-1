package domain.terrain;

import domain.animals.Animal;
import domain.plants.Plant;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArraySet;

import static java.util.Objects.nonNull;

public class Cell {
    public final Island island;
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
                animal.feed(Cell.this);
                Animal reproduced = animal.reproduce(Cell.this);
                if (nonNull(reproduced)) {
                    newLivestock.add(reproduced);
                }

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
