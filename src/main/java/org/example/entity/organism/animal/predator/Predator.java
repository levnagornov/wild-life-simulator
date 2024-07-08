package org.example.entity.organism.animal.predator;

import org.example.entity.coordinate.Coordinate;
import org.example.entity.organism.animal.Animal;
import org.example.entity.characteristic.Characteristics;

/**
 * The {@code Predator} class represents a predatory animal in the simulation.
 * It extends the {@code Animal} class and provides specific functionality for herbivores.
 */
public abstract class Predator extends Animal {

    /**
     * Constructs a new predator with the specified characteristics and current coordinate.
     *
     * @param characteristics    the characteristics of the herbivore, such as weight and movement speed
     * @param currentCoordinate  the current coordinate of the herbivore in the simulation
     */
    public Predator(Characteristics characteristics, Coordinate currentCoordinate) {
        super(characteristics, currentCoordinate);
    }
}
