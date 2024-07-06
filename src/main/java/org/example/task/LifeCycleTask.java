package org.example.task;

import org.example.entity.area.Area;
import org.example.service.*;
import org.example.view.View;

public class LifeCycleTask implements Runnable {
    private final View view;
    private final Area area;
    private final SimulationService simulationService;
    private final PopulationService populationService;
    private final StatisticsService statisticsService;
    private final TerminationService terminationService;
    private final DeadOrganismRemover deadOrganismRemover;
    private final DelayService delayService;

    public LifeCycleTask(View view,
                         Area area,
                         SimulationService simulationService,
                         PopulationService populationService,
                         StatisticsService statisticsService,
                         TerminationService terminationService,
                         DeadOrganismRemover deadOrganismRemover,
                         DelayService delayService) {
        this.view = view;
        this.area = area;
        this.simulationService = simulationService;
        this.populationService = populationService;
        this.statisticsService = statisticsService;
        this.terminationService = terminationService;
        this.deadOrganismRemover = deadOrganismRemover;
        this.delayService = delayService;
    }

    @Override
    public void run() {
        view.showWorldMap(area);
        populationService.populateOrganisms(area);
        view.showTotalAliveOrganisms(area);
        while (!terminationService.isSimulationFinished(area)) {
            view.showIterationNumber();
            simulationService.simulateLife(area);
            statisticsService.increaseIterationCounter();
            deadOrganismRemover.removeDeadOrganisms(area);
            view.showAliveToday();
            view.showStatistics(area);
            delayService.delay();
        }
    }
}
