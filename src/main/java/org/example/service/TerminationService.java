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

/**
 * Service class for determining if the simulation should be terminated based on configured termination conditions.
 */
public class TerminationService {
    private static final Logger logger = LoggerFactory.getLogger(TerminationService.class);

    private final TerminationConfig terminationConfig;
    private final StatisticsService statisticsService;

    /**
     * Constructs a TerminationService with termination configuration and statistics service.
     *
     * @param terminationConfig Configuration for termination conditions.
     * @param statisticsService Service for collecting and providing statistics.
     */
    public TerminationService(TerminationConfig terminationConfig,
                              StatisticsService statisticsService) {
        this.terminationConfig = terminationConfig;
        this.statisticsService = statisticsService;
    }

    /**
     * Checks if the simulation should be finished based on configured termination conditions.
     *
     * @param area The simulation area.
     * @return true if the simulation should be finished, otherwise false.
     */
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

    /**
     * Checks if the iteration limit is reached.
     *
     * @return true if the iteration limit is reached, otherwise false.
     */
    private boolean isIterationLimitReached() {
        return terminationConfig.iterationLimit()
                && statisticsService.getCurrentIterationCounter() > terminationConfig.iterationCount();
    }

    /**
     * Checks if all predators are dead in the area based on configured condition.
     *
     * @param area The simulation area.
     * @return true if all predators are dead, otherwise false.
     */
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

    /**
     * Checks if all herbivores are dead in the area based on configured condition.
     *
     * @param area The simulation area.
     * @return true if all herbivores are dead, otherwise false.
     */
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

    /**
     * Checks if all animals are dead in the area based on configured condition.
     *
     * @param area The simulation area.
     * @return true if all animals are dead, otherwise false.
     */
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
