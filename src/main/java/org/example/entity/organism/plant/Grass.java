package org.example.entity.organism.plant;

import org.example.entity.coordinate.Coordinate;
import org.example.entity.characteristic.Characteristics;

/**
 * The {@code Grass} class represents a type of plant in the simulation.
 * It extends the {@code Plant} class and provides specific characteristics for grass.
 */
public class Grass extends Plant {

    /**
     * Constructs a new grass plant with the specified characteristics and coordinate.
     *
     * @param characteristics the characteristics of the grass, such as growth rate and size
     * @param coordinate      the coordinate of the grass in the simulation
     */
    public Grass(Characteristics characteristics, Coordinate coordinate) {
        super(characteristics, coordinate);
    }
}
