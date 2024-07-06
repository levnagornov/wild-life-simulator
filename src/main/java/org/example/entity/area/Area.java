package org.example.entity.area;

import org.example.entity.coordinate.Coordinate;
import org.example.entity.location.Location;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * The {@code Area} class represents an abstract area composed of multiple locations.
 * It defines the basic properties and behaviors of an area, such as height, width, and a list of locations.
 */
public abstract class Area {
    private final int height;
    private final int width;
    private final List<Location> locations;

    /**
     * Constructs an {@code Area} with the specified height and width.
     * Initializes an empty list of locations.
     *
     * @param height the height of the area
     * @param width the width of the area
     */
    public Area(int height, int width) {
        this.height = height;
        this.width = width;
        this.locations = new CopyOnWriteArrayList<>();
    }

    /**
     * Returns the height of the area.
     *
     * @return the height of the area
     */
    public int getHeight() {
        return height;
    }

    /**
     * Returns the width of the area.
     *
     * @return the width of the area
     */
    public int getWidth() {
        return width;
    }

    /**
     * Returns the list of locations in the area.
     *
     * @return the list of locations in the area
     */
    public List<Location> getLocations() {
        return locations;
    }

    /**
     * Adds a location to the area.
     *
     * @param location the location to be added
     */
    public void addLocation(Location location) {
        locations.add(location);
    }

    /**
     * Returns the location at the specified coordinate.
     * This method must be implemented by subclasses to provide specific behavior.
     *
     * @param coordinate the coordinate of the location to be retrieved
     * @return the location at the specified coordinate
     */
    public abstract Location getLocationByCoordinate(Coordinate coordinate);
}
