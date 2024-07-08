package org.example.config;

/**
 * The {@code ViewConfig} class represents the configuration settings for the simulation view.
 * It includes a setting to control the visibility of detailed location information.
 */
public record ViewConfig(boolean isDetailedLocationInfoVisible) {
}
