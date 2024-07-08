package org.example.entity.organism.plant;

import org.example.entity.coordinate.Coordinate;
import org.example.entity.organism.Organism;
import org.example.entity.characteristic.Characteristics;

/**
 * The {@code Plant} class represents a plant in the simulation.
 * It is an abstract class that extends the {@code Organism} class,
 * providing common characteristics and behavior for all plants.
 */
public abstract class Plant extends Organism {

    /**
     * Constructs a new plant with the specified characteristics and coordinate.
     *
     * @param characteristics the characteristics of the plant, such as growth rate and size
     * @param coordinate      the coordinate of the plant in the simulation
     */
    public Plant(Characteristics characteristics, Coordinate coordinate) {
        super(characteristics, coordinate);
    }
}
