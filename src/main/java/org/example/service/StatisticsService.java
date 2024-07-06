package org.example.service;

import org.example.entity.area.Area;
import org.example.entity.organism.Organism;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class StatisticsService {
    private final ConcurrentHashMap<Class<? extends Organism>, Long> aliveOrganismMap;
    private long totalDiedOrganisms;
    private long diedTodayOrganisms;
    private long currentIterationCounter;

    public StatisticsService() {
        this.aliveOrganismMap = new ConcurrentHashMap<>();
        this.currentIterationCounter = 1;
    }

    public long getTotalDiedOrganisms() {
        return totalDiedOrganisms;
    }

    public long getCurrentIterationCounter() {
        return currentIterationCounter;
    }

    public void increaseIterationCounter() {
        this.currentIterationCounter++;
    }

    public long getDiedToday() {
        return diedTodayOrganisms;
    }

    public Map<Class<? extends Organism>, Long> getAliveOrganismMap() {
        return aliveOrganismMap;
    }

    public int getTotalAliveOrganisms(Area area) {
        return area.getLocations()
                .stream()
                .flatMap(location -> location.getOrganisms().stream())
                .filter(Organism::isAlive)
                .mapToInt(organism -> 1).sum();
    }

    public void registerDeadOrganisms(List<Organism> deadOrganisms) {
        this.diedTodayOrganisms = deadOrganisms.size();
        this.totalDiedOrganisms += deadOrganisms.size();
    }

    public void registerAliveOrganisms(List<Organism> aliveOrganisms) {
        aliveOrganisms.parallelStream()
                .forEach(organism -> aliveOrganismMap.merge(organism.getClass(), 1L, Long::sum));
    }
}
