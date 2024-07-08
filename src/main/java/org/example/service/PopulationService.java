package org.example.service;

import org.example.entity.coordinate.Coordinate;
import org.example.entity.area.Area;
import org.example.entity.location.Location;
import org.example.entity.organism.Organism;
import org.example.entity.organism.OrganismFactory;
import org.example.entity.organism.OrganismRegistry;
import org.example.entity.organism.plant.Plant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.IntStream;

/**
 * The {@code PopulationService} class handles population of organisms and plants in the specified area.
 */
public class PopulationService {
    private static final Logger logger = LoggerFactory.getLogger(PopulationService.class);

    private final OrganismRegistry organismRegistry;
    private final OrganismFactory organismFactory;
    private final RandomizerService randomizerService;
    private final String organisms;
    private final String plants;

    /**
     * Constructs a new {@code PopulationService} with the given dependencies.
     *
     * @param organismRegistry  the registry of organisms
     * @param organismFactory   the factory to create organisms
     * @param randomizerService the service for randomization
     */
    public PopulationService(OrganismRegistry organismRegistry,
                             OrganismFactory organismFactory,
                             RandomizerService randomizerService) {
        this.organismRegistry = organismRegistry;
        this.organismFactory = organismFactory;
        this.randomizerService = randomizerService;
        this.organisms = "Organisms";
        this.plants = "Plants";
    }

    /**
     * Populates organisms in the specified area.
     *
     * @param area the area to populate with organisms
     */
    public void populateOrganisms(Area area) {
        populate(organisms, area, this::populateOrganismsInLocation);
    }

    /**
     * Populates plants in the specified area.
     *
     * @param area the area to populate with plants
     */
    public void populatePlants(Area area) {
        populate(plants, area, this::populatePlantsInLocation);
    }

    /**
     * Populates entities of the specified type in the area.
     *
     * @param type            the type of entities to populate (organisms or plants)
     * @param area            the area to populate
     * @param locationConsumer the consumer to populate entities in each location
     */
    private void populate(String type, Area area, Consumer<Location> locationConsumer) {
        logger.debug("{} populating has been started", type);

        area.getLocations()
            .parallelStream()
            .filter(location -> location.getLocationType().isHabitable())
            .forEach(locationConsumer);

        logger.debug("{} populating has been completed", type);
    }

    /**
     * Populates plants in the specified location.
     *
     * @param location the location to populate with plants
     */
    private void populatePlantsInLocation(Location location) {
        var plants = organismRegistry.getPossibleOrganisms()
                .stream()
                .filter(Plant.class::isAssignableFrom)
                .flatMap(organismClass -> generateOrganismList(organismClass, location.getCoordinate(), randomizerService.getRandomPopulationAmountForLocation(organismClass, location)).stream())
                .toList();

        location.addOrganisms(plants);

        logger.debug("{} plants were populated successfully in location: {}", plants.size(), location);
    }

    /**
     * Populates organisms in the specified location.
     *
     * @param location the location to populate with organisms
     */
    private void populateOrganismsInLocation(Location location) {
        var organisms = organismRegistry.getPossibleOrganisms()
                .stream()
                .flatMap(organismClass -> generateOrganismList(organismClass, location.getCoordinate(), randomizerService.getRandomPopulationAmount(organismClass)).stream())
                .toList();

        location.addOrganisms(organisms);

        logger.debug("{} organisms were populated successfully in location: {}", organisms.size(), location);
    }

    /**
     * Generates a list of organisms of the specified class at the given coordinate.
     *
     * @param organismClass the class of organisms to generate
     * @param coordinate    the coordinate for the organisms
     * @param populationAmount the number of organisms to generate
     * @return a list of generated organisms
     */
    private List<Organism> generateOrganismList(Class<? extends Organism> organismClass, Coordinate coordinate, int populationAmount) {
        return IntStream.range(0, populationAmount)
                .mapToObj(i -> organismFactory.createOrganism(organismClass, coordinate))
                .toList();
    }
}