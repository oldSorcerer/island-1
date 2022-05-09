package simulation.terrain;

import simulation.plants.Plant;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class Island {
    public final int width = 100;
    public final int height = 20;
    private final int STEP_PERIOD = 500;
    private final int maxCellPlants = 100;
    public final Cell[][] cells = new Cell[height][width];

    public Island() {
        initCells();
        growPlants();
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
                int random = ThreadLocalRandom.current().nextInt(maxCellPlants / 2);
                for (int i = 0; i < random; i++) {
                    cells[y][x].plants.add(new Plant());
                }
            }
        }
    }

    public void run() {
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(2);
        executorService.scheduleAtFixedRate(new PlantGrowth(), STEP_PERIOD, STEP_PERIOD * 5, TimeUnit.MILLISECONDS);
    }

    private void print() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Cell cell = cells[y][x];
                int plantsCount = cells[y][x].plants.size();
                if (plantsCount == 0) {
                    System.out.print("➖");
                } else if (plantsCount == 1) {
                    System.out.print("\uD83C\uDF31");
                } else if (plantsCount < 20) {
                    System.out.print("\uD83C\uDF3F");
                } else {
                    System.out.print("\uD83C\uDF33");
                }
            }
            System.out.println();
        }
    }

    private class PlantGrowth implements Runnable {
        @Override
        public void run() {
            System.out.printf("Plants(grown/eaten): %d/%d\n", Plant.plantsGrown, Plant.plantsEaten);
            print();

            try {
                for (int y = 0; y < height; y++) {
                    for (int x = 0; x < width; x++) {
                        if (ThreadLocalRandom.current().nextBoolean()) {
                            continue;
                        }
                        int factor = maxCellPlants / 2 - Math.abs(cells[y][x].plants.size() - maxCellPlants / 2) + 2;
                        int numberOfNewPlants = factor != 0 ? ThreadLocalRandom.current().nextInt(factor) : 1;

                        for (int i = 0; i < numberOfNewPlants; i++) {
                            cells[y][x].plants.add(new Plant());
                        }
                    }
                }
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }
}
