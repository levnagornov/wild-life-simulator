package org.example.entity.organism;

import org.example.entity.coordinate.Coordinate;
import org.example.entity.characteristic.Characteristics;

import java.util.concurrent.atomic.AtomicLong;

/**
 * The {@code Organism} class represents a generic organism with unique characteristics
 * and a coordinate location in a simulation. This class is intended to be extended by
 * more specific types of organisms.
 * <p>
 * Each organism has a unique ID, characteristics, and a coordinate indicating its
 * position. The organism also has a state to determine if it is alive or dead.
 * </p>
 */
public abstract class Organism {
    private static final AtomicLong idCounter = new AtomicLong(0);

    private final long id;
    private final Characteristics characteristics;
    protected Coordinate coordinate;
    private volatile boolean isAlive;

    /**
     * Constructs an organism with specified characteristics and coordinate.
     *
     * @param characteristics the characteristics of the organism
     * @param coordinate the coordinate of the organism
     */
    public Organism(Characteristics characteristics,
                    Coordinate coordinate) {
        this.id = idCounter.incrementAndGet();
        this.characteristics = characteristics;
        this.coordinate = coordinate;
        this.isAlive = true;
    }

    /**
     * Returns the unique ID of this organism.
     *
     * @return the unique ID of this organism
     */
    public long getId() {
        return id;
    }

    /**
     * Returns the characteristics of this organism.
     *
     * @return the characteristics of this organism
     */
    public Characteristics getCharacteristics() {
        return characteristics;
    }

    /**
     * Returns the coordinate of this organism.
     *
     * @return the coordinate of this organism
     */
    public Coordinate getCoordinate() {
        return coordinate;
    }

    /**
     * Checks if the organism is alive.
     *
     * @return {@code true} if the organism is alive, {@code false} otherwise
     */
    public boolean isAlive() {
        return isAlive;
    }

    /**
     * Marks the organism as dead.
     */
    public void die() {
        isAlive = false;
    }

    /**
     * Compares this organism to the specified object. The result is {@code true} if
     * and only if the argument is not {@code null} and is an {@code Organism} object
     * that has the same ID as this object.
     *
     * @param o the object to compare this {@code Organism} against
     * @return {@code true} if the given object represents an {@code Organism} with the
     *         same ID, {@code false} otherwise
     */
    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Organism organism)) return false;

        return id == organism.id;
    }

    /**
     * Returns a hash code value for this organism. The hash code is based on the unique ID.
     *
     * @return a hash code value for this organism
     */
    @Override
    public int hashCode() {
        return Long.hashCode(id);
    }
}
