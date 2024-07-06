package org.example.entity.organism;

import org.example.entity.coordinate.Coordinate;
import org.example.entity.characteristic.CharacteristicsFactory;

/**
 * The {@code OrganismFactory} class is responsible for creating instances of different
 * types of organisms. It uses a registry of organism classes and their constructors
 * along with a characteristics factory to generate the required characteristics.
 */
public class OrganismFactory {
    private final OrganismRegistry organismRegistry;
    private final CharacteristicsFactory characteristicsFactory;

    /**
     * Constructs an {@code OrganismFactory} with the specified registry and
     * characteristics factory.
     *
     * @param organismRegistry the registry that holds the mapping of organism classes
     *                         to their constructors
     * @param characteristicsFactory the factory to create characteristics for organisms
     */
    public OrganismFactory(OrganismRegistry organismRegistry,
                           CharacteristicsFactory characteristicsFactory) {
        this.organismRegistry = organismRegistry;
        this.characteristicsFactory = characteristicsFactory;
    }

    /**
     * Creates an organism of the specified class and assign it the given coordinate.
     *
     * @param organismClass the class of the organism to create
     * @param coordinate the coordinate where the organism will be placed
     * @return the created organism
     * @throws IllegalArgumentException if the given organism class cannot be created
     */
    public Organism createOrganism(Class<? extends Organism> organismClass, Coordinate coordinate) {
        var organismRegistryMap = organismRegistry.getOrganismClassToConstructorMap();

        if (!organismRegistryMap.containsKey(organismClass)) {
            throw new IllegalArgumentException("The given %s can't be created.%n".formatted(organismClass));
        }

        var characteristics = characteristicsFactory.getCharacteristics(organismClass);

        return organismRegistryMap.get(organismClass).apply(characteristics, coordinate);
    }
}
