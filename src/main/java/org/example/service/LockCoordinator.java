package org.example.service;

import org.example.entity.location.Location;

public class LockCoordinator {
    public Location getFirstLocationToLock(Location currentLocation, Location newLocation) {
        return currentLocation.getId() < newLocation.getId() ? currentLocation : newLocation;
    }

    public Location getSecondLocationToLock(Location firstLocationToLock, Location currentLocation, Location newLocation) {
        return firstLocationToLock == currentLocation ? newLocation : currentLocation;
    }
}
