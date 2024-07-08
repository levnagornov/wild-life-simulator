package org.example.service;

import org.example.entity.area.Area;
import org.example.entity.location.Location;

/**
 * The {@code DeadOrganismRemover} class is responsible for removing dead organisms
 * from an {@code Area} and updating statistics accordingly.
 */
public class DeadOrganismRemover {
    private final StatisticsService statisticsService;

    /**
     * Constructs a new {@code DeadOrganismRemover} with the specified {@code StatisticsService}.
     *
     * @param statisticsService the {@code StatisticsService} used to register dead and alive organisms
     */
    public DeadOrganismRemover(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    /**
     * Removes dead organisms from all locations within the specified {@code Area}.
     * This method processes locations in parallel.
     *
     * @param area the {@code Area} from which dead organisms are to be removed
     */
    public void removeDeadOrganisms(Area area) {
        area.getLocations()
            .parallelStream()
            .forEach(this::removeDeadOrganismsInLocation);
    }

    /**
     * Removes dead organisms from the specified {@code Location} and updates statistics.
     *
     * @param location the {@code Location} from which dead organisms are to be removed
     */
    private void removeDeadOrganismsInLocation(Location location) {
        var deadOrganismsInLocation = location.getDeadOrganisms();
        var aliveOrganismsInLocation = location.getAiveOrganisms();
        statisticsService.registerDeadOrganisms(deadOrganismsInLocation);
        statisticsService.registerAliveOrganisms(aliveOrganismsInLocation);
        location.removeDeadOrganisms();
    }
}
