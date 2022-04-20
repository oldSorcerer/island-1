package domain.terrain;

import domain.animals.Animal;
import domain.plants.Plant;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.*;

public class Island {
    public int plantsGrown;
    public int plantsEaten;
    public int animalsBorn;
    public int animalsDied;
    public final int width = 100;
    public final int height = 20;
    private final int STEP_PERIOD = 500;
    private final int maxCellPlants = 2000;
    public final Cell[][] cells = new Cell[height][width];

    public Island() {
        initCells();
        growPlants();
        populateAnimals();
    }

    private void initCells() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                cells[y][x] = new Cell(this, x, y);
            }
        }
    }

    private void growPlants() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int random = ThreadLocalRandom.current().nextInt(50, 1000);
                for (int i = 0; i < random; i++) {
                    cells[y][x].plants.add(new Plant(this));
                }
            }
        }
    }

    private void populateAnimals() {
        for (int i = 0; i < 100; i++) {
            cells[0][0].animals.add(new Animal(this));
            cells[0][width - 1].animals.add(new Animal(this));
            cells[height - 1][0].animals.add(new Animal(this));
            cells[height - 1][width - 1].animals.add(new Animal(this));
        }
    }

    public ExecutorService runPlantsGrowth() {
        ScheduledExecutorService plantGrowth = Executors.newScheduledThreadPool(width * height);
        plantGrowth.scheduleAtFixedRate(new PlantGrowth(), STEP_PERIOD, STEP_PERIOD * 5, TimeUnit.MILLISECONDS);
        return plantGrowth;
    }

    public void runAnimalLifeCycle() throws InterruptedException {
        for (int i = 0; i < 1000; i++) {
            System.out.printf("Step: %d\t\t animals(born/died): %d/%d\t\t plants(grown/eaten): %d/%d\n",
                    i, animalsBorn, animalsDied, plantsGrown, plantsEaten);
            printAnimals();
            nextLifeCycle();
            Thread.sleep(STEP_PERIOD);
        }
    }

    private void printAnimals() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Cell cell = cells[y][x];
                System.out.print(cell.animals.isEmpty()
                        ? cell.plants.isEmpty() ? "_" : "."
                        : cell.animals.size() > 9 ? "*" : cell.animals.size());
            }
            System.out.println();
        }
    }

    private void nextLifeCycle() {
        ExecutorService service = Executors.newCachedThreadPool();
        Set<Future<Map<Cell, Set<Animal>>>> futures = new HashSet<>();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                futures.add(service.submit(cells[y][x].new AnimalLifeCycle()));
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

    private class PlantGrowth implements Runnable {
        @Override
        public void run() {
            try {
                for (int y = 0; y < height; y++) {
                    for (int x = 0; x < width; x++) {
                        if (ThreadLocalRandom.current().nextBoolean()) {
                            continue;
                        }
                        int factor = maxCellPlants / 2 - Math.abs(cells[y][x].plants.size() - maxCellPlants / 2);
                        int numberOfNewPlants = factor != 0 ? ThreadLocalRandom.current().nextInt(factor) : 1;

                        for (int i = 0; i < numberOfNewPlants; i++) {
                            cells[y][x].plants.add(new Plant(Island.this));
                        }
                    }
                }
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }
}
