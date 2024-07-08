package org.example.entity.organism.animal.predator;

import org.example.entity.characteristic.Characteristics;
import org.example.entity.coordinate.Coordinate;

/**
 * The {@code Bear} class represents a bear in the simulation.
 * It extends the {@code Predator} class, inheriting its characteristics and behavior.
 */
public class Bear extends Predator {

    /**
     * Constructs a new bear with the specified characteristics and current coordinate.
     *
     * @param characteristics    the characteristics of the goat, such as weight and movement speed
     * @param currentCoordinate  the current coordinate of the goat in the simulation
     */
    public Bear(Characteristics characteristics, Coordinate currentCoordinate) {
        super(characteristics, currentCoordinate);
    }
}
