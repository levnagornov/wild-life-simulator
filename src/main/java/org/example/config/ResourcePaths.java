package org.example.config;

import java.io.File;

/**
 * The {@code ResourcePaths} class contains static constants representing paths to various configuration files
 * used in the simulation. These files are stored in the `src/main/resources` directory.
 */
public class ResourcePaths {
    public static final File DIETS_FILE = new File("./src/main/resources/diets.yml");
    public static final File EMOJIS_FILE = new File("./src/main/resources/emojis.yml");
    public static final File CHARACTERISTICS_FILE = new File("./src/main/resources/characteristics.yml");
    public static final File SIMULATION_CONFIG_FILE = new File("./src/main/resources/simulation.yml");
    public static final File TERMINATION_CONFIG_FILE = new File("./src/main/resources/termination.yml");
    public static final File VIEW_CONFIG_FILE = new File("./src/main/resources/view.yml");
}
