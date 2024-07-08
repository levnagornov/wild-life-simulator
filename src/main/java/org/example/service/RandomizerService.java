package org.example.service;

import org.example.entity.characteristic.CharacteristicsFactory;
import org.example.entity.location.Location;
import org.example.entity.location.LocationType;
import org.example.entity.organism.Organism;

import java.util.List;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.concurrent.ThreadLocalRandom;

/**
 * The {@code RandomizerService} provides methods for generating random values and making probabilistic decisions.
 */
public class RandomizerService {
    private final CharacteristicsFactory characteristicsFactory;
    private final NavigableMap<Double, LocationType> probabilityMap;
    private final ThreadLocalRandom random;
    private double totalProbability;

    /**
     * Constructs a {@code RandomizerService} with the given characteristics factory.
     *
     * @param characteristicsFactory the factory for organism characteristics
     */
    public RandomizerService(CharacteristicsFactory characteristicsFactory) {
        this.characteristicsFactory = characteristicsFactory;
        this.probabilityMap = initializeProbabilityMap();
        this.random = ThreadLocalRandom.current();
    }

    /**
     * Retrieves a random location type based on predefined probabilities.
     *
     * @return a random location type
     */
    public LocationType getRandomLocationType() {
        var randomValue = random.nextDouble() * totalProbability;

        return probabilityMap.higherEntry(randomValue).getValue();
    }

    /**
     * Generates a random population amount for a given organism class.
     * It's based on the animals characteristic {@code maxSpeciesPerCoordinate}.
     *
     * @param organismClass the class of organism
     * @return the random population amount
     */
    public int getRandomPopulationAmount(Class<? extends Organism> organismClass) {
        var maxSpeciesPerCoordinate = characteristicsFactory.getCharacteristics(organismClass).maxSpeciesPerCoordinate();

        return random.nextInt(maxSpeciesPerCoordinate);
    }

    /**
     * Generates a random population amount for a given organism class and location.
     * It's based on the animals characteristic {@code maxSpeciesPerCoordinate} and it also
     * considers already existing animals in the location.
     *
     * @param organismClass the class of organism
     * @param location      the location to consider
     * @return the random population amount
     */
    public int getRandomPopulationAmountForLocation(Class<? extends Organism> organismClass, Location location) {
        var maxSpeciesPerCoordinate = characteristicsFactory.getCharacteristics(organismClass).maxSpeciesPerCoordinate();
        var speciesInLocation = location.getSpeciesCounterMap().get(organismClass);
        var availableSlots = maxSpeciesPerCoordinate - speciesInLocation;

        return random.nextInt(availableSlots);
    }

    /**
     * Generates a random offspring amount for a given organism class.
     *
     * @param organismClass the class of organism
     * @return the random offspring amount
     */
    public int getRandomOffspringAmount(Class<? extends Organism> organismClass) {
        var maxSpeciesPerCoordinate = characteristicsFactory.getCharacteristics(organismClass).maxSpeciesPerCoordinate();
        var maxOffspringAmount = Integer.parseInt(Integer.toString(maxSpeciesPerCoordinate).substring(0, 1));

        return random.nextInt(0, maxOffspringAmount + 1);
    }

    /**
     * Determines if an attempt with the given chance is successful.
     *
     * @param chance the chance of success (between 0 and 1)
     * @return {@code true} if successful, otherwise {@code false}
     */
    public boolean isSuccessfulAttempt(double chance) {
        return random.nextDouble() < chance;
    }

    /**
     * Retrieves a random index for a list.
     *
     * @param list the list to retrieve index from
     * @param <T>  the type of elements in the list
     * @return the random index
     */
    public <T> int getRandomIndex(List<T> list) {
        return random.nextInt(0, list.size());
    }

    /**
     * Initializes the probability map based on location types and their probabilities.
     *
     * @return the initialized probability map
     */
    private NavigableMap<Double, LocationType> initializeProbabilityMap() {
        var map = new TreeMap<Double, LocationType>();

        for (var locationType : LocationType.values()) {
            if (locationType.getProbability() <= 0) {
                continue;
            }
            totalProbability += locationType.getProbability();
            map.put(totalProbability, locationType);
        }

        return map;
    }
}
