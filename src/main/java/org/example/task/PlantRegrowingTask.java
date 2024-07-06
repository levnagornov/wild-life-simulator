package org.example.task;

import org.example.entity.area.Area;
import org.example.service.PopulationService;

public class PlantRegrowingTask implements Runnable {
    private final Area area;
    private final PopulationService populationService;

    public PlantRegrowingTask(Area area,
                              PopulationService populationService) {
        this.area = area;
        this.populationService = populationService;
    }

    @Override
    public void run() {
        populationService.populatePlants(area);
    }
}
