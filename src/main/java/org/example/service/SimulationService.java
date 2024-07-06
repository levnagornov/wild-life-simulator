package org.example.service;

import org.example.entity.area.Area;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimulationService {
    private static final Logger logger = LoggerFactory.getLogger(SimulationService.class);

    private final MovementService movementService;
    private final FeedingService feedingService;
    private final ReproductionService reproductionService;
    private final HungerService hungerService;

    public SimulationService(MovementService movementService,
                             FeedingService feedingService,
                             ReproductionService reproductionService,
                             HungerService hungerService) {
        this.movementService = movementService;
        this.feedingService = feedingService;
        this.reproductionService = reproductionService;
        this.hungerService = hungerService;
    }

    public void simulateLife(Area area) {
        logger.info("Simulation cycle started");
        movementService.move(area);
        feedingService.feed(area);
        reproductionService.reproduce(area);
        hungerService.increaseHunger(area);
        logger.info("Simulation cycle has been completed");
    }
}
