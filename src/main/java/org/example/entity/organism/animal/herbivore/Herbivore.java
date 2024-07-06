package org.example.entity.organism.animal.herbivore;

import org.example.entity.coordinate.Coordinate;
import org.example.entity.organism.animal.Animal;
import org.example.entity.characteristic.Characteristics;

public abstract class Herbivore extends Animal {
    public Herbivore(Characteristics characteristics, Coordinate currentCoordinate) {
        super(characteristics, currentCoordinate);
    }
}
