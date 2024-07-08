package org.example.service;

import org.example.entity.area.Area;
import org.example.entity.location.Location;

/**
 * The {@code HungerService} class manages the increase of hunger levels
 * for animals within specified locations of an area.
 */
public class HungerService {
    private final double hungerRatio;

    /**
     * Constructs a new {@code HungerService} with the specified hunger ratio.
     *
     * @param hungerRatio the ratio by which hunger levels are increased
     */
    public HungerService(double hungerRatio) {
        this.hungerRatio = hungerRatio;
    }

    /**
     * Increases the hunger levels for all animals in the specified area.
     *
     * @param area the area where animals' hunger levels are increased
     */
    public void increaseHunger(Area area) {
        area.getLocations()
            .parallelStream()
            .forEach(this::increaseHungerInLocation);
    }

    /**
     * Increases the hunger level for animals in a specific location.
     *
     * @param location the location where animals' hunger levels are increased
     */
    private void increaseHungerInLocation(Location location) {
        location.getAnimals()
                .parallelStream()
                .forEach(animal -> animal.hunger(hungerRatio));
    }
}
