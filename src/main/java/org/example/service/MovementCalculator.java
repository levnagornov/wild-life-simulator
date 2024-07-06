package org.example.service;

import org.example.entity.coordinate.Coordinate;
import org.example.entity.coordinate.CoordinateFactory;
import org.example.entity.area.Area;
import org.example.entity.organism.animal.Animal;

import java.util.ArrayList;
import java.util.List;

public class MovementCalculator {
    private final CoordinateFactory coordinateFactory;
    private final ValidationService validationService;
    private final RandomizerService randomizerService;
    private Area area;

    public MovementCalculator(CoordinateFactory coordinateFactory,
                              ValidationService validationService,
                              RandomizerService randomizerService) {
        this.coordinateFactory = coordinateFactory;
        this.validationService = validationService;
        this.randomizerService = randomizerService;
    }

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
