package domain.animals;

import domain.terrain.Cell;
import domain.terrain.Island;

import java.util.concurrent.ThreadLocalRandom;

public class Animal {

    private boolean dead;
    private int hungerLevel = 9;
    private boolean reproduced;

    public Animal() {
        Island.animalsBorn++;
    }

    public boolean isDead() {
        return dead;
    }

    public void setReproduced(boolean reproduced) {
        this.reproduced = reproduced;
    }

    public boolean isReadyToReproduce() {
        return !reproduced && hungerLevel < 5;
    }

    public void increaseHunger() {
        if (dead || hungerLevel >= 10) {
            return;
        }
        hungerLevel++;
        if (hungerLevel == 10) {
            dead = true;
        }
    }

    public void decreaseHunger() {
        if (dead || hungerLevel <= 0) {
            return;
        }
        hungerLevel--;
    }

    public Cell getDestinationCell(int currentX, int currentY) {
        if (dead) {
            return null;
        }

        int random = ThreadLocalRandom.current().nextInt(100);
        Cell destinationCell;
        if (random < 20) {
            if (currentX <= 0) {
                return null;
            }
            destinationCell = Island.island.cells[currentY][currentX - 1];
        } else if (random < 40) {
            if (currentX >= Island.island.width - 1) {
                return null;
            }
            destinationCell = Island.island.cells[currentY][currentX + 1];
        } else if (random < 60) {
            if (currentY <= 0) {
                return null;
            }
            destinationCell = Island.island.cells[currentY - 1][currentX];
        } else if (random < 80) {
            if (currentY >= Island.island.height - 1) {
                return null;
            }
            destinationCell = Island.island.cells[currentY + 1][currentX];
        } else {
            return null;
        }

        return destinationCell;
    }
}
