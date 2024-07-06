package org.example.service;

import org.example.entity.area.Area;
import org.example.entity.location.Location;

public class HungerService {
    private final double hungerRatio;

    public HungerService(double hungerRatio) {
        this.hungerRatio = hungerRatio;
    }

    public void increaseHunger(Area area) {
        area.getLocations()
            .parallelStream()
            .forEach(this::increaseHungerInLocation);
    }

    private void increaseHungerInLocation(Location location) {
        location.getAnimals()
                .parallelStream()
                .forEach(animal -> animal.hunger(hungerRatio));
    }
}
