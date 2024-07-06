    package org.example.config;

    public record TerminationConfig(boolean iterationLimit,
                                    int iterationCount,
                                    boolean allAnimalsDead,
                                    boolean allPredatorsDead,
                                    boolean allHerbivoreDead) {
    }
