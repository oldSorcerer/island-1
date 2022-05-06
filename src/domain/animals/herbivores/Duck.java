package domain.animals.herbivores;

import domain.animals.Animal;
import domain.terrain.Cell;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import static domain.Params.*;

public class Duck extends Herbivore {

    public Duck() {
        ducksBorn++;
        this.weight = duckWeight;
        this.maxInCell = ducksInCell;
        init();
        this.diet = duckDiet;
    }

    @Override
    protected Set<Animal> getOffspring() {
        return new HashSet<>() {{
            add(new Duck());
        }};
    }

    @Override
    public void die() {
        super.die();
        ducksDied++;
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
