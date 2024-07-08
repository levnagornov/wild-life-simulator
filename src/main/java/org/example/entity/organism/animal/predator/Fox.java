package org.example.entity.organism.animal.predator;

import org.example.entity.coordinate.Coordinate;
import org.example.entity.characteristic.Characteristics;

/**
 * The {@code Fox} class represents a fox in the simulation.
 * It extends the {@code Predator} class, inheriting its characteristics and behavior.
 */
public class Fox extends Predator {

    /**
     * Constructs a new fox with the specified characteristics and current coordinate.
     *
     * @param characteristics    the characteristics of the goat, such as weight and movement speed
     * @param currentCoordinate  the current coordinate of the goat in the simulation
     */
    public Fox(Characteristics characteristics, Coordinate currentCoordinate) {
        super(characteristics, currentCoordinate);
    }
}
