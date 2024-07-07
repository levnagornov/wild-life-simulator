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

public class PopulationService {
    private static final Logger logger = LoggerFactory.getLogger(PopulationService.class);

    private final OrganismRegistry organismRegistry;
    private final OrganismFactory organismFactory;
    private final RandomizerService randomizerService;
    private final String organisms;
    private final String plants;

    public PopulationService(OrganismRegistry organismRegistry,
                             OrganismFactory organismFactory,
                             RandomizerService randomizerService) {
        this.organismRegistry = organismRegistry;
        this.organismFactory = organismFactory;
        this.randomizerService = randomizerService;
        this.organisms = "Organisms";
        this.plants = "Plants";
    }

    public void populateOrganisms(Area area) {
        populate(organisms, area, this::populateOrganismsInLocation);
    }

    public void populatePlants(Area area) {
        populate(plants, area, this::populatePlantsInLocation);
    }

    private void populate(String type, Area area, Consumer<Location> locationConsumer) {
        logger.debug("{} populating has been started", type);

        area.getLocations()
            .parallelStream()
            .filter(location -> location.getLocationType().isHabitable())
            .forEach(locationConsumer);

        logger.debug("{} populating has been completed", type);
    }

    private void populatePlantsInLocation(Location location) {
        var plants = organismRegistry.getPossibleOrganisms()
                .stream()
                .filter(Plant.class::isAssignableFrom)
                .flatMap(organismClass -> generateOrganismList(organismClass, location.getCoordinate(), randomizerService.getRandomPopulationAmountForLocation(organismClass, location)).stream())
                .toList();

        location.addOrganisms(plants);

        logger.debug("{} plants were populated successfully in location: {}", plants.size(), location);
    }

    private void populateOrganismsInLocation(Location location) {
        var organisms = organismRegistry.getPossibleOrganisms()
                .stream()
                .flatMap(organismClass -> generateOrganismList(organismClass, location.getCoordinate(), randomizerService.getRandomPopulationAmount(organismClass)).stream())
                .toList();

        location.addOrganisms(organisms);

        logger.debug("{} organisms were populated successfully in location: {}", organisms.size(), location);
    }

    private List<Organism> generateOrganismList(Class<? extends Organism> organismClass, Coordinate coordinate, int populationAmount) {
        return IntStream.range(0, populationAmount)
                .mapToObj(i -> organismFactory.createOrganism(organismClass, coordinate))
                .toList();
    }
}