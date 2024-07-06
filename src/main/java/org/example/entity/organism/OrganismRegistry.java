package org.example.entity.organism;

import org.example.entity.characteristic.Characteristics;
import org.example.entity.coordinate.Coordinate;
import org.example.entity.organism.animal.herbivore.*;
import org.example.entity.organism.animal.predator.*;
import org.example.entity.organism.plant.Grass;

import java.util.*;
import java.util.function.BiFunction;

/**
 * The {@code OrganismRegistry} class maintains mappings between organism names and their classes,
 * and between organism classes and their constructors. It ensures that all organisms are properly registered.
 * All the new implmentations must be registered here, otherwise you won't be able to create them with the factory.
 */
public class OrganismRegistry {
    private final Map<String, Class<? extends Organism>> organismNameToOrganismClassMap;
    private final Map<Class<? extends Organism>, BiFunction<Characteristics, Coordinate, Organism>> organismClassToConstructorMap;

    /**
     * Constructs an {@code OrganismRegistry} and initializes the mappings for organism names to classes
     * and classes to constructors. It also performs a consistency check on the registry.
     */
    public OrganismRegistry() {
        this.organismNameToOrganismClassMap = initializeClassMap();
        this.organismClassToConstructorMap = initializeConstructorMap();
        checkRegistry();
    }

    /**
     * Returns the class of the organism corresponding to the specified name.
     *
     * @param name the name of the organism
     * @return the class of the organism
     * @throws IllegalArgumentException if the organism name is unknown
     */
    public Class<? extends Organism> getClassByName(String name) {
        var organismClass = organismNameToOrganismClassMap.get(name);

        if (organismClass == null) {
            throw new IllegalArgumentException("Unknown organism: " + name);
        }

        return organismClass;
    }

    /**
     * Returns the mapping of organism classes to their constructors.
     *
     * @return the mapping of organism classes to constructors
     */
    public Map<Class<? extends Organism>, BiFunction<Characteristics, Coordinate, Organism>> getOrganismClassToConstructorMap() {
        return organismClassToConstructorMap;
    }

    /**
     * Returns a collection of all possible organism classes.
     *
     * @return a collection of all possible organism classes
     */
    public Collection<Class<? extends Organism>> getPossibleOrganisms() {
        return organismNameToOrganismClassMap.values();
    }

    /**
     * Initializes the mapping of organism names to their corresponding classes.
     * All the new implmentations must be registered here, otherwise you won't be able to create them with the factory.
     *
     * @return the mapping of organism names to classes
     */
    private Map<String, Class<? extends Organism>> initializeClassMap() {
        return Map.ofEntries(
                Map.entry("Boar", Boar.class),
                Map.entry("Buffalo", Buffalo.class),
                Map.entry("Caterpillar", Caterpillar.class),
                Map.entry("Deer", Deer.class),
                Map.entry("Duck", Duck.class),
                Map.entry("Goat", Goat.class),
                Map.entry("Horse", Horse.class),
                Map.entry("Mouse", Mouse.class),
                Map.entry("Rabbit", Rabbit.class),
                Map.entry("Sheep", Sheep.class),

                Map.entry("Bear", Bear.class),
                Map.entry("Boa", Boa.class),
                Map.entry("Eagle", Eagle.class),
                Map.entry("Fox", Fox.class),
                Map.entry("Wolf", Wolf.class),

                Map.entry("Grass", Grass.class)
        );
    }

    /**
     * Initializes the mapping of organism classes to their corresponding constructors.
     * All the new implmentations must be registered here, otherwise you won't be able to create them with the factory.
     *
     * @return the mapping of organism classes to constructors
     */
    private Map<Class<? extends Organism>, BiFunction<Characteristics, Coordinate, Organism>> initializeConstructorMap() {
        return Map.ofEntries(
                Map.entry(Boar.class, Boar::new),
                Map.entry(Buffalo.class, Buffalo::new),
                Map.entry(Caterpillar.class, Caterpillar::new),
                Map.entry(Deer.class, Deer::new),
                Map.entry(Duck.class, Duck::new),
                Map.entry(Goat.class, Goat::new),
                Map.entry(Horse.class, Horse::new),
                Map.entry(Mouse.class, Mouse::new),
                Map.entry(Rabbit.class, Rabbit::new),
                Map.entry(Sheep.class, Sheep::new),

                Map.entry(Bear.class, Bear::new),
                Map.entry(Boa.class, Boa::new),
                Map.entry(Eagle.class, Eagle::new),
                Map.entry(Fox.class, Fox::new),
                Map.entry(Wolf.class, Wolf::new),

                Map.entry(Grass.class, Grass::new)
        );
    }

    /**
     * Performs a consistency check on the registry to ensure that all organisms are correctly registered.
     *
     * @throws IllegalStateException if there is an inconsistency in the registration
     */
    private void checkRegistry() {
        var organismsFromClassMap = new HashSet<>(organismNameToOrganismClassMap.values());
        var organismsFromConstructorMap = organismClassToConstructorMap.keySet();

        var hasEqualSize = organismsFromClassMap.size() == organismsFromConstructorMap.size();
        var hasEqualElements = organismsFromClassMap.containsAll(organismsFromConstructorMap);

        if (!hasEqualSize || !hasEqualElements) {
            throw new IllegalStateException("Can't create registry, because organisms were incorrectly registered in the maps.");
        }
    }
}
