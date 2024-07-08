package org.example.service;

import org.example.entity.area.Area;
import org.example.entity.location.Location;
import org.example.entity.organism.animal.Animal;
import org.example.logger.TimeExecutionLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The {@code MovementService} class manages animal movement within an area.
 */
public class MovementService {
    private static final Logger logger = LoggerFactory.getLogger(MovementService.class);

    private final MovementCalculator movementCalculator;
    private final LockCoordinator lockCoordinator;
    private final TimeExecutionLogger timeExecutionLogger;
    private Area area;

    /**
     * Constructs a {@code MovementService} with the specified dependencies.
     *
     * @param movementCalculator the calculator for calculating next coordinates
     * @param lockCoordinator    the coordinator for managing locks during movement
     * @param timeExecutionLogger the logger for logging execution time
     */
    public MovementService(MovementCalculator movementCalculator,
                           LockCoordinator lockCoordinator,
                           TimeExecutionLogger timeExecutionLogger) {
        this.movementCalculator = movementCalculator;
        this.lockCoordinator = lockCoordinator;
        this.timeExecutionLogger = timeExecutionLogger;
    }

    /**
     * Moves all animals within the specified area.
     *
     * @param area the area where animals are moved
     */
    public void move(Area area) {
        this.area = area;
        timeExecutionLogger.logExecutionTime("Movement", moveAnimals(area));
    }

    /**
     * Returns a runnable that moves animals in each location of the area in parallel.
     *
     * @param area the area where animals are moved
     * @return a runnable for moving animals
     */
    private Runnable moveAnimals(Area area) {
        return () -> area.getLocations()
                         .parallelStream()
                         .forEach(this::moveAnimalsInLocation);
    }

    /**
     * Moves animals within a specific location.
     *
     * @param location the location where animals are moved
     */
    private void moveAnimalsInLocation(Location location) {
        location.getAnimals()
                .parallelStream()
                .forEach(this::moveAnimal);
    }

    /**
     * Moves a specific animal to its next coordinate based on movement calculations.
     *
     * @param animal the animal to be moved
     */
    private void moveAnimal(Animal animal) {
        if (animal.getCharacteristics().moveSpeed() == 0) {
            logger.debug("Can't move animal {} the move speed is set 0", animal.getClass().getSimpleName() + animal.getId());
            return;
        }

        logger.debug("Move animal {}", animal.getClass().getSimpleName() + animal.getId());

        var currentCoordinate = animal.getCoordinate();
        var newCoordinate = movementCalculator.calculateNextCoordinate(area, animal);
        logger.debug("The current coordinate is {}", currentCoordinate);
        logger.debug("The new coordinate is {}", currentCoordinate);

        if (currentCoordinate.equals(newCoordinate)) {
            logger.debug("The new coordinate equals to the old one. Animal will stay at the same location");
            return;
        }

        logger.debug("An attempt to move the animal to a new location");

        var currentLocation = area.getLocationByCoordinate(currentCoordinate);
        var newLocation = area.getLocationByCoordinate(newCoordinate);
        logger.debug("The current location is {}", currentLocation);
        logger.debug("The new location is {}", currentLocation);

        var firstLocationToLock = lockCoordinator.getFirstLocationToLock(currentLocation, newLocation);
        var secondLocationToLock = lockCoordinator.getSecondLocationToLock(firstLocationToLock, currentLocation, newLocation);

        var firstLocationLock = firstLocationToLock.getReentrantLock();
        var secondLocationLock = secondLocationToLock.getReentrantLock();

        firstLocationLock.lock();
        logger.debug("The first location lock has been acquired");

        secondLocationLock.lock();
        logger.debug("The second location lock has been acquired");

        try {
            animal.move(newCoordinate);
            newLocation.addOrganism(animal);
            currentLocation.removeOrganism(animal);
        } finally {
            firstLocationLock.unlock();
            logger.debug("The first location lock has been released");

            secondLocationLock.unlock();
            logger.debug("The second location lock has been released");
        }

        logger.debug("The animal has moved successfully.");
    }
}
