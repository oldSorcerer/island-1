package simulation.wildlife.herbivores;

import simulation.terrain.Cell;
import simulation.wildlife.Animal;

import java.util.HashSet;
import java.util.Set;

import static simulation.Params.*;

public class Boar extends Herbivore {

    public Boar() {
        super(boarWeight, boarsInCell, boarMaxDistance, boarDiet);
        boarsBorn++;
    }

    @Override
    protected Set<Animal> getOffspring() {
        return new HashSet<>() {{
            add(new Boar());
        }};
    }

    @Override
    public void die() {
        super.die();
        boarsDied++;
    }

    @Override
    public void feed(Cell cell) {
        super.feed(cell);
        hunt(cell);
    }
}
