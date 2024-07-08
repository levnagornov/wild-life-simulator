package org.example.view;

import org.example.config.ViewConfig;
import org.example.entity.coordinate.CoordinateFactory;
import org.example.entity.area.Area;
import org.example.entity.location.Location;
import org.example.provider.EmojiProvider;
import org.example.service.StatisticsService;

/**
 * ConsoleView implements the View interface to provide textual representation
 * and visualization of a simulation area and its statistics on the console.
 * It uses emojis and textual information to display the state of the simulation
 * including the world map, iteration numbers, organism statistics, and more.
 */
public class ConsoleView implements View {
    private final StatisticsService statisticsService;
    private final EmojiProvider emojiProvider;
    private final CoordinateFactory coordinateFactory;
    private final ViewConfig viewConfig;

    /**
     * Constructs a ConsoleView object with the specified dependencies.
     *
     * @param statisticsService  Service for retrieving simulation statistics.
     * @param emojiProvider     Provider for obtaining emojis representing organisms.
     * @param coordinateFactory Factory for creating coordinates within the simulation area.
     * @param viewConfig        Configuration for controlling the display options.
     */
    public ConsoleView(StatisticsService statisticsService,
                       EmojiProvider emojiProvider,
                       CoordinateFactory coordinateFactory, ViewConfig viewConfig) {
        this.statisticsService = statisticsService;
        this.emojiProvider = emojiProvider;
        this.coordinateFactory = coordinateFactory;
        this.viewConfig = viewConfig;
    }

    /**
     * Displays the world map of the simulation area using emojis or characters
     * representing different types of locations and organisms.
     *
     * @param area The simulation area to be displayed.
     */
    @Override
    public void showWorldMap(Area area) {
        for (int y = 0; y < area.getHeight(); y++) {
            for (int x = 0; x < area.getWidth(); x++) {
                printLocationByCoordinate(area, y, x);
            }
            System.out.println();
        }
    }

    /**
     * Prints the current iteration number of the simulation.
     */
    @Override
    public void showIterationNumber() {
        System.out.println("--- Day " + statisticsService.getCurrentIterationCounter() + " ---");
    }


    /**
     * Prints the total number of organisms that have died since the start of the simulation.
     */
    @Override
    public void showTotalDied() {
        System.out.println("Total died: " + statisticsService.getTotalDiedOrganisms());
    }

    /**
     * Prints the number of organisms that have died during the current iteration.
     */
    @Override
    public void showDiedToday() {
        System.out.println("Died today " + statisticsService.getDiedToday());
    }

    /**
     * Displays detailed statistics about the simulation area, including
     * total alive organisms, organisms that died today, and optionally detailed
     * location information based on the configuration.
     *
     * @param area The simulation area to retrieve statistics from.
     */
    @Override
    public void showStatistics(Area area) {
        if (viewConfig.isDetailedLocationInfoVisible()) {
            printLocations(area);
        }
        showDiedToday();
        showTotalAliveOrganisms(area);
        showTotalDied();
        System.out.println();
    }

    /**
     * Prints the total number of alive organisms currently present in the simulation area.
     *
     * @param area The simulation area to retrieve statistics from.
     */
    @Override
    public void showTotalAliveOrganisms(Area area) {
        var totalAliveOrganisms = statisticsService.getTotalAliveOrganisms(area);
        System.out.printf("Total alive organisms: %d%n".formatted(totalAliveOrganisms));
    }

    /**
     * Prints the number of organisms alive today categorized by their class
     * along with corresponding emojis representing each class.
     */
    @Override
    public void showAliveToday() {
        var aliveOrganismMap = statisticsService.getAliveOrganismMap();
        System.out.println("Alive:");
        for (var entry : aliveOrganismMap.entrySet()) {
            var organismClass = entry.getKey();
            var aliveOrganisms = entry.getValue();
            var organismEmoji = emojiProvider.getOrganismEmoji(organismClass);
            System.out.println(organismEmoji + ": " + aliveOrganisms);
        }
    }

    /**
     * Prints the location within the simulation area specified by coordinates,
     * including emojis representing the location type and organisms present.
     *
     * @param area The simulation area containing the location.
     * @param y    The y-coordinate of the location.
     * @param x    The x-coordinate of the location.
     */
    private void printLocationByCoordinate(Area area, int y, int x) {
        var coordinate = coordinateFactory.getCoordinate(y, x);
        var location = area.getLocationByCoordinate(coordinate);
        var locationType = location.getLocationType();
        var emoji = emojiProvider.getLocationTypeEmoji(locationType);

        System.out.print(emoji);

        if (x != area.getHeight() - 1) {
            System.out.print(" ");
        }
    }

    /**
     * Iterates over all locations in the simulation area and prints detailed information
     * about each location, including the types and number of organisms present.
     *
     * @param area The simulation area containing the locations.
     */
    private void printLocations(Area area) {
        area.getLocations()
                .forEach(this::printLocation);
    }

    /**
     * Prints detailed information about a specific location within the simulation area,
     * including the types and number of organisms present at that location.
     *
     * @param location The location within the simulation area to be printed.
     */
    private void printLocation(Location location) {
        var coordinate = location.getCoordinate();
        System.out.printf("Location at coordinate at [%d,%d] contains:%n", coordinate.y(), coordinate.x());
        var aliveOrganismsNumberInCoordinate = 0;

        if (location.getSpeciesCounterMap().isEmpty()) {
            System.out.println("    No alive organisms");
            return;
        }

        for (var specieAndAmount : location.getSpeciesCounterMap().entrySet()) {
            var specieClass = specieAndAmount.getKey();
            var specieAmount = specieAndAmount.getValue();
            var message = specieAmount == 1 ? "    %s %d specie %n" : "    %s %d species %n";
            System.out.printf(message, specieClass.getSimpleName(), specieAmount);

            aliveOrganismsNumberInCoordinate += specieAmount;
        }

        System.out.printf("Alive organisms here: %d%n".formatted(aliveOrganismsNumberInCoordinate));
    }
}
