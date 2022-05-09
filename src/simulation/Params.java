package simulation;

import simulation.wildlife.herbivores.*;
import simulation.wildlife.predators.Boa;
import simulation.wildlife.predators.Fox;

import java.util.Map;

public class Params {
    public static final int WIDTH = 100;
    public static final int HEIGHT = 20;
    public static final int ANIMAL_STEP_PERIOD = 500;
    public static final int PLANT_STEP_PERIOD = ANIMAL_STEP_PERIOD * 5;
    public static final int MAX_CELL_PLANTS = 200;
    public static final double PLANT_WEIGHT = 1;

    public static int wolvesBorn;
    public static int wolvesDied;
    public static final double wolfWeight = 50;
    public static final int wolvesInCell = 30;
    public static final int wolfMaxDistance = 2;
    public static final Map<Class, Integer> wolfDiet = Map.of(
            Horse.class, 10,
            Deer.class, 15,
            Rabbit.class, 60,
            Mouse.class, 80,
            Goat.class, 60,
            Sheep.class, 70,
            Boar.class, 15,
            Buffalo.class, 10,
            Duck.class, 40);

    public static int boasBorn;
    public static int boasDied;
    public static final double boaWeight = 15;
    public static final int boasInCell = 30;
    public static final int boaMaxDistance = 1;
    public static final Map<Class, Integer> boaDiet = Map.of(
            Rabbit.class, 20,
            Mouse.class, 40,
            Duck.class, 10,
            Fox.class, 15);

    public static int foxesBorn;
    public static int foxesDied;
    public static final double foxWeight = 8;
    public static final int foxesInCell = 30;
    public static final int foxMaxDistance = 2;
    public static final Map<Class, Integer> foxDiet = Map.of(
            Rabbit.class, 70,
            Mouse.class, 90,
            Duck.class, 60,
            Caterpillar.class, 40);

    public static int bearsBorn;
    public static int bearsDied;
    public static final double bearWeight = 500;
    public static final int bearsInCell = 5;
    public static final int bearMaxDistance = 2;
    public static final Map<Class, Integer> bearDiet = Map.of(
            Boa.class, 80,
            Horse.class, 40,
            Deer.class, 80,
            Rabbit.class, 80,
            Mouse.class, 90,
            Goat.class, 70,
            Sheep.class, 70,
            Boar.class, 50,
            Buffalo.class, 20,
            Duck.class, 10);

    public static int eaglesBorn;
    public static int eaglesDied;
    public static final double eagleWeight = 6;
    public static final int eaglesInCell = 20;
    public static final int eagleMaxDistance = 3;
    public static final Map<Class, Integer> eagleDiet = Map.of(
            Fox.class, 10,
            Rabbit.class, 90,
            Mouse.class, 90,
            Duck.class, 80);

    public static int horsesBorn;
    public static int horsesDied;
    public static final double horseWeight = 400;
    public static final int horsesInCell = 20;
    public static final int horseMaxDistance = 4;

    public static int deerBorn;
    public static int deerDied;
    public static final double deerWeight = 300;
    public static final int deerInCell = 20;
    public static final int deerMaxDistance = 4;

    public static int rabbitsBorn;
    public static int rabbitsDied;
    public static final double rabbitWeight = 2;
    public static final int rabbitsInCell = 150;
    public static final int rabbitMaxDistance = 2;

    public static int miceBorn;
    public static int miceDied;
    public static final double mouseWeight = 0.05;
    public static final int miceInCell = 500;
    public static final int mouseMaxDistance = 1;
    public static final Map<Class, Integer> mouseDiet = Map.of(Caterpillar.class, 90);

    public static int goatsBorn;
    public static int goatsDied;
    public static final double goatWeight = 60;
    public static final int goatsInCell = 140;
    public static final int goatMaxDistance = 3;

    public static int sheepsBorn;
    public static int sheepsDied;
    public static final double sheepWeight = 70;
    public static final int sheepsInCell = 140;
    public static final int sheepMaxDistance = 3;

    public static int boarsBorn;
    public static int boarsDied;
    public static final double boarWeight = 400;
    public static final int boarsInCell = 50;
    public static final int boarMaxDistance = 2;
    public static final Map<Class, Integer> boarDiet = Map.of(Mouse.class, 50, Caterpillar.class, 90);

    public static int buffaloesBorn;
    public static int buffaloesDied;
    public static final double buffaloWeight = 700;
    public static final int buffaloesInCell = 10;
    public static final int buffaloMaxDistance = 3;

    public static int ducksBorn;
    public static int ducksDied;
    public static final double duckWeight = 1;
    public static final int ducksInCell = 200;
    public static final int duckMaxDistance = 4;
    public static final Map<Class, Integer> duckDiet = Map.of(Caterpillar.class, 90);

    public static int caterpillarsBorn;
    public static int caterpillarsDied;
    public static final double caterpillarWeight = 0.01;
    public static final int caterpillarsInCell = 1000;
    public static final int caterpillarMaxDistance = 0;
}
