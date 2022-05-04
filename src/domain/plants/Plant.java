package domain.plants;

import static domain.Params.plantWeight;

public class Plant {
    public static int plantsGrown;
    public static int plantsEaten;

    public int weight = plantWeight;

    public Plant() {
        plantsGrown++;
    }
}
