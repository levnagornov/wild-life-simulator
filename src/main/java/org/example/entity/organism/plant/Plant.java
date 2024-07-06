package org.example.entity.organism.plant;

import org.example.entity.coordinate.Coordinate;
import org.example.entity.organism.Organism;
import org.example.entity.characteristic.Characteristics;

public abstract class Plant extends Organism {
    public Plant(Characteristics characteristics, Coordinate coordinate) {
        super(characteristics, coordinate);
    }
}
