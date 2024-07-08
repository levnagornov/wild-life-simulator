package org.example.task;

import org.example.entity.area.Area;
import org.example.service.*;
import org.example.view.View;

/**
 * Represents a task that runs the life cycle of a simulation.
 */
public class LifeCycleTask implements Runnable {
    private final View view;
    private final Area area;
    private final SimulationService simulationService;
    private final PopulationService populationService;
    private final StatisticsService statisticsService;
    private final TerminationService terminationService;
    private final DeadOrganismRemover deadOrganismRemover;
    private final DelayService delayService;

    /**
     * Constructs a LifeCycleTask with the specified dependencies.
     *
     * @param view                 The view responsible for displaying the simulation.
     * @param area                 The simulation area containing locations and organisms.
     * @param simulationService    Service for simulating the life cycle of organisms.
     * @param populationService    Service for populating organisms and plants in the area.
     * @param statisticsService    Service for maintaining statistics of the simulation.
     * @param terminationService   Service for checking termination conditions of the simulation.
     * @param deadOrganismRemover  Service for removing dead organisms from the simulation area.
     * @param delayService         Service for introducing delays between simulation cycles.
     */
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

    /**
     * Runs the life cycle of the simulation until termination conditions are met.
     */
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
