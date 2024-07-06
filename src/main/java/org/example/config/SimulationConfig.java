package org.example.config;

public record SimulationConfig(double hungerRatio,
                               int iterationMinLatency,
                               int height,
                               int width) {
}
