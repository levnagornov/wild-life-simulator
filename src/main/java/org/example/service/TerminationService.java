package org.example.service;

import org.example.config.TerminationConfig;
import org.example.entity.area.Area;
import org.example.entity.location.Location;
import org.example.entity.organism.animal.Animal;
import org.example.entity.organism.animal.herbivore.Herbivore;
import org.example.entity.organism.animal.predator.Predator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Predicate;

public class TerminationService {
    private static final Logger logger = LoggerFactory.getLogger(MovementService.class);

    private final TerminationConfig terminationConfig;
    private final StatisticsService statisticsService;

    public TerminationService(TerminationConfig terminationConfig,
                              StatisticsService statisticsService) {
        this.terminationConfig = terminationConfig;
        this.statisticsService = statisticsService;
    }

    public boolean isSimulationFinished(Area area) {
        var areAllHerbivoreDead = areAllHerbivoreDead(area);
        var areAllPredatorsDead = areAllPredatorsDead(area);
        var areAllAnimalsDead = areAllAnimalsDead(area);
        var isIterationLimitReached = isIterationLimitReached();
        var isSimulationFinished = areAllHerbivoreDead || areAllPredatorsDead || areAllAnimalsDead || isIterationLimitReached;

        logger.debug("Check if all herbivore are dead: {}", areAllHerbivoreDead);
        logger.debug("Check if all predators are dead: {}", areAllPredatorsDead);
        logger.debug("Check if all animals are dead: {}", areAllAnimalsDead);
        logger.debug("Check if iteration limit is reached: {}", isIterationLimitReached);
        logger.debug("Check if simulation should stop: {}", isSimulationFinished);

        return isSimulationFinished;
    }

    private boolean isIterationLimitReached() {
        return terminationConfig.iterationLimit()
                && statisticsService.getCurrentIterationCounter() > terminationConfig.iterationCount();
    }

    private boolean areAllPredatorsDead(Area area) {
        if (!terminationConfig.allPredatorsDead()) {
            return false;
        }

        // doesn't work with var
        Predicate<Location> hasAlivePredator = location -> location.getGroupedOrganismsBySuperclass()
                .get(Predator.class)
                .parallelStream()
                .anyMatch(organism -> organism.isAlive() && organism instanceof Predator);

        return area.getLocations()
                   .parallelStream()
                   .noneMatch(hasAlivePredator);
    }

    private boolean areAllHerbivoreDead(Area area) {
        if (!terminationConfig.allHerbivoreDead()) {
            return false;
        }

        // doesn't work with var
        Predicate<Location> hasAliveHerbivore = location -> location.getGroupedOrganismsBySuperclass()
                .get(Herbivore.class)
                .parallelStream()
                .anyMatch(organism -> organism.isAlive() && organism instanceof Herbivore);

        return area.getLocations()
                   .parallelStream()
                   .noneMatch(hasAliveHerbivore);
    }

    private boolean areAllAnimalsDead(Area area) {
        if (!terminationConfig.allAnimalsDead()) {
            return false;
        }

        // doesn't work with var
        Predicate<Location> hasAliveAnimal = location -> location.getGroupedOrganismsBySuperclass()
                .get(Animal.class)
                .parallelStream()
                .anyMatch(organism -> organism.isAlive() && organism instanceof Animal);

        return area.getLocations()
                   .parallelStream()
                   .noneMatch(hasAliveAnimal);
    }
}
