package org.example.view;

import org.example.config.ViewConfig;
import org.example.entity.coordinate.CoordinateFactory;
import org.example.entity.area.Area;
import org.example.entity.location.Location;
import org.example.repository.EmojiRepository;
import org.example.service.StatisticsService;

public class ConsoleView implements View {
    private final StatisticsService statisticsService;
    private final EmojiRepository emojiRepository;
    private final CoordinateFactory coordinateFactory;
    private final ViewConfig viewConfig;

    public ConsoleView(StatisticsService statisticsService,
                       EmojiRepository emojiRepository,
                       CoordinateFactory coordinateFactory, ViewConfig viewConfig) {
        this.statisticsService = statisticsService;
        this.emojiRepository = emojiRepository;
        this.coordinateFactory = coordinateFactory;
        this.viewConfig = viewConfig;
    }

    @Override
    public void showWorldMap(Area area) {
        for (int y = 0; y < area.getHeight(); y++) {
            for (int x = 0; x < area.getWidth(); x++) {
                printLocationByCoordinate(area, y, x);
            }
            System.out.println();
        }
    }

    @Override
    public void showIterationNumber() {
        System.out.println("--- Day " + statisticsService.getCurrentIterationCounter() + " ---");
    }

    @Override
    public void showTotalDied() {
        System.out.println("Total died: " + statisticsService.getTotalDiedOrganisms());
    }

    @Override
    public void showDiedToday() {
        System.out.println("Died today " + statisticsService.getDiedToday());
    }

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

    @Override
    public void showTotalAliveOrganisms(Area area) {
        var totalAliveOrganisms = statisticsService.getTotalAliveOrganisms(area);
        System.out.printf("Total alive organisms: %d%n".formatted(totalAliveOrganisms));
    }

    @Override
    public void showAliveToday() {
        var aliveOrganismMap = statisticsService.getAliveOrganismMap();
        System.out.println("Alive:");
        for (var entry : aliveOrganismMap.entrySet()) {
            var organismClass = entry.getKey();
            var aliveOrganisms = entry.getValue();
            var organismEmoji = emojiRepository.getOrganismEmoji(organismClass);
            System.out.println(organismEmoji + ": " + aliveOrganisms);
        }
    }

    private void printLocationByCoordinate(Area area, int y, int x) {
        var coordinate = coordinateFactory.getCoordinate(y, x);
        var location = area.getLocationByCoordinate(coordinate);
        var locationType = location.getLocationType();
        var emoji = emojiRepository.getLocationTypeEmoji(locationType);

        System.out.print(emoji);

        if (x != area.getHeight() - 1) {
            System.out.print(" ");
        }
    }

    private void printLocations(Area area) {
        area.getLocations()
                .forEach(this::printLocation);
    }

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
