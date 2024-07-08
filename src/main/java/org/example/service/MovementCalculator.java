package org.example.service;

import org.example.entity.coordinate.Coordinate;
import org.example.entity.coordinate.CoordinateFactory;
import org.example.entity.area.Area;
import org.example.entity.organism.animal.Animal;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@code MovementCalculator} class calculates the next coordinate for animal movement within an area.
 */
public class MovementCalculator {
    private final CoordinateFactory coordinateFactory;
    private final ValidationService validationService;
    private final RandomizerService randomizerService;
    private Area area;

    /**
     * Constructs a {@code MovementCalculator} with the specified dependencies.
     *
     * @param coordinateFactory the factory for creating coordinates
     * @param validationService the service for validating coordinates and locations
     * @param randomizerService the service for generating random numbers
     */
    public MovementCalculator(CoordinateFactory coordinateFactory,
                              ValidationService validationService,
                              RandomizerService randomizerService) {
        this.coordinateFactory = coordinateFactory;
        this.validationService = validationService;
        this.randomizerService = randomizerService;
    }

    /**
     * Calculates the next coordinate for the given animal within the specified area.
     *
     * @param area   the area where the animal moves
     * @param animal the animal whose next coordinate is calculated
     * @return the next coordinate for the animal to move to
     */
    public Coordinate calculateNextCoordinate(Area area, Animal animal) {
        this.area = area;

        var numberOfMoves = animal.getCharacteristics().moveSpeed();
        var currentCoordinate = animal.getCoordinate();

        for (int i = 0; i < numberOfMoves; i++) {
            var possibleCoordinates = calculatePossibleMoves(currentCoordinate, animal);
            if (possibleCoordinates.isEmpty()) {
                break;
            }
            var randomElementIndex = randomizerService.getRandomIndex(possibleCoordinates);
            currentCoordinate = possibleCoordinates.get(randomElementIndex);
        }

        return currentCoordinate;
    }

    /**
     * Calculates the list of possible coordinates for the animal to move from the current coordinate.
     *
     * @param coordinate the current coordinate of the animal
     * @param animal     the animal whose movement is considered
     * @return the list of possible coordinates for the animal to move to
     */
    private List<Coordinate> calculatePossibleMoves(Coordinate coordinate, Animal animal) {
        var availableCoordinates = new ArrayList<Coordinate>();
        var possibleDirections = new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}, {-1, -1}, {-1, 1}, {1, -1}, {1, 1}};

        for (var direction : possibleDirections) {
            var newY = coordinate.y() + direction[0];
            var newX = coordinate.x() + direction[1];
            var newCoordinate = coordinateFactory.getCoordinate(newY, newX);
            var newLocation = area.getLocationByCoordinate(newCoordinate);
            var isValidMove = validationService.isValidCoordinate(newCoordinate, area) && validationService.isValidLocationForAnimal(newLocation, animal);

            if (isValidMove) {
                availableCoordinates.add(newCoordinate);
            }
        }

        return availableCoordinates;
    }
}
