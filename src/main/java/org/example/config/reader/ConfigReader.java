package org.example.config.reader;

import org.example.config.SimulationConfig;
import org.example.config.TerminationConfig;
import org.example.config.ViewConfig;
import org.example.entity.characteristic.Characteristics;
import org.example.entity.location.LocationType;
import org.example.entity.organism.Organism;

import java.util.Map;

public interface ConfigReader {
    Map<Class<? extends Organism>, Map<Class<? extends Organism>, Double>> readDiets();

    Map<Class<? extends Organism>, Characteristics> readCharacteristics();

    Map<Class<? extends Organism>, String> readOrganismEmojis();

    Map<LocationType, String> readLocationTypeEmojis();

    SimulationConfig readSimulationConfig();

    TerminationConfig readTerminationConfig();

    ViewConfig readViewConfig();
}
