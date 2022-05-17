package simulation.terrain;

import simulation.wildlife.Animal;
import simulation.wildlife.Plant;
import simulation.wildlife.herbivores.*;
import simulation.wildlife.predators.*;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.*;
import java.util.stream.Collectors;

import static simulation.Params.*;

public class Island {

    private final Cell[][] cells = new Cell[HEIGHT][WIDTH];

    public Island() {
        initCells();
        growPlants();
        populateAnimals();
    }

    private void initCells() {
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                cells[y][x] = new Cell(this, x, y);
            }
        }
    }

    private void growPlants() {
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                int random = ThreadLocalRandom.current().nextInt(MAX_CELL_PLANTS / 10, MAX_CELL_PLANTS / 2);
                for (int i = 0; i < random; i++) {
                    cells[y][x].plants.add(new Plant());
                }
            }
        }
    }

    private void populateAnimals() {
        int totalCellAmount = wolvesInCell + boasInCell + foxesInCell + bearsInCell + eaglesInCell + horsesInCell
                + deerInCell + rabbitsInCell + miceInCell + goatsInCell + sheepsInCell + boarsInCell
                + buffaloesInCell + ducksInCell + caterpillarsInCell;
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
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
                if (getRandom(boarsInCell, totalCellAmount)) {
                    cells[y][x].animals.add(new Boar());
                    cells[y][x].animals.add(new Boar());
                }
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

    public Cell getDestinationCell(Cell start, List<Direction> directions) {
        Cell destination;
        for (Direction direction : directions) {
            if (direction == Direction.LEFT && start.x > 0) {
                destination = cells[start.y][start.x - 1];
            } else if (direction == Direction.RIGHT && start.x < WIDTH - 1) {
                destination = cells[start.y][start.x + 1];
            } else if (direction == Direction.UP && start.y > 0) {
                destination = cells[start.y - 1][start.x];
            } else if (direction == Direction.DOWN && start.y < HEIGHT - 1) {
                destination = cells[start.y + 1][start.x];
            } else {
                continue;
            }

            start = destination;
        }

        return start;
    }

    private static boolean getRandom(int animalsInCell, int totalInCell) {
        return ThreadLocalRandom.current().nextInt(100) < 100 * animalsInCell / totalInCell + 1;
    }

    public void run() {
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(2);
        executorService.scheduleAtFixedRate(new PlantGrowth(), PLANT_STEP_PERIOD, PLANT_STEP_PERIOD, TimeUnit.MILLISECONDS);
        executorService.scheduleAtFixedRate(new AnimalsLifeCycle(), 0, ANIMAL_STEP_PERIOD, TimeUnit.MILLISECONDS);
    }

    private class PlantGrowth implements Runnable {
        @Override
        public void run() {
            try {
                for (int y = 0; y < HEIGHT; y++) {
                    for (int x = 0; x < WIDTH; x++) {
                        if (ThreadLocalRandom.current().nextBoolean()) {
                            continue;
                        }
                        int factor = MAX_CELL_PLANTS / 2 - Math.abs(cells[y][x].plants.size() - MAX_CELL_PLANTS / 2) + 2;
                        int numberOfNewPlants = factor > 0 ? ThreadLocalRandom.current().nextInt(factor) : 10;

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
            printIsland();
            nextLifeCycle();
            step++;
        }

        private void nextLifeCycle() {
            ExecutorService service = Executors.newCachedThreadPool();
            Set<Future<Map<Cell, Set<Animal>>>> resettlementGroups = new HashSet<>();
            for (int y = 0; y < HEIGHT; y++) {
                for (int x = 0; x < WIDTH; x++) {
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

        private void printIsland() {
            System.out.printf("Step: %d\t\t\tPlants: %d/%d\n" +
                            "Wolf: %d/%d\t\t\tBoa: %d/%d\t\t\tFox: %d/%d\n" +
                            "Bear: %d/%d\t\t\tEagle: %d/%d\t\t\tHorse: %d/%d\n" +
                            "Deer: %d/%d\t\t\tRabbit: %d/%d\t\t\tMouse: %d/%d\n" +
                            "Goat: %d/%d\t\t\tSheep: %d/%d\t\t\tBoar: %d/%d\n" +
                            "Buffalo: %d/%d\t\t\tDuck: %d/%d\t\t\tCaterpillar: %d/%d\n", step,
                    Plant.plantsGrown, Plant.plantsEaten,
                    wolvesBorn, wolvesDied, boasBorn, boasDied, foxesBorn, foxesDied,
                    bearsBorn, bearsDied, eaglesBorn, eaglesDied, horsesBorn, horsesDied,
                    deerBorn, deerDied, rabbitsBorn, rabbitsDied, miceBorn, miceDied,
                    goatsBorn, goatsDied, sheepsBorn, sheepsDied, boarsBorn, boarsDied,
                    buffaloesBorn, buffaloesDied, ducksBorn, ducksDied, caterpillarsBorn, caterpillarsDied);

            for (int y = 0; y < HEIGHT; y++) {
                for (int x = 0; x < WIDTH; x++) {
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
                        } else if (classes.contains(Boar.class)) {
                            System.out.print("\uD83D\uDC17"); // 🐗
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
    }
}
