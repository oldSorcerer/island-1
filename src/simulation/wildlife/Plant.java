package simulation.wildlife;

import static simulation.Params.PLANT_WEIGHT;

public class Plant {
    public static int plantsGrown;
    public static int plantsEaten;

    public double weight = PLANT_WEIGHT;

    public Plant() {
        plantsGrown++;
    }
}
