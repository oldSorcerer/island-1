package domain.animals.predators;

import domain.animals.Animal;
import domain.animals.herbivores.Herbivore;
import domain.terrain.Cell;

import java.util.Optional;

public abstract class Predator extends Animal {

    @Override
    public void feed(Cell cell) {
        Optional<Animal> prey = cell.animals.stream().filter(animal -> animal instanceof Herbivore).findAny();
        if (prey.isPresent()) {
            eat(cell, prey.get());
        } else {
            increaseHunger(cell);
        }
    }

    void eat(Cell cell, Animal prey) {
        cell.animals.remove(prey);
        prey.die();
        decreaseHunger(prey.getWeight());
    }
}
