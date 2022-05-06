package domain.terrain;

import domain.Params;
import domain.animals.Animal;
import domain.animals.herbivores.*;
import domain.animals.predators.*;
import domain.plants.Plant;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.*;
import java.util.stream.Collectors;

import static domain.Params.*;

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
        int totalCellAmount = wolvesInCell + boasInCell + foxesInCell + bearsInCell + eaglesInCell + horsesInCell
                + deerInCell + rabbitsInCell + miceInCell + goatsInCell + sheepsInCell + kangaroosInCell
                + buffaloesInCell + ducksInCell + caterpillarsInCell;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (getRandom(wolvesInCell, totalCellAmount)) {
                    cells[y][x].animals.add(new Wolf());
                    cells[y][x].animals.add(new Wolf());
                }
                if (getRandom(boasInCell, totalCellAmount)) {
                    cells[y][x].animals.add(new Boa());
                    cells[y][x].animals.add(new Boa());
                }
                if (getRandom(foxesInCell, totalCellAmount)) {
                    cells[y][x].animals.add(new Fox());
                    cells[y][x].animals.add(new Fox());
                }
                if (getRandom(bearsInCell, totalCellAmount)) {
                    cells[y][x].animals.add(new Bear());
                    cells[y][x].animals.add(new Bear());
                }
                if (getRandom(eaglesInCell, totalCellAmount)) {
                    cells[y][x].animals.add(new Eagle());
                    cells[y][x].animals.add(new Eagle());
                }
                if (getRandom(horsesInCell, totalCellAmount)) {
                    cells[y][x].animals.add(new Horse());
                    cells[y][x].animals.add(new Horse());
                }
                if (getRandom(deerInCell, totalCellAmount)) {
                    cells[y][x].animals.add(new Deer());
                    cells[y][x].animals.add(new Deer());
                }
                if (getRandom(rabbitsInCell, totalCellAmount)) {
                    cells[y][x].animals.add(new Rabbit());
                    cells[y][x].animals.add(new Rabbit());
                }
                if (getRandom(miceInCell, totalCellAmount)) {
                    cells[y][x].animals.add(new Mouse());
                    cells[y][x].animals.add(new Mouse());
                }
                if (getRandom(goatsInCell, totalCellAmount)) {
                    cells[y][x].animals.add(new Goat());
                    cells[y][x].animals.add(new Goat());
                }
                if (getRandom(sheepsInCell, totalCellAmount)) {
                    cells[y][x].animals.add(new Sheep());
                    cells[y][x].animals.add(new Sheep());
                }
//                if (getRandom(kangaroosInCell, totalCellAmount)) {
//                    cells[y][x].animals.add(new Kangaroo());
//                    cells[y][x].animals.add(new Kangaroo());
//                }
                if (getRandom(buffaloesInCell, totalCellAmount)) {
                    cells[y][x].animals.add(new Buffalo());
                    cells[y][x].animals.add(new Buffalo());
                }
                if (getRandom(ducksInCell, totalCellAmount)) {
                    cells[y][x].animals.add(new Duck());
                    cells[y][x].animals.add(new Duck());
                }
                if (getRandom(caterpillarsInCell, totalCellAmount)) {
                    cells[y][x].animals.add(new Caterpillar());
                    cells[y][x].animals.add(new Caterpillar());
                }
            }
        }
    }

    private static boolean getRandom(int animalsInCell, int totalInCell) {
        return ThreadLocalRandom.current().nextInt(100) < 100 * animalsInCell / totalInCell + 1;
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
                        System.out.print("\uD83C\uDF31"); // 🌱
                    } else {
                        System.out.print("\uD83C\uDF3F"); // 🌿
                    }
                } else {
                    Set<Class> classes = cell.animals.stream()
                            .map(Animal::getClass)
                            .collect(Collectors.toSet());
                    if (classes.contains(Buffalo.class)) {
                        System.out.print("\uD83D\uDC03"); // 🐃
                    } else if (classes.contains(Bear.class)) {
                        System.out.print("\uD83D\uDC3B"); // 🐻
                    } else if (classes.contains(Horse.class)) {
                        System.out.print("\uD83D\uDC0E"); // 🐎
                    } else if (classes.contains(Deer.class)) {
                        System.out.print("\uD83E\uDD8C"); // 🦌
//                    } else if (classes.contains(Kangaroo.class)) {
//                        System.out.print("KK"); // 🦘
                    } else if (classes.contains(Sheep.class)) {
                        System.out.print("\uD83D\uDC11"); // 🐑
                    } else if (classes.contains(Goat.class)) {
                        System.out.print("\uD83D\uDC10"); // 🐐
                    } else if (classes.contains(Wolf.class)) {
                        System.out.print("\uD83D\uDC3A"); // 🐺
                    } else if (classes.contains(Boa.class)) {
                        System.out.print("\uD83D\uDC0D"); // 🐍
                    } else if (classes.contains(Fox.class)) {
                        System.out.print("\uD83E\uDD8A"); // 🦊
                    } else if (classes.contains(Eagle.class)) {
                        System.out.print("\uD83E\uDD85"); // 🦅
                    } else if (classes.contains(Rabbit.class)) {
                        System.out.print("\uD83D\uDC07"); // 🐇
                    } else if (classes.contains(Duck.class)) {
                        System.out.print("\uD83E\uDD86"); // 🦆
                    } else if (classes.contains(Mouse.class)) {
                        System.out.print("\uD83D\uDC01"); // 🐁
                    } else if (classes.contains(Caterpillar.class)) {
                        System.out.print("\uD83D\uDC1B"); // 🐛
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
            System.out.printf("Step: %d\t\t\tPlants: %d/%d\n" +
                            "Wolf: %d/%d\t\t\tBoa: %d/%d\t\t\tFox: %d/%d\n" +
                            "Bear: %d/%d\t\t\tEagle: %d/%d\t\t\tHorse: %d/%d\n" +
                            "Deer: %d/%d\t\t\tRabbit: %d/%d\t\t\tMouse: %d/%d\n" +
                            "Goat: %d/%d\t\t\tSheep: %d/%d\t\t\tKangaroo: %d/%d\n" +
                            "Buffalo: %d/%d\t\t\tDuck: %d/%d\t\t\tCaterpillar: %d/%d\n", step,
                    Plant.plantsGrown, Plant.plantsEaten,
                    wolvesBorn, wolvesDied, boasBorn, boasDied, foxesBorn, foxesDied,
                    bearsBorn, bearsDied, eaglesBorn, eaglesDied, horsesBorn, horsesDied,
                    deerBorn, deerDied, rabbitsBorn, rabbitsDied, miceBorn, miceDied,
                    goatsBorn, goatsDied, sheepsBorn, sheepsDied, kangaroosBorn, kangaroosDied,
                    buffaloesBorn, buffaloesDied, ducksBorn, ducksDied, caterpillarsBorn, caterpillarsDied);
            printIsland();
            nextLifeCycle();
            step++;
        }
    }
}
