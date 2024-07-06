package org.example.service;

import org.example.entity.area.Area;
import org.example.entity.characteristic.CharacteristicsFactory;
import org.example.entity.coordinate.Coordinate;
import org.example.entity.location.Location;
import org.example.entity.organism.Organism;
import org.example.entity.organism.animal.Animal;

public class ValidationService {
    private final CharacteristicsFactory characteristicsFactory;

    public ValidationService(CharacteristicsFactory characteristicsFactory) {
        this.characteristicsFactory = characteristicsFactory;
    }

    public boolean isValidCoordinate(Coordinate coordinate, Area area) {
        return isCoordinateInsideArea(coordinate, area);
    }

    public boolean isValidLocationForAnimal(Location location, Animal animal) {
        return isPassableLocation(location) && !isLocationOverpopulated(location, animal.getClass());
    }

    public boolean isCoordinateInsideArea(Coordinate coordinate, Area area) {
        return coordinate.y() >= 0
                && coordinate.x() >= 0
                && coordinate.y() < area.getHeight()
                && coordinate.x() < area.getWidth();
    }

    public boolean isPassableLocation(Location location) {
        return location.getLocationType().isPassable();
    }

    public boolean isLocationOverpopulated(Location location, Class<? extends Organism> organismType) {
        var organismCount = location.getSpeciesCounterMap().getOrDefault(organismType, 0);
        var organismLimit = characteristicsFactory.getCharacteristics(organismType).maxSpeciesPerCoordinate();

        return organismCount >= organismLimit;
    }
}
