package org.example.entity.organism.animal.herbivore;

import org.example.entity.coordinate.Coordinate;
import org.example.entity.characteristic.Characteristics;

/**
 * The {@code Duck} class represents a duck in the simulation.
 * It extends the {@code Herbivore} class, inheriting its characteristics and behavior.
 */
public class Duck extends Herbivore {

    /**
     * Constructs a new duck with the specified characteristics and current coordinate.
     *
     * @param characteristics    the characteristics of the goat, such as weight and movement speed
     * @param currentCoordinate  the current coordinate of the goat in the simulation
     */
    public Duck(Characteristics characteristics, Coordinate currentCoordinate) {
        super(characteristics, currentCoordinate);
    }
}
