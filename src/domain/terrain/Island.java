package domain.terrain;

import domain.animals.Animal;
import domain.plants.Plant;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.*;

public class Island {
    public static int plantsGrown;
    public static int plantsEaten;
    public static int animalsBorn;
    public static int animalsDied;
    public final int width = 100;
    public final int height = 20;
    public final Cell[][] cells = new Cell[height][width];

    public static Island island = new Island();

    public Island() {
        initCells();
        growPlants();
        populateAnimals();
    }

    private void initCells() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                cells[y][x] = new Cell(x, y);
            }
        }
    }

    private void growPlants() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int random = ThreadLocalRandom.current().nextInt(50, 1000);
                for (int i = 0; i < random; i++) {
                    cells[y][x].plants.add(new Plant());
                }
            }
        }
    }

    private void populateAnimals() {
        for (int i = 0; i < 100; i++) {
            cells[0][0].animals.add(new Animal());
            cells[0][width - 1].animals.add(new Animal());
            cells[height - 1][0].animals.add(new Animal());
            cells[height - 1][width - 1].animals.add(new Animal());
        }
    }

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 1000; i++) {
            System.out.printf("%d\t\t animals(born/died): %d/%d\t\t plants(grown/eaten): %d/%d\n",
                    i, animalsBorn, animalsDied, plantsGrown, plantsEaten);
            printAnimals();
            resettlement();
            Thread.sleep(500);
        }
    }

    private static void printAnimals() {
        for (int y = 0; y < island.height; y++) {
            for (int x = 0; x < island.width; x++) {
                Cell cell = island.cells[y][x];
                System.out.print(cell.animals.isEmpty()
                        ? cell.plants.isEmpty() ? "_" : "."
                        : cell.animals.size() > 9 ? "*" : cell.animals.size());
            }
            System.out.println();
        }
    }

    private static void resettlement() {
        ExecutorService service = Executors.newCachedThreadPool();
        Set<Future<Map<Cell, Set<Animal>>>> futures = new HashSet<>();
        for (int y = 0; y < island.height; y++) {
            for (int x = 0; x < island.width; x++) {
                futures.add(service.submit(island.cells[y][x].new LifeCycle()));
            }
        }
        service.shutdown();
        try {
            if (service.awaitTermination(10, TimeUnit.MINUTES)) {
                for (Future<Map<Cell, Set<Animal>>> future : futures) {
                    for (Map.Entry<Cell, Set<Animal>> entry : future.get().entrySet()) {
                        entry.getKey().animals.addAll(entry.getValue());
                    }
                }
            } else {
                throw new TimeoutException();
            }
        } catch (InterruptedException | TimeoutException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
