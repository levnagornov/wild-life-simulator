package org.example.entity.location;

/**
 * The {@code LocationType} enum represents different types of locations that can exist in the simulation.
 * Each location type has a probability of occurrence, and flags indicating if it is passable and habitable.
 */
public enum LocationType {
    FOREST(0.65, true, true),
    SEA(0.2, false, false),
    MOUNTAIN(0.15, false, false);

    private final double probability;
    private final boolean isPassable;
    private final boolean isHabitable;

    /**
     * Constructs a {@code LocationType} with the specified properties.
     *
     * @param probability the probability of this location type occurring
     * @param isPassable  whether this location type is passable
     * @param isHabitable whether this location type is habitable
     */
    LocationType(double probability, boolean isPassable, boolean isHabitable) {
        this.probability = probability;
        this.isPassable = isPassable;
        this.isHabitable = isHabitable;
    }

    /**
     * Returns the probability of this location type occurring.
     *
     * @return the probability of this location type
     */
    public double getProbability() {
        return probability;
    }

    /**
     * Returns whether this location type is passable.
     *
     * @return {@code true} if this location type is passable, {@code false} otherwise
     */
    public boolean isPassable() {
        return isPassable;
    }

    /**
     * Returns whether this location type is habitable.
     *
     * @return {@code true} if this location type is habitable, {@code false} otherwise
     */
    public boolean isHabitable() {
        return isHabitable;
    }
}
