package org.example.config.reader;

import org.example.config.SimulationConfig;
import org.example.config.TerminationConfig;
import org.example.config.ViewConfig;
import org.example.entity.characteristic.Characteristics;
import org.example.entity.location.LocationType;
import org.example.entity.organism.Organism;

import java.util.Map;

/**
 * The {@code ConfigReader} interface defines methods for reading various configuration settings.
 * Implementations of this interface are responsible for providing the specific logic to read configurations.
 */
public interface ConfigReader {

    /**
     * Reads and returns the diet configurations for different organisms.
     * The diet configuration maps each organism class to a map that defines the probability of consuming other organism classes.
     *
     * @return a map where keys are organism classes and values are maps of other organism classes and their consumption probabilities
     */
    Map<Class<? extends Organism>, Map<Class<? extends Organism>, Double>> readDiets();

    /**
     * Reads and returns the characteristics of different organisms.
     *
     * @return a map where keys are organism classes and values are their respective characteristics
     */
    Map<Class<? extends Organism>, Characteristics> readCharacteristics();

    /**
     * Reads and returns the emojis associated with different organisms.
     *
     * @return a map where keys are organism classes and values are their respective emojis
     */
    Map<Class<? extends Organism>, String> readOrganismEmojis();

    /**
     * Reads and returns the emojis associated with different location types.
     *
     * @return a map where keys are location types and values are their respective emojis
     */
    Map<LocationType, String> readLocationTypeEmojis();

    /**
     * Reads and returns the simulation configuration settings.
     *
     * @return the simulation configuration settings
     */
    SimulationConfig readSimulationConfig();

    /**
     * Reads and returns the termination configuration settings.
     *
     * @return the termination configuration settings
     */
    TerminationConfig readTerminationConfig();

    /**
     * Reads and returns the view configuration settings.
     *
     * @return the view configuration settings
     */
    ViewConfig readViewConfig();
}
