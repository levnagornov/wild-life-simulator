package org.example.entity.organism.animal.herbivore;

import org.example.entity.coordinate.Coordinate;
import org.example.entity.organism.animal.Animal;
import org.example.entity.characteristic.Characteristics;

/**
 * The {@code Herbivore} class represents a herbivorous animal in the simulation.
 * It extends the {@code Animal} class and provides specific functionality for herbivores.
 */
public abstract class Herbivore extends Animal {

    /**
     * Constructs a new herbivore with the specified characteristics and current coordinate.
     *
     * @param characteristics    the characteristics of the herbivore, such as weight and movement speed
     * @param currentCoordinate  the current coordinate of the herbivore in the simulation
     */
    public Herbivore(Characteristics characteristics, Coordinate currentCoordinate) {
        super(characteristics, currentCoordinate);
    }
}
