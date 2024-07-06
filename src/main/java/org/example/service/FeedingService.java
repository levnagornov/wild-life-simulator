package org.example.service;

import org.example.entity.area.Area;
import org.example.entity.location.Location;
import org.example.entity.organism.Organism;
import org.example.entity.organism.animal.Animal;
import org.example.logger.TimeExecutionLogger;
import org.example.repository.DietRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class FeedingService {
    private static final Logger logger = LoggerFactory.getLogger(FeedingService.class);

    private final DietRepository dietRepository;
    private final RandomizerService randomizerService;
    private final StatisticsService statisticsService;
    private final TimeExecutionLogger timeExecutionLogger;
    private final Queue<Organism> deadAnimals;

    public FeedingService(DietRepository dietRepository,
                          RandomizerService randomizerService,
                          StatisticsService statisticsService,
                          TimeExecutionLogger timeExecutionLogger) {
        this.dietRepository = dietRepository;
        this.randomizerService = randomizerService;
        this.statisticsService = statisticsService;
        this.timeExecutionLogger = timeExecutionLogger;
        this.deadAnimals = new ConcurrentLinkedQueue<>();
    }

    public void feed(Area area) {
        timeExecutionLogger.logExecutionTime("Feeding", feedAnimals(area));
        statisticsService.registerDeadOrganisms(deadAnimals.stream().toList());
    }

    private Runnable feedAnimals(Area area) {
        return () -> area.getLocations()
                .parallelStream()
                .forEach(this::feedAnimalsByLocation);
    }

    private void feedAnimalsByLocation(Location location) {
        var groupedOrganismsByClass = location.getGroupedOrganismsByClass();

        location.getAnimals()
                .parallelStream()
                .forEach(animal -> feedAnimal(animal, groupedOrganismsByClass));
    }

    private void feedAnimal(Animal animal, Map<Class<?>, List<Organism>> groupedOrganisms) {
        var animalClassNameWithId = animal.getClass().getSimpleName() + animal.getId();

        logger.debug("Feeding animal {} at {}", animalClassNameWithId, animal.getCoordinate());

        if (!animal.isAlive()) {
            logger.debug("Animal {} is already dead", animalClassNameWithId);
            return;
        }

        var preys = getPossiblePreys(animal, groupedOrganisms);
        if (preys.isEmpty()) {
            logger.debug("No preys found for {}", animalClassNameWithId);
            return;
        }

        logger.debug("Prey list: {}", preys);

        var prey = getRandomPrey(preys);
        var preyClassNameWithId = prey.getClass().getSimpleName() + prey.getId();
        if (!isPreyCaught(animal, prey)) {
            logger.debug("Animal {} didn't catch {}", animalClassNameWithId, preyClassNameWithId);
            return;
        }

        logger.debug("Animal {} caught {}", animalClassNameWithId, preyClassNameWithId);

        animal.eat(prey);
        deadAnimals.add(prey);
    }

    private List<Organism> getPossiblePreys(Organism eater, Map<Class<?>, List<Organism>> groupedOrganisms) {
        var eaterDiet = dietRepository.getDiet(eater.getClass());

        return groupedOrganisms.entrySet()
                .stream()
                .filter(entry -> eaterDiet.containsKey(entry.getKey()))
                .flatMap(entry -> entry.getValue().stream())
                .filter(prey -> prey.isAlive() && !prey.equals(eater))
                .toList();
    }

    private Organism getRandomPrey(List<Organism> preys) {
        var randomPreyIndex = randomizerService.getRandomIndex(preys);

        return preys.get(randomPreyIndex);
    }

    private boolean isPreyCaught(Organism eater, Organism prey) {
        var eaterDiet = dietRepository.getDiet(eater.getClass());
        var preyEatChance = eaterDiet.get(prey.getClass());

        return randomizerService.isSuccessfulAttempt(preyEatChance);
    }
}
