package domain;

import domain.animals.herbivores.Deer;
import domain.animals.herbivores.Rabbit;
import domain.animals.herbivores.Hamster;

import java.util.Map;

public class Params {
    public static double plantWeight = 1;

    public static int wolvesBorn;
    public static int wolvesDied;
    public static double wolfWeight = 50;
    public static int wolvesInCell = 30;
    public static Map<Class,Integer> wolfDiet = Map.of(Deer.class, 40, Hamster.class, 80, Rabbit.class, 60);

    public static int snakesBorn;
    public static int snakesDied;
    public static double snakeWeight = 2;
    public static int snakesInCell = 123;
    public static Map<Class,Integer> snakeDiet = Map.of(Deer.class, 40, Hamster.class, 80, Rabbit.class, 60);

    public static int foxesBorn;
    public static int foxesDied;
    public static double foxWeight = 4;
    public static int foxesInCell = 50;
    public static Map<Class,Integer> foxDiet = Map.of(Hamster.class, 90, Rabbit.class, 70);

    public static int bearsBorn;
    public static int bearsDied;
    public static double bearWeight = 250;
    public static int bearsInCell = 7;
    public static Map<Class,Integer> bearDiet = Map.of(Hamster.class, 90, Rabbit.class, 70);

    public static int eaglesBorn;
    public static int eaglesDied;
    public static double eagleWeight = 6;
    public static int eaglesInCell = 166;
    public static Map<Class,Integer> eagleDiet = Map.of(Hamster.class, 90, Rabbit.class, 70);

    public static int horsesBorn;
    public static int horsesDied;
    public static double horseWeight = 300;
    public static int horsesInCell = 23;

    public static int deerBorn;
    public static int deerDied;
    public static double deerWeight = 170;
    public static int deerInCell = 41;

    public static int rabbitsBorn;
    public static int rabbitsDied;
    public static double rabbitWeight = 3;
    public static int rabbitsInCell = 750;

    public static int hamstersBorn;
    public static int hamstersDied;
    public static double hamsterWeight = 0.03;
    public static int hamstersInCell = 10000;

    public static int goatsBorn;
    public static int goatsDied;
    public static double goatWeight = 65;
    public static int goatsInCell = 107;

    public static int sheepsBorn;
    public static int sheepsDied;
    public static double sheepWeight = 45;
    public static int sheepsInCell = 156;

    public static int kangaroosBorn;
    public static int kangaroosDied;
    public static double kangarooWeight = 47;
    public static int kangaroosInCell = 149;

    public static int cowsBorn;
    public static int cowsDied;
    public static double cowWeight = 350;
    public static int cowsInCell = 20;

    public static int ducksBorn;
    public static int ducksDied;
    public static double duckWeight = 1;
    public static int ducksInCell = 500;

    public static int caterpillarsBorn;
    public static int caterpillarsDied;
    public static double caterpillarWeight = 0.01;
    public static int caterpillarsInCell = 10000;
}
