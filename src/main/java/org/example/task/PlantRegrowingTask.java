package org.example.task;

import org.example.entity.area.Area;
import org.example.service.PopulationService;

/**
 * Represents a task for regrowing plants in a specified area.
 */
public class PlantRegrowingTask implements Runnable {
    private final Area area;
    private final PopulationService populationService;

    /**
     * Constructs a PlantRegrowingTask with the specified area and population service.
     *
     * @param area             The area where plants will be populated.
     * @param populationService The service responsible for populating plants.
     */
    public PlantRegrowingTask(Area area,
                              PopulationService populationService) {
        this.area = area;
        this.populationService = populationService;
    }

    /**
     * Runs the task to populate plants in the area using the PopulationService.
     */
    @Override
    public void run() {
        populationService.populatePlants(area);
    }
}
