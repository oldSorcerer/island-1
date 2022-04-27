package domain.terrain;

import domain.animals.Animal;
import domain.animals.herbivores.Deer;
import domain.animals.herbivores.Rabbit;
import domain.animals.predators.Predator;
import domain.animals.predators.Wolf;
import domain.plants.Plant;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.*;

public class Island {
    public int plantsGrown;
    public int plantsEaten;
    public final int width = 100;
    public final int height = 20;
    private final int ANIMAL_STEP_PERIOD = 500;
    private final int PLANT_STEP_PERIOD = ANIMAL_STEP_PERIOD * 5;
    private final int maxCellPlants = 100;
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
                int random = ThreadLocalRandom.current().nextInt(maxCellPlants / 10, maxCellPlants / 2);
                for (int i = 0; i < random; i++) {
                    cells[y][x].plants.add(new Plant());
                }
            }
        }
    }

    private void populateAnimals() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int deerRrandom = ThreadLocalRandom.current().nextInt(100);
                if (deerRrandom < 10) {
                    cells[y][x].animals.add(new Deer());
                    cells[y][x].animals.add(new Deer());
                    cells[y][x].animals.add(new Deer());
                    cells[y][x].animals.add(new Deer());
                }

                int wolfRrandom = ThreadLocalRandom.current().nextInt(100);
                if (wolfRrandom < 10) {
                    cells[y][x].animals.add(new Wolf());
                    cells[y][x].animals.add(new Wolf());
                    cells[y][x].animals.add(new Wolf());
                    cells[y][x].animals.add(new Wolf());
                }
            }
        }
    }

    public void run() {
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(2);
        executorService.scheduleAtFixedRate(new PlantGrowth(), PLANT_STEP_PERIOD, PLANT_STEP_PERIOD, TimeUnit.MILLISECONDS);
        executorService.scheduleAtFixedRate(new AnimalsLifeCycle(), 0, ANIMAL_STEP_PERIOD, TimeUnit.MILLISECONDS);
    }

    private void printIsland() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Cell cell = cells[y][x];
                System.out.print(cell.animals.isEmpty()
                        ? cell.plants.isEmpty()
                        ? "➖" // empty
                        : cell.plants.size() < 10 ? "\uD83C\uDF31" : "\uD83C\uDF3F" // only plants
                        : cell.animals.stream().allMatch(a -> a instanceof Predator) ? "\uD83D\uDC3A" : "\uD83E\uDD8C");
            }
            System.out.println();
        }
    }

    private void nextLifeCycle() {
        ExecutorService service = Executors.newCachedThreadPool();
        Set<Future<Map<Cell, Set<Animal>>>> resettlementGroups = new HashSet<>();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                resettlementGroups.add(service.submit(cells[y][x].new CellLifeCycle()));
            }
        }
        service.shutdown();

        try {
            if (service.awaitTermination(10, TimeUnit.MINUTES)) {
                for (Future<Map<Cell, Set<Animal>>> cellGroups : resettlementGroups) {
                    for (Map.Entry<Cell, Set<Animal>> group : cellGroups.get().entrySet()) {
                        group.getKey().animals.addAll(group.getValue());
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

    class AnimalsLifeCycle implements Runnable {

        private int step;

        @Override
        public void run() {
            System.out.printf("Step: %d\t\t Rabbit(born/died): %d/%d\t\t Deer(born/died): %d/%d\t\t Wolves(born/died): %d/%d\t\t Plants(grown/eaten): %d/%d\n", step,
                    Rabbit.rabbitsBorn, Rabbit.rabbitsDied,
                    Deer.deerBorn, Deer.deerDied,
                    Wolf.wolvesBorn, Wolf.wolvesDied,
                    plantsGrown, plantsEaten);
            printIsland();
            nextLifeCycle();
            step++;
        }
    }
}
