package simulation.terrain;

import simulation.wildlife.Animal;
import simulation.wildlife.Plant;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;

import static simulation.Params.HEIGHT;
import static simulation.Params.WIDTH;

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

    private Cell getDestinationCell(Cell start, List<Direction> directions) {
        Cell destination;
        for (Direction direction : directions) {
            if (direction == Direction.LEFT && start.x > 0) {
                destination = island.cells[start.y][start.x - 1];
            } else if (direction == Direction.RIGHT && start.x < WIDTH - 1) {
                destination = island.cells[start.y][start.x + 1];
            } else if (direction == Direction.UP && start.y > 0) {
                destination = island.cells[start.y - 1][start.x];
            } else if (direction == Direction.DOWN && start.y < HEIGHT - 1) {
                destination = island.cells[start.y + 1][start.x];
            } else {
                continue;
            }

            start = destination;
        }

        return start;
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

                List<Direction> directions = animal.getDirection();
                Cell nextCell = getDestinationCell(Cell.this, directions);
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
