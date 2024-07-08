package org.example.service;

import org.example.entity.area.Area;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Service class responsible for simulating the life cycle in an area.
 */
public class SimulationService {
    private static final Logger logger = LoggerFactory.getLogger(SimulationService.class);

    private final MovementService movementService;
    private final FeedingService feedingService;
    private final ReproductionService reproductionService;
    private final HungerService hungerService;

    /**
     * Constructs a SimulationService with required dependencies.
     *
     * @param movementService     The service responsible for animal movement.
     * @param feedingService      The service responsible for feeding animals.
     * @param reproductionService The service responsible for animal reproduction.
     * @param hungerService       The service responsible for increasing hunger in animals.
     */
    public SimulationService(MovementService movementService,
                             FeedingService feedingService,
                             ReproductionService reproductionService,
                             HungerService hungerService) {
        this.movementService = movementService;
        this.feedingService = feedingService;
        this.reproductionService = reproductionService;
        this.hungerService = hungerService;
    }

    /**
     * Simulates a complete life cycle in the specified area.
     *
     * @param area The area in which the life cycle simulation should be conducted.
     */
    public void simulateLife(Area area) {
        logger.info("Simulation cycle started");
        movementService.move(area);
        feedingService.feed(area);
        reproductionService.reproduce(area);
        hungerService.increaseHunger(area);
        logger.info("Simulation cycle has been completed");
    }
}
