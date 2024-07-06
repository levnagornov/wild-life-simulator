package org.example.service;

import org.example.entity.area.Area;
import org.example.entity.location.Location;

public class DeadOrganismRemover {
    private final StatisticsService statisticsService;

    public DeadOrganismRemover(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    public void removeDeadOrganisms(Area area) {
        area.getLocations()
            .parallelStream()
            .forEach(this::removeDeadOrganismsInLocation);
    }

    private void removeDeadOrganismsInLocation(Location location) {
        var deadOrganismsInLocation = location.getDeadOrganisms();
        var aliveOrganismsInLocation = location.getAiveOrganisms();
        statisticsService.registerDeadOrganisms(deadOrganismsInLocation);
        statisticsService.registerAliveOrganisms(aliveOrganismsInLocation);
        location.removeDeadOrganisms();
    }
}
