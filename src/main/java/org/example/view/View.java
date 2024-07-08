package org.example.view;

import org.example.entity.area.Area;

/**
 * View interface defines methods to display simulation information.
 */
public interface View {

    /**
     * Displays the world map of the simulation area.
     *
     * @param area The simulation area to display.
     */
    void showWorldMap(Area area);

    /**
     * Displays detailed statistics of the simulation area.
     *
     * @param area The simulation area to display statistics for.
     */
    void showStatistics(Area area);

    /**
     * Displays the current iteration number of the simulation.
     */
    void showIterationNumber();

    /**
     * Displays the number of organisms that died today.
     */
    void showDiedToday();

    /**
     * Displays the number of organisms that are alive today, categorized by type.
     */
    void showAliveToday();

    /**
     * Displays the total number of organisms that have died in the simulation.
     */
    void showTotalDied();

    /**
     * Displays the total number of organisms that are currently alive in the simulation area.
     *
     * @param area The simulation area to calculate total alive organisms.
     */
    void showTotalAliveOrganisms(Area area);
}
