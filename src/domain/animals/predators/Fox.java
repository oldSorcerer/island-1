package domain.animals.predators;

import domain.animals.Animal;

import java.util.HashSet;
import java.util.Set;

public class Fox extends Predator {
    public static int foxesBorn;
    public static int foxesDied;

    public Fox() {
        foxesBorn++;
        this.maxHungerLevel = 15;
    }

    @Override
    protected Set<Animal> getChild() {
        return new HashSet<>() {{
            add(new Fox());
        }};
    }

    @Override
    public void die() {
        super.die();
        foxesDied++;
    }
}
