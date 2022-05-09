package simulation.wildlife.predators;

import simulation.wildlife.Animal;
import simulation.terrain.Cell;

import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Predator extends Animal {

    @Override
    protected void pinchGrass(Cell cell) {
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
