package domain;

import domain.animals.herbivores.Deer;
import domain.animals.herbivores.Rabbit;
import domain.animals.herbivores.Rat;

import java.util.Map;

public class Params {
    public static int plantWeight = 1;

    public static int deerBorn;
    public static int deerDied;
    public static int deerWeight = 170;
    public static int deerMaxSaturation = 26;

    public static int ratsBorn;
    public static int ratsDied;
    public static int ratWeight = 1;
    public static int ratMaxSaturation = 1;

    public static int rabbitsBorn;
    public static int rabbitsDied;
    public static int rabbitWeight = 3;
    public static int rabbitMaxSaturation = 1;

    public static int foxesBorn;
    public static int foxesDied;
    public static int foxWeight = 4;
    public static int foxMaxSaturation = 1;
    public static Map<Class,Integer> foxDiet = Map.of(Rat.class, 90, Rabbit.class, 70);

    public static int wolvesBorn;
    public static int wolvesDied;
    public static int wolfWeight = 50;
    public static int wolfMaxSaturation = 8;
    public static Map<Class,Integer> wolfDiet = Map.of(Deer.class, 40, Rat.class, 80, Rabbit.class, 60);
}
