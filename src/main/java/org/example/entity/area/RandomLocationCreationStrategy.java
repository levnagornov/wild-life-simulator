package org.example.entity.area;

import org.example.entity.coordinate.CoordinateFactory;
import org.example.entity.location.Location;
import org.example.entity.location.LocationFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@code RandomLocationCreationStrategy} class implements {@code LocationCreationStrategy}.
 * It generates a grid of {@code Location} instances across a specified height and width using random coordinates.
 */
public class RandomLocationCreationStrategy implements LocationCreationStrategy {
    private final int height;
    private final int width;
    private final LocationFactory locationFactory;
    private final CoordinateFactory coordinateFactory;

    /**
     * Constructs a {@code RandomLocationCreationStrategy} with the specified height, width, location factory, and coordinate factory.
     *
     * @param height the height of the grid
     * @param width the width of the grid
     * @param locationFactory the factory used to create locations
     * @param coordinateFactory the factory used to create coordinates
     */
    public RandomLocationCreationStrategy(int height, int width, LocationFactory locationFactory, CoordinateFactory coordinateFactory) {
        this.height = height;
        this.width = width;
        this.locationFactory = locationFactory;
        this.coordinateFactory = coordinateFactory;
    }

    /**
     * Creates and returns a list of {@code Location} instances distributed across the grid.
     * Each location is created using a random location type generated by the location factory.
     *
     * @return a list of randomly created locations
     */
    @Override
    public List<Location> createLocations() {
        var locations = new ArrayList<Location>();

        for (var y = 0; y < height; y++) {
            for (var x = 0; x < width; x++) {
                var coordinate = coordinateFactory.getCoordinate(y, x);
                var location = locationFactory.createRandomLocation(coordinate);
                locations.add(location);
            }
        }

        return locations;
    }
}
