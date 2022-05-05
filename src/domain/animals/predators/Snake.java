package domain.animals.predators;

import domain.animals.Animal;

import java.util.HashSet;
import java.util.Set;

import static domain.Params.*;

public class Snake extends Predator {

    public Snake() {
        snakesBorn++;
        this.weight = snakeWeight;
        this.maxInCell = snakesInCell;
        init();
        this.diet = snakeDiet;
    }

    @Override
    protected Set<Animal> getOffspring() {
        return new HashSet<>() {{
            add(new Snake());
        }};
    }

    @Override
    public void die() {
        super.die();
        snakesDied++;
    }
}