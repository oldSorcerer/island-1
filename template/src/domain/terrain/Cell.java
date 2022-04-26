package domain.terrain;

import domain.plants.Plant;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class Cell {
    public final Island island;
    private final int x;
    private final int y;
    public Set<Plant> plants = new CopyOnWriteArraySet<>();

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
}
