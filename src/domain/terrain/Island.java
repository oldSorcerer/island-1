package domain.terrain;

import domain.Params;
import domain.animals.Animal;
import domain.animals.herbivores.Deer;
import domain.animals.herbivores.Rabbit;
import domain.animals.herbivores.Rat;
import domain.animals.predators.Fox;
import domain.animals.predators.Wolf;
import domain.plants.Plant;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class Island {
    public final int width = 100;
    public final int height = 20;
    private final int ANIMAL_STEP_PERIOD = 500;
    private final int PLANT_STEP_PERIOD = ANIMAL_STEP_PERIOD * 5;
    private final int maxCellPlants = 200;
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
                int random = ThreadLocalRandom.current().nextInt(100);
                if (random < 10) {
                    cells[y][x].animals.add(new Deer());
                    cells[y][x].animals.add(new Deer());
                }

                random = ThreadLocalRandom.current().nextInt(100);
                if (random < 1) {
                    cells[y][x].animals.add(new Wolf());
                    cells[y][x].animals.add(new Wolf());
                }

                random = ThreadLocalRandom.current().nextInt(100);
                if (random < 2) {
                    cells[y][x].animals.add(new Fox());
                    cells[y][x].animals.add(new Fox());
                }

                random = ThreadLocalRandom.current().nextInt(100);
                if (random < 20) {
                    cells[y][x].animals.add(new Rabbit());
                    cells[y][x].animals.add(new Rabbit());
                }

                random = ThreadLocalRandom.current().nextInt(100);
                if (random < 60) {
                    cells[y][x].animals.add(new Rat());
                    cells[y][x].animals.add(new Rat());
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
                if (cell.animals.isEmpty()) {
                    if (cell.plants.isEmpty()) {
                        System.out.print("➖");
                    } else if (cell.plants.size() < 10) {
                        System.out.print("\uD83C\uDF31");
                    } else {
                        System.out.print("\uD83C\uDF3F");
                    }
                } else {
                    Set<Class> classes = cell.animals.stream()
                            .map(Animal::getClass)
                            .collect(Collectors.toSet());
                    if (classes.contains(Deer.class)) {
                        System.out.print("\uD83E\uDD8C");
                    } else if (classes.contains(Wolf.class)) {
                        System.out.print("\uD83D\uDC3A");
                    } else if (classes.contains(Fox.class)) {
                        System.out.print("\uD83E\uDD8A");
                    } else if (classes.contains(Rabbit.class)) {
                        System.out.print("\uD83D\uDC07");
                    } else if (classes.contains(Rat.class)) {
                        System.out.print("\uD83D\uDC00");
                    }
                }
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
            System.out.printf("Step: %d%n" +
                            "Deer(born/died): %d/%d%n" +
                            "Wolves(born/died): %d/%d%n" +
                            "Foxes(born/died): %d/%d%n" +
                            "Rabbits(born/died): %d/%d%n" +
                            "Rats(born/died): %d/%d%n" +
                            "Plants(grown/eaten): %d/%d\n", step,
                    Params.deerBorn, Params.deerDied,
                    Params.wolvesBorn, Params.wolvesDied,
                    Params.foxesBorn, Params.foxesDied,
                    Params.rabbitsBorn, Params.rabbitsDied,
                    Params.ratsBorn, Params.ratsDied,
                    Plant.plantsGrown, Plant.plantsEaten);
            printIsland();
            nextLifeCycle();
            step++;
        }
    }
}
