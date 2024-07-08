package org.example.service;

import org.example.entity.area.Area;
import org.example.entity.characteristic.CharacteristicsFactory;
import org.example.entity.coordinate.Coordinate;
import org.example.entity.location.Location;
import org.example.entity.organism.Organism;
import org.example.entity.organism.animal.Animal;

/**
 * Service class for validation checks related to coordinates, locations, and organism populations.
 */
public class ValidationService {
    private final CharacteristicsFactory characteristicsFactory;

    /**
     * Constructs a ValidationService with the specified CharacteristicsFactory.
     *
     * @param characteristicsFactory Factory for retrieving characteristics of organisms.
     */
    public ValidationService(CharacteristicsFactory characteristicsFactory) {
        this.characteristicsFactory = characteristicsFactory;
    }

    /**
     * Checks if the given coordinate is inside the specified area.
     *
     * @param coordinate The coordinate to check.
     * @param area       The area to check against.
     * @return true if the coordinate is inside the area, otherwise false.
     */
    public boolean isValidCoordinate(Coordinate coordinate, Area area) {
        return isCoordinateInsideArea(coordinate, area);
    }

    /**
     * Checks if the given location is valid for placing the specified animal.
     * A valid location is passable and not overpopulated by the same type of organism.
     *
     * @param location The location to check.
     * @param animal   The animal to be placed.
     * @return true if the location is valid, otherwise false.
     */
    public boolean isValidLocationForAnimal(Location location, Animal animal) {
        return isPassableLocation(location) && !isLocationOverpopulated(location, animal.getClass());
    }

    /**
     * Checks if the given coordinate is inside the specified area.
     *
     * @param coordinate The coordinate to check.
     * @param area       The area to check against.
     * @return true if the coordinate is inside the area, otherwise false.
     */
    public boolean isCoordinateInsideArea(Coordinate coordinate, Area area) {
        return coordinate.y() >= 0
                && coordinate.x() >= 0
                && coordinate.y() < area.getHeight()
                && coordinate.x() < area.getWidth();
    }

    /**
     * Checks if the given location is passable.
     * A passable location is determined by its location type.
     *
     * @param location The location to check.
     * @return true if the location is passable, otherwise false.
     */
    public boolean isPassableLocation(Location location) {
        return location.getLocationType().isPassable();
    }

    /**
     * Checks if the given location is overpopulated by the specified type of organism.
     *
     * @param location     The location to check.
     * @param organismType The type of organism to check for overpopulation.
     * @return true if the location is overpopulated by the organism type, otherwise false.
     */
    public boolean isLocationOverpopulated(Location location, Class<? extends Organism> organismType) {
        var organismCount = location.getSpeciesCounterMap().getOrDefault(organismType, 0);
        var organismLimit = characteristicsFactory.getCharacteristics(organismType).maxSpeciesPerCoordinate();

        return organismCount >= organismLimit;
    }
}
