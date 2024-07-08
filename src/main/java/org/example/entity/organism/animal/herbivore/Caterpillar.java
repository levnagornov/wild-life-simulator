package org.example.entity.organism.animal.herbivore;

import org.example.entity.coordinate.Coordinate;
import org.example.entity.characteristic.Characteristics;

/**
 * The {@code Caterpillar} class represents a сaterpillar in the simulation.
 * It extends the {@code Herbivore} class, inheriting its characteristics and behavior.
 */
public class Caterpillar extends Herbivore {

    /**
     * Constructs a new сaterpillar with the specified characteristics and current coordinate.
     *
     * @param characteristics    the characteristics of the goat, such as weight and movement speed
     * @param currentCoordinate  the current coordinate of the goat in the simulation
     */
    public Caterpillar(Characteristics characteristics, Coordinate currentCoordinate) {
        super(characteristics, currentCoordinate);
    }
}
