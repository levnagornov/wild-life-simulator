package org.example.config;

/**
 * The {@code TerminationConfig} class represents the configuration settings for terminating the simulation.
 * It includes various conditions that can trigger the end of the simulation.
 */
public record TerminationConfig(boolean iterationLimit,
                                int iterationCount,
                                boolean allAnimalsDead,
                                boolean allPredatorsDead,
                                boolean allHerbivoreDead) {
}
