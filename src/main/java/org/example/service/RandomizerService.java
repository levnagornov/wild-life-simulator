package org.example.service;

import org.example.entity.characteristic.CharacteristicsFactory;
import org.example.entity.location.Location;
import org.example.entity.location.LocationType;
import org.example.entity.organism.Organism;

import java.util.List;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.concurrent.ThreadLocalRandom;

public class RandomizerService {
    private final CharacteristicsFactory characteristicsFactory;
    private final NavigableMap<Double, LocationType> probabilityMap;
    private final ThreadLocalRandom random;
    private double totalProbability;

    public RandomizerService(CharacteristicsFactory characteristicsFactory) {
        this.characteristicsFactory = characteristicsFactory;
        this.probabilityMap = initializeProbabilityMap();
        this.random = ThreadLocalRandom.current();
    }

    public LocationType getRandomLocationType() {
        var randomValue = random.nextDouble() * totalProbability;

        return probabilityMap.higherEntry(randomValue).getValue();
    }

    public int getRandomPopulationAmount(Class<? extends Organism> organismClass) {
        var maxSpeciesPerCoordinate = characteristicsFactory.getCharacteristics(organismClass).maxSpeciesPerCoordinate();

        return random.nextInt(maxSpeciesPerCoordinate);
    }

    public int getRandomPopulationAmountForLocation(Class<? extends Organism> organismClass, Location location) {
        var maxSpeciesPerCoordinate = characteristicsFactory.getCharacteristics(organismClass).maxSpeciesPerCoordinate();
        var speciesInLocation = location.getSpeciesCounterMap().get(organismClass);
        var availableSlots = maxSpeciesPerCoordinate - speciesInLocation;

        return random.nextInt(availableSlots);
    }

    public int getRandomOffspringAmount(Class<? extends Organism> organismClass) {
        var maxSpeciesPerCoordinate = characteristicsFactory.getCharacteristics(organismClass).maxSpeciesPerCoordinate();
        var maxOffspringAmount = Integer.parseInt(Integer.toString(maxSpeciesPerCoordinate).substring(0, 1));

        return random.nextInt(0, maxOffspringAmount + 1);
    }

    public boolean isSuccessfulAttempt(double chance) {
        return random.nextDouble() < chance;
    }

    public <T> int getRandomIndex(List<T> list) {
        return random.nextInt(0, list.size());
    }

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
