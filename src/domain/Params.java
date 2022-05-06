package domain;

import domain.animals.herbivores.*;
import domain.animals.predators.Boa;
import domain.animals.predators.Fox;

import java.util.Map;

public class Params {
    public static double plantWeight = 1;

    public static int wolvesBorn;
    public static int wolvesDied;
    public static double wolfWeight = 50;
    public static int wolvesInCell = 30;
    public static Map<Class, Integer> wolfDiet = Map.of(
            Horse.class, 10,
            Deer.class, 15,
            Rabbit.class, 60,
            Mouse.class, 80,
            Goat.class, 60,
            Sheep.class, 70,
            Kangaroo.class, 20,
            Buffalo.class, 10,
            Duck.class, 40);

    public static int boasBorn;
    public static int boasDied;
    public static double boaWeight = 15;
    public static int boasInCell = 20;
    public static Map<Class, Integer> boaDiet = Map.of(
            Rabbit.class, 20,
            Mouse.class, 40,
            Duck.class, 10,
            Fox.class, 15);

    public static int foxesBorn;
    public static int foxesDied;
    public static double foxWeight = 8;
    public static int foxesInCell = 30;
    public static Map<Class, Integer> foxDiet = Map.of(
            Rabbit.class, 70,
            Mouse.class, 90,
            Duck.class, 60,
            Caterpillar.class, 40);

    public static int bearsBorn;
    public static int bearsDied;
    public static double bearWeight = 500;
    public static int bearsInCell = 5;
    public static Map<Class, Integer> bearDiet = Map.of(
            Boa.class, 80,
            Horse.class, 40,
            Deer.class, 80,
            Rabbit.class, 80,
            Mouse.class, 90,
            Goat.class, 70,
            Sheep.class, 70,
            Kangaroo.class, 60,
            Buffalo.class, 20,
            Duck.class, 10);

    public static int eaglesBorn;
    public static int eaglesDied;
    public static double eagleWeight = 6;
    public static int eaglesInCell = 160;
    public static Map<Class, Integer> eagleDiet = Map.of(
            Fox.class, 10,
            Rabbit.class, 90,
            Mouse.class, 90,
            Duck.class, 80);

    public static int horsesBorn;
    public static int horsesDied;
    public static double horseWeight = 400;
    public static int horsesInCell = 20;

    public static int deerBorn;
    public static int deerDied;
    public static double deerWeight = 300;
    public static int deerInCell = 20;

    public static int rabbitsBorn;
    public static int rabbitsDied;
    public static double rabbitWeight = 2;
    public static int rabbitsInCell = 800;

    public static int miceBorn;
    public static int miceDied;
    public static double mouseWeight = 0.05;
    public static int miceInCell = 500;
    public static Map<Class, Integer> mouseDiet = Map.of(Caterpillar.class, 90);

    public static int goatsBorn;
    public static int goatsDied;
    public static double goatWeight = 60;
    public static int goatsInCell = 140;

    public static int sheepsBorn;
    public static int sheepsDied;
    public static double sheepWeight = 70;
    public static int sheepsInCell = 140;

    public static int kangaroosBorn;
    public static int kangaroosDied;
    public static double kangarooWeight = 80;
    public static int kangaroosInCell = 150;

    public static int buffaloesBorn;
    public static int buffaloesDied;
    public static double buffaloWeight = 700;
    public static int buffaloesInCell = 10;

    public static int ducksBorn;
    public static int ducksDied;
    public static double duckWeight = 0.8;
    public static int ducksInCell = 200;
    public static Map<Class, Integer> duckDiet = Map.of(Caterpillar.class, 90);

    public static int caterpillarsBorn;
    public static int caterpillarsDied;
    public static double caterpillarWeight = 0.01;
    public static int caterpillarsInCell = 1000;
}
