package domain.animals.herbivores;

import domain.animals.Animal;

import java.util.HashSet;
import java.util.Set;

public class Rabbit extends Herbivore {

    public static int rabbitsBorn;
    public static int rabbitsDied;

    public Rabbit() {
        rabbitsBorn++;
        this.weight = 1;
    }

    @Override
    protected Set<Animal> getChild() {
        return new HashSet<>() {{
            add(new Rabbit());
            add(new Rabbit());
            add(new Rabbit());
        }};
    }

    @Override
    public void die() {
        super.die();
        rabbitsDied++;
    }
}
