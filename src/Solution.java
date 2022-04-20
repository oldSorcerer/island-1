import domain.terrain.Island;

import java.util.concurrent.ExecutorService;

public class Solution {

    public static void main(String[] args) throws InterruptedException {
        Island island = new Island();

        ExecutorService plantGrowth = island.runPlantsGrowth();
        island.runAnimalLifeCycle();
        plantGrowth.shutdown();
    }
}
