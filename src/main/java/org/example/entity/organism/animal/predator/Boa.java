package org.example.entity.organism.animal.predator;

import org.example.entity.characteristic.Characteristics;
import org.example.entity.coordinate.Coordinate;

/**
 * The {@code Boa} class represents a boa in the simulation.
 * It extends the {@code Predator} class, inheriting its characteristics and behavior.
 */
public class Boa extends Predator {

    /**
     * Constructs a new boa with the specified characteristics and current coordinate.
     *
     * @param characteristics    the characteristics of the goat, such as weight and movement speed
     * @param currentCoordinate  the current coordinate of the goat in the simulation
     */
    public Boa(Characteristics characteristics, Coordinate currentCoordinate) {
        super(characteristics, currentCoordinate);
    }
}
