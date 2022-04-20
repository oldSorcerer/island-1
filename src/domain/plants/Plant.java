package domain.plants;

import domain.terrain.Island;

public class Plant {
    public Plant(Island island) {
        island.plantsGrown++;
    }
}
