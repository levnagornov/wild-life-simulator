package org.example.entity.location;

import org.example.entity.coordinate.Coordinate;
import org.example.service.RandomizerService;

/**
 * The {@code LocationFactory} class is responsible for creating instances of {@code Location}.
 * It provides methods to create a location of a specified type or a random location type.
 */
public class LocationFactory {
    private final RandomizerService randomizerService;

    /**
     * Constructs a {@code LocationFactory} with the specified {@code RandomizerService}.
     *
     * @param randomizerService the randomizer service used to generate random location types
     */
    public LocationFactory(RandomizerService randomizerService) {
        this.randomizerService = randomizerService;
    }

    /**
     * Creates a location of the specified type at the given coordinate.
     *
     * @param locationType the type of the location
     * @param coordinate the coordinate of the location
     * @return the created location
     */
    public Location createLocation(LocationType locationType, Coordinate coordinate) {
        return switch (locationType) {
            case FOREST -> new Location(LocationType.FOREST, coordinate);
            case SEA -> new Location(LocationType.SEA, coordinate);
            case MOUNTAIN -> new Location(LocationType.MOUNTAIN, coordinate);
        };
    }

    /**
     * Creates a location of a random type at the given coordinate.
     * The type of the location is determined by the {@code RandomizerService}.
     *
     * @param coordinate the coordinate of the location
     * @return the created location
     */
    public Location createRandomLocation(Coordinate coordinate) {
        var randomLocationType = randomizerService.getRandomLocationType();
        return createLocation(randomLocationType, coordinate);
    }
}
