package org.example.service;

import org.example.entity.area.Area;
import org.example.entity.organism.Organism;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Service class for collecting and providing statistics on organisms in an area.
 */
public class StatisticsService {
    private final ConcurrentHashMap<Class<? extends Organism>, Long> aliveOrganismMap;
    private long totalDiedOrganisms;
    private long diedTodayOrganisms;
    private long currentIterationCounter;

    /**
     * Constructs a StatisticsService with initial counters.
     */
    public StatisticsService() {
        this.aliveOrganismMap = new ConcurrentHashMap<>();
        this.currentIterationCounter = 1;
    }

    /**
     * Retrieves the total number of organisms that have died across all iterations.
     *
     * @return Total number of died organisms.
     */
    public long getTotalDiedOrganisms() {
        return totalDiedOrganisms;
    }

    /**
     * Retrieves the current iteration counter.
     *
     * @return Current iteration counter.
     */
    public long getCurrentIterationCounter() {
        return currentIterationCounter;
    }

    /**
     * Increases the current iteration counter by one.
     */
    public void increaseIterationCounter() {
        this.currentIterationCounter++;
    }

    /**
     * Retrieves the number of organisms that died today in the current iteration.
     *
     * @return Number of organisms died today.
     */
    public long getDiedToday() {
        return diedTodayOrganisms;
    }

    /**
     * Retrieves the map of alive organisms categorized by their class.
     *
     * @return Map of alive organisms.
     */
    public Map<Class<? extends Organism>, Long> getAliveOrganismMap() {
        return aliveOrganismMap;
    }

    /**
     * Calculates the total number of alive organisms in the specified area.
     *
     * @param area The area containing organisms.
     * @return Total number of alive organisms in the area.
     */
    public int getTotalAliveOrganisms(Area area) {
        return area.getLocations()
                .stream()
                .flatMap(location -> location.getOrganisms().stream())
                .filter(Organism::isAlive)
                .mapToInt(organism -> 1).sum();
    }

    /**
     * Registers the list of organisms that have died today.
     *
     * @param deadOrganisms List of organisms that have died.
     */
    public void registerDeadOrganisms(List<Organism> deadOrganisms) {
        this.diedTodayOrganisms = deadOrganisms.size();
        this.totalDiedOrganisms += deadOrganisms.size();
    }

    /**
     * Registers the list of organisms that are currently alive.
     *
     * @param aliveOrganisms List of organisms that are alive.
     */
    public void registerAliveOrganisms(List<Organism> aliveOrganisms) {
        aliveOrganisms.parallelStream()
                .forEach(organism -> aliveOrganismMap.merge(organism.getClass(), 1L, Long::sum));
    }
}
