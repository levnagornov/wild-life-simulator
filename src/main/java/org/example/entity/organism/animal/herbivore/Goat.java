package org.example.entity.organism.animal.herbivore;

import org.example.entity.characteristic.Characteristics;
import org.example.entity.coordinate.Coordinate;

/**
 * The {@code Goat} class represents a goat in the simulation.
 * It extends the {@code Herbivore} class, inheriting its characteristics and behavior.
 */
public class Goat extends Herbivore {

    /**
     * Constructs a new goat with the specified characteristics and current coordinate.
     *
     * @param characteristics    the characteristics of the goat, such as weight and movement speed
     * @param currentCoordinate  the current coordinate of the goat in the simulation
     */
    public Goat(Characteristics characteristics, Coordinate currentCoordinate) {
        super(characteristics, currentCoordinate);
    }
}
