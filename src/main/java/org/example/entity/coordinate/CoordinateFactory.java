package org.example.entity.coordinate;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The {@code CoordinateFactory} class is responsible for creating and managing {@code Coordinate} instances.
 * It ensures that for any given pair of coordinates (y, x), only one {@code Coordinate} object is created and reused.
 */
public class CoordinateFactory {
    private final Map<int[], Coordinate> coordinates;

    /**
     * Constructs a {@code CoordinateFactory} with an empty coordinate cache.
     */
    public CoordinateFactory() {
        coordinates = new ConcurrentHashMap<>();
    }

    /**
     * Returns a {@code Coordinate} object for the given y and x values.
     * If a coordinate with the specified values already exists, it returns the existing one.
     * Otherwise, it creates a new {@code Coordinate} object and stores it in the cache.
     *
     * @param y the y-coordinate
     * @param x the x-coordinate
     * @return a {@code Coordinate} object for the specified y and x values
     */
    public Coordinate getCoordinate(int y, int x) {
        var key = new int[]{y, x};
        coordinates.putIfAbsent(key, new Coordinate(y, x));

        return coordinates.get(key);
    }
}
