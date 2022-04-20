package domain.animals;

import domain.terrain.Cell;
import domain.terrain.Direction;
import domain.terrain.Island;

import java.util.concurrent.ThreadLocalRandom;

public class Animal {

    private boolean dead;
    private int hungerLevel = 9;
    private boolean reproduced;

    public Animal(Island island) {
        island.animalsBorn++;
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

    public Direction getDirection(int currentX, int currentY) {
        if (dead) {
            return Direction.NONE;
        }

        int random = ThreadLocalRandom.current().nextInt(100);
        if (random < 20) {
            return Direction.LEFT;
        } else if (random < 40) {
            return Direction.RIGHT;
        } else if (random < 60) {
            return Direction.UP;
        } else if (random < 80) {
            return Direction.DOWN;
        } else {
            return Direction.NONE;
        }
    }
}
