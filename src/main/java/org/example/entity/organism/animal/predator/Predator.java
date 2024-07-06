package org.example.entity.organism.animal.predator;

import org.example.entity.coordinate.Coordinate;
import org.example.entity.organism.animal.Animal;
import org.example.entity.characteristic.Characteristics;

public abstract class Predator extends Animal {
    public Predator(Characteristics characteristics, Coordinate currentCoordinate) {
        super(characteristics, currentCoordinate);
    }
}
