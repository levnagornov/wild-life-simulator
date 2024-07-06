package org.example.entity.area;

import org.example.entity.location.Location;

import java.util.List;

/**
 * The {@code LocationCreationStrategy} interface defines a strategy for creating a list of {@code Location} instances.
 * Implementations of this interface are responsible for providing the specific logic to create locations.
 */
public interface LocationCreationStrategy {

    /**
     * Creates and returns a list of {@code Location} instances.
     *
     * @return a list of locations
     */
    List<Location> createLocations();
}
