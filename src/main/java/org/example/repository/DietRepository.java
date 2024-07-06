package org.example.repository;

import org.example.entity.organism.Organism;
import org.example.config.reader.ConfigReader;

import java.util.Map;

public class DietRepository {
    private final Map<Class<? extends Organism>, Map<Class<? extends Organism>, Double>> dietMap;

    public DietRepository(ConfigReader configReader) {
        this.dietMap = configReader.readDiets();
    }

    public Map<Class<? extends Organism>, Double> getDiet(Class<? extends Organism> organism) {
        return dietMap.get(organism);
    }
}
