package org.example.service;

import org.example.entity.area.Area;
import org.example.entity.location.Location;
import org.example.entity.organism.Organism;
import org.example.entity.organism.animal.Animal;
import org.example.logger.TimeExecutionLogger;
import org.example.provider.DietProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * The {@code FeedingService} class manages the feeding process of animals
 * within a specified area. It determines which animals can be preyed upon
 * based on the diet configuration and updates the statistics accordingly.
 */
public class FeedingService {
    private static final Logger logger = LoggerFactory.getLogger(FeedingService.class);

    private final DietProvider dietProvider;
    private final RandomizerService randomizerService;
    private final StatisticsService statisticsService;
    private final TimeExecutionLogger timeExecutionLogger;
    private final Queue<Organism> deadAnimals;

    /**
     * Constructs a new {@code FeedingService} with the specified dependencies.
     *
     * @param dietProvider the provider for diet configurations
     * @param randomizerService the service for randomization tasks
     * @param statisticsService the service for updating statistics
     * @param timeExecutionLogger the logger for timing executions
     */
    public FeedingService(DietProvider dietProvider,
                          RandomizerService randomizerService,
                          StatisticsService statisticsService,
                          TimeExecutionLogger timeExecutionLogger) {
        this.dietProvider = dietProvider;
        this.randomizerService = randomizerService;
        this.statisticsService = statisticsService;
        this.timeExecutionLogger = timeExecutionLogger;
        this.deadAnimals = new ConcurrentLinkedQueue<>();
    }

    /**
     * Initiates the feeding process for all animals in the given area.
     *
     * @param area the area where the feeding process takes place
     */
    public void feed(Area area) {
        timeExecutionLogger.logExecutionTime("Feeding", feedAnimals(area));
        statisticsService.registerDeadOrganisms(deadAnimals.stream().toList());
    }

    /**
     * Returns a {@link Runnable} that initiates the feeding process for all animals
     * in the given area in parallel.
     *
     * @param area the area where the feeding process takes place
     * @return a {@link Runnable} for the feeding process
     */
    private Runnable feedAnimals(Area area) {
        return () -> area.getLocations()
                .parallelStream()
                .forEach(this::feedAnimalsByLocation);
    }

    /**
     * Feeds animals in a specific location.
     *
     * @param location the location where the animals are to be fed
     */
    private void feedAnimalsByLocation(Location location) {
        var groupedOrganismsByClass = location.getGroupedOrganismsByClass();

        location.getAnimals()
                .parallelStream()
                .forEach(animal -> feedAnimal(animal, groupedOrganismsByClass));
    }

    /**
     * Feeds a specific animal based on its diet and the available preys in the location.
     *
     * @param animal the animal to be fed
     * @param groupedOrganisms a map of organisms grouped by their class
     */
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

    /**
     * Returns a list of possible preys for the specified eater based on the diet configuration.
     *
     * @param eater the organism that is looking for food
     * @param groupedOrganisms a map of organisms grouped by their class
     * @return a list of possible preys
     */
    private List<Organism> getPossiblePreys(Organism eater, Map<Class<?>, List<Organism>> groupedOrganisms) {
        var eaterDiet = dietProvider.getDiet(eater.getClass());

        return groupedOrganisms.entrySet()
                .stream()
                .filter(entry -> eaterDiet.containsKey(entry.getKey()))
                .flatMap(entry -> entry.getValue().stream())
                .filter(prey -> prey.isAlive() && !prey.equals(eater))
                .toList();
    }

    /**
     * Selects a random prey from the list of possible preys.
     *
     * @param preys the list of possible preys
     * @return a randomly selected prey
     */
    private Organism getRandomPrey(List<Organism> preys) {
        var randomPreyIndex = randomizerService.getRandomIndex(preys);

        return preys.get(randomPreyIndex);
    }

    /**
     * Determines if the prey is successfully caught based on the eater's diet and a random success check.
     *
     * @param eater the organism attempting to catch the prey
     * @param prey the organism being hunted
     * @return {@code true} if the prey is caught, {@code false} otherwise
     */
    private boolean isPreyCaught(Organism eater, Organism prey) {
        var eaterDiet = dietProvider.getDiet(eater.getClass());
        var preyEatChance = eaterDiet.get(prey.getClass());

        return randomizerService.isSuccessfulAttempt(preyEatChance);
    }
}
