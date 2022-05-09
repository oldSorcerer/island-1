package simulation.wildlife.herbivores;

import simulation.wildlife.Animal;
import simulation.terrain.Cell;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import static simulation.Params.*;

public class Mouse extends Herbivore {

    public Mouse() {
        miceBorn++;
        this.weight = mouseWeight;
        this.maxInCell = miceInCell;
        this.maxDistance = mouseMaxDistance;
        init();
        this.diet = mouseDiet;
    }

    @Override
    protected Set<Animal> getOffspring() {
        return new HashSet<>() {{
            add(new Mouse());
        }};
    }

    @Override
    public void die() {
        super.die();
        miceDied++;
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
