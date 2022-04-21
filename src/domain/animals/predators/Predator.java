package domain.animals.predators;

import domain.animals.Animal;
import domain.animals.herbivores.Herbivore;
import domain.terrain.Cell;

import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Predator extends Animal {

    @Override
    public void feed(Cell cell) {
        if (hungerLevel <= maxHungerLevel / 4) {
            increaseHunger(cell);
            return;
        }

        Optional<Animal> prey = findPray(cell);
        if (prey.isPresent()) {
            Animal snack = prey.get();
            cell.animals.remove(snack);
            snack.die();
            decreaseHunger(snack.getWeight());
            feed(cell);
        } else {
            increaseHunger(cell);
        }
    }

    protected Optional<Animal> findPray(Cell cell) {
        for (Animal candidate : cell.animals) {
            if (candidate instanceof Herbivore) {
                int i = ThreadLocalRandom.current().nextInt(100);
                if (i < (100 - candidate.getWeight())) {
                    return Optional.of(candidate);
                }
            }
        }

        return Optional.empty();
    }
}
