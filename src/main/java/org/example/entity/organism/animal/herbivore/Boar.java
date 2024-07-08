package org.example.entity.organism.animal.herbivore;

import org.example.entity.characteristic.Characteristics;
import org.example.entity.coordinate.Coordinate;

/**
 * The {@code Boar} class represents a boar in the simulation.
 * It extends the {@code Herbivore} class, inheriting its characteristics and behavior.
 */
public class Boar extends Herbivore {

    /**
     * Constructs a new boar with the specified characteristics and current coordinate.
     *
     * @param characteristics    the characteristics of the goat, such as weight and movement speed
     * @param currentCoordinate  the current coordinate of the goat in the simulation
     */
    public Boar(Characteristics characteristics, Coordinate currentCoordinate) {
        super(characteristics, currentCoordinate);
    }
}
