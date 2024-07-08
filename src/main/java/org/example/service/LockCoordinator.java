package org.example.service;

import org.example.entity.location.Location;

/**
 * The {@code LockCoordinator} class coordinates locking of entities.
 */
public class LockCoordinator {

    /**
     * Determines the first location to lock based on their IDs.
     *
     * @param currentLocation the current location
     * @param newLocation     the new location
     * @return the location with the smaller ID
     */
    public Location getFirstLocationToLock(Location currentLocation, Location newLocation) {
        return currentLocation.getId() < newLocation.getId() ? currentLocation : newLocation;
    }

    /**
     * Determines the second location to lock based on the first location and their IDs.
     *
     * @param firstLocationToLock the first location to lock
     * @param currentLocation     the current location
     * @param newLocation         the new location
     * @return the location different from the first location to lock
     */
    public Location getSecondLocationToLock(Location firstLocationToLock, Location currentLocation, Location newLocation) {
        return firstLocationToLock == currentLocation ? newLocation : currentLocation;
    }
}
