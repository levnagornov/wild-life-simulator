package org.example.config;

/**
 * The {@code SimulationConfig} class represents the configuration settings for the simulation.
 * It includes parameters related to the hunger ratio, iteration latency, and the dimensions of the simulation area.
 */
public record SimulationConfig(double hungerRatio,
                               int iterationMinLatency,
                               int height,
                               int width) {
}
