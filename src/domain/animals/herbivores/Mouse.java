package domain.animals.herbivores;

import domain.animals.Animal;

import java.util.HashSet;
import java.util.Set;

public class Mouse extends Herbivore {

    public static int miceBorn;
    public static int miceDied;

    public Mouse() {
        miceBorn++;
        this.weight = 1;
    }

    @Override
    protected Set<Animal> getChild() {
        return new HashSet<>() {{
            for (int i = 0; i < 9; i++) {
                add(new Mouse());
            }
        }};
    }

    @Override
    public void die() {
        super.die();
        miceDied++;
    }
}
