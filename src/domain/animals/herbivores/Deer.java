package domain.animals.herbivores;

import domain.animals.Animal;

import java.util.HashSet;
import java.util.Set;

public class Deer extends Herbivore {

    public static int deerBorn;
    public static int deerDied;

    public Deer() {
        deerBorn++;
        this.weight = 50;
        this.maxHungerLevel = 40;
        hungerLevel = maxHungerLevel / 2;
    }

    @Override
    protected Set<Animal> getChild() {
        return new HashSet<>() {{
            add(new Deer());
        }};
    }

    @Override
    public void die() {
        super.die();
        deerDied++;
    }
}
