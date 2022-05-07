package domain.animals.herbivores;

import domain.animals.Animal;
import domain.terrain.Cell;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import static domain.Params.*;

public class Boar extends Herbivore {

    public Boar() {
        boarsBorn++;
        this.weight = boarWeight;
        this.maxInCell = boarsInCell;
        this.maxDistance = boarMaxDistance;
        init();
        this.diet = boarDiet;
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
    public void hunt(Cell cell) {
        if (saturation >= maxSaturation) {
            decreaseSaturation(cell);
            return;
        }

        do {
            Optional<Animal> prey = findPray(cell);
            if (prey.isPresent()) {
                Animal snack = prey.get();
                cell.animals.remove(snack);
                snack.die();
                increaseSaturation(snack.getWeight());
                feed(cell);
            } else {
                break;
            }
        } while (saturation < maxSaturation / 2);

        decreaseSaturation(cell);
    }

    protected Optional<Animal> findPray(Cell cell) {
        for (Animal candidate : cell.animals) {
            if (diet.containsKey(candidate.getClass())) {
                int random = ThreadLocalRandom.current().nextInt(100);
                if (random < diet.get(candidate.getClass())) {
                    return Optional.of(candidate);
                }
            }
        }

        return Optional.empty();
    }
}
