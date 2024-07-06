package org.example;

import org.example.config.DependencyContainer;

import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("Simulation started.");

        var dependencyContainer = new DependencyContainer();

        var simulationTask = dependencyContainer.getSimulationTask();
        var plantRegrowingTask = dependencyContainer.getPlantRegrowingTask();

        try (var executorService = Executors.newSingleThreadExecutor(); var scheduledExecutorService = Executors.newScheduledThreadPool(1)) {
            scheduledExecutorService.scheduleAtFixedRate(plantRegrowingTask, 1, 5, TimeUnit.SECONDS);

            var submit = executorService.submit(simulationTask);

            try {
                submit.get();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        System.out.println("Simulation has been completed.");
    }
}