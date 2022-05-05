package domain.animals.herbivores;

import domain.animals.Animal;

import java.util.HashSet;
import java.util.Set;

import static domain.Params.*;

public class Rat extends Herbivore {

    public Rat() {
        ratsBorn++;
        this.weight = ratWeight;
        this.maxInCell = ratsInCell;
        init();
    }

    @Override
    protected Set<Animal> getChild() {
        return new HashSet<>() {{
//            for (int i = 0; i < 9; i++) {
                add(new Rat());
//            }
        }};
    }

    @Override
    public void die() {
        super.die();
        ratsDied++;
    }
}
