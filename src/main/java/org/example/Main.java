package org.example;

import org.example.config.DependencyContainer;

import java.util.concurrent.*;

/**
 * Main class to start and manage the simulation of a virtual ecosystem.
 * It initializes necessary dependencies using a DependencyContainer, executes
 * the simulation and plant regrowing tasks concurrently, and ensures proper
 * termination and cleanup after completion or in case of exceptions.
 */
public class Main {

    /**
     * Main method to start the simulation.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        System.out.println("Simulation started.");

        var dependencyContainer = new DependencyContainer();
        var simulationTask = dependencyContainer.getSimulationTask();
        var plantRegrowingTask = dependencyContainer.getPlantRegrowingTask();

        try (var executorService = Executors.newSingleThreadExecutor();
             var scheduledExecutorService = Executors.newScheduledThreadPool(1))
        {
            scheduledExecutorService.scheduleAtFixedRate(plantRegrowingTask, 1, 5, TimeUnit.SECONDS);
            var submit = executorService.submit(simulationTask);
            submit.get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        System.out.println("Simulation has been completed.");
    }
}