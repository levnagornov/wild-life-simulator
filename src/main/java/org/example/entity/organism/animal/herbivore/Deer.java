package org.example.entity.organism.animal.herbivore;

import org.example.entity.characteristic.Characteristics;
import org.example.entity.coordinate.Coordinate;

/**
 * The {@code Deer} class represents a deer in the simulation.
 * It extends the {@code Herbivore} class, inheriting its characteristics and behavior.
 */
public class Deer extends Herbivore {

    /**
     * Constructs a new deer with the specified characteristics and current coordinate.
     *
     * @param characteristics    the characteristics of the goat, such as weight and movement speed
     * @param currentCoordinate  the current coordinate of the goat in the simulation
     */
    public Deer(Characteristics characteristics, Coordinate currentCoordinate) {
        super(characteristics, currentCoordinate);
    }
}
