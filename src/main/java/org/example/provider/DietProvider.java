package org.example.provider;

import org.example.entity.organism.Organism;
import org.example.config.reader.ConfigReader;

import java.util.Map;

/**
 * The {@code DietProvider} class provides diet information for organisms.
 * It reads the diet configuration from a {@code ConfigReader} and stores it in a map.
 */
public class DietProvider {
    private final Map<Class<? extends Organism>, Map<Class<? extends Organism>, Double>> dietMap;

    /**
     * Constructs a new {@code DietProvider} with the specified configuration reader.
     *
     * @param configReader the configuration reader to use for reading diet information
     */
    public DietProvider(ConfigReader configReader) {
        this.dietMap = configReader.readDiets();
    }

    /**
     * Returns the diet information for the specified organism.
     * The diet information is a map where the keys are prey organisms and the values are the probabilities of eating them.
     *
     * @param organism the class of the organism for which to get the diet information
     * @return the diet information for the specified organism
     */
    public Map<Class<? extends Organism>, Double> getDiet(Class<? extends Organism> organism) {
        return dietMap.get(organism);
    }
}
