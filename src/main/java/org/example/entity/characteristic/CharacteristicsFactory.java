package org.example.entity.characteristic;

import org.example.entity.organism.Organism;
import org.example.config.reader.ConfigReader;

import java.util.Map;

/**
 * The {@code CharacteristicsFactory} class is responsible for providing {@code Characteristics} for different types of organisms.
 * It uses a {@code ConfigReader} to load characteristics from a configuration source.
 */
public class CharacteristicsFactory {
    private final Map<Class<? extends Organism>, Characteristics> characteristics;

    /**
     * Constructs a {@code CharacteristicsFactory} with the specified {@code ConfigReader}.
     * The characteristics are read from the configuration source during initialization.
     *
     * @param configReader the configuration reader used to read the characteristics
     */
    public CharacteristicsFactory(ConfigReader configReader) {
        this.characteristics = configReader.readCharacteristics();
    }

    /**
     * Returns the {@code Characteristics} for the specified organism class.
     * If the characteristics for the given organism class are not found, an {@code IllegalArgumentException} is thrown.
     *
     * @param organism the class of the organism
     * @return the characteristics for the specified organism class
     * @throws IllegalArgumentException if the characteristics for the specified organism class are not found
     */
    public Characteristics getCharacteristics(Class<? extends Organism> organism) {
        if (!characteristics.containsKey(organism)) {
            throw new IllegalArgumentException("Can't find characteristics for " + organism);
        }

        return characteristics.get(organism);
    }
}
