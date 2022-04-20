package domain.animals;

public class Deer extends Herbivore {

    public static int deerBorn;
    public static int deerDied;

    public Deer() {
        deerBorn++;
    }

    @Override
    Deer getChild() {
        return new Deer();
    }

    @Override
    public void die() {
        super.die();
        deerDied++;
    }
}
