package org.example.entity.organism.animal;

import org.example.entity.coordinate.Coordinate;
import org.example.entity.organism.Organism;
import org.example.entity.characteristic.Characteristics;

/**
 * The {@code Animal} class represents a generic animal in the simulation, extending the {@code Organism} class.
 * It includes additional properties and behaviors specific to animals, such as satiety and readiness to mate.
 */
public abstract class Animal extends Organism {
    private double satiety;
    private boolean isReadyToMate;

    /**
     * Constructs an {@code Animal} with specified characteristics and coordinate.
     * Initializes the satiety based on the characteristics and sets the animal as not ready to mate.
     *
     * @param characteristics the characteristics of the animal
     * @param coordinate the coordinate of the animal
     */
    public Animal(Characteristics characteristics,
                  Coordinate coordinate) {
        super(characteristics, coordinate);
        this.satiety = characteristics.foodForSatiety() * characteristics.startSatietyRatio();
        this.isReadyToMate = false;
    }

    /**
     * Makes the animal eat another organism, increasing its satiety and marking it as ready to mate.
     * The eaten organism is marked as dead.
     *
     * @param organism the organism to be eaten
     */
    public void eat(Organism organism) {
        if (organism.isAlive()) {
            increaseSatiety(organism.getCharacteristics().weight());
            isReadyToMate = true;
            organism.die();
        }
    }

    /**
     * Moves the animal to a new coordinate.
     *
     * @param coordinate the new coordinate to move to
     */
    public void move(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    /**
     * Marks the animal as having reproduced, resetting its readiness to mate.
     */
    public void reproduce() {
        isReadyToMate = false;
    }

    /**
     * Checks if the animal is ready to mate.
     *
     * @return {@code true} if the animal is ready to mate, {@code false} otherwise
     */
    public boolean isReadyToMate() {
        return isReadyToMate;
    }

    /**
     * Reduces the animal's satiety based on the hunger ratio. If the hunger ratio is negative, throws an exception.
     * If the satiety falls below zero, the animal dies.
     *
     * @param hungerRatio the ratio by which to reduce satiety
     * @throws IllegalArgumentException if the hunger ratio is negative
     */
    public void hunger(double hungerRatio) {
        if (hungerRatio < 0) {
            throw new IllegalArgumentException("Hunger ratio can't be a negative number=" + hungerRatio);
        }

        satiety -= getCharacteristics().foodForSatiety() * hungerRatio;
        isReadyToMate = false;

        if (satiety < 0) {
            die();
        }
    }

    /**
     * Increases the animal's satiety by the specified amount. If the satiety value is negative, throws an exception.
     *
     * @param satiety the amount to increase satiety by
     * @throws IllegalArgumentException if the satiety value is negative
     */
    private void increaseSatiety(double satiety) {
        if (satiety < 0) {
            throw new IllegalArgumentException("Satiety can't be increased by a negative number=" + satiety);
        }

        this.satiety += satiety % getCharacteristics().weight();
    }
}
