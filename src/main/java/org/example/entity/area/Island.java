package org.example.entity.area;

import org.example.entity.coordinate.Coordinate;
import org.example.entity.location.Location;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * The {@code Island} class represents an island, which is a specific type of {@code Area}.
 * It uses a {@code LocationCreationStrategy} to create its locations and maps coordinates to locations for quick access.
 */
public class Island extends Area {
    private final LocationCreationStrategy locationCreationStrategy;
    private final Map<Coordinate, Location> coordinateToLocationsMap;

    /**
     * Constructs an {@code Island} with the specified height, width, and location creation strategy.
     * Initializes the locations using the provided strategy and creates a map from coordinates to locations.
     *
     * @param height the height of the island
     * @param width the width of the island
     * @param locationCreationStrategy the strategy used to create locations on the island
     */
    public Island(int height, int width, LocationCreationStrategy locationCreationStrategy) {
        super(height, width);
        this.locationCreationStrategy = locationCreationStrategy;
        createLocations();
        this.coordinateToLocationsMap = initializeCoordinateToLocationMap();
    }

    /**
     * Returns the location at the specified coordinate.
     *
     * @param coordinate the coordinate of the location to be retrieved
     * @return the location at the specified coordinate, or {@code null} if no location exists at that coordinate
     */
    @Override
    public Location getLocationByCoordinate(Coordinate coordinate) {
        return coordinateToLocationsMap.get(coordinate);
    }

    /**
     * Creates locations using the specified location creation strategy and adds them to the island.
     */
    private void createLocations() {
        locationCreationStrategy.createLocations().forEach(this::addLocation);
    }

    /**
     * Initializes a map from coordinates to locations for quick access.
     *
     * @return a map from coordinates to locations
     */
    private Map<Coordinate, Location> initializeCoordinateToLocationMap() {
        return this.getLocations()
                .parallelStream()
                .collect(Collectors.toMap(Location::getCoordinate, location -> location, (a, b) -> b, ConcurrentHashMap::new));
    }
}
