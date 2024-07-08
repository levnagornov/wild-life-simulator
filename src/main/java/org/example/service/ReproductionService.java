package org.example.service;

import org.example.entity.area.Area;
import org.example.entity.location.Location;
import org.example.entity.organism.Organism;
import org.example.entity.organism.OrganismFactory;
import org.example.entity.organism.animal.Animal;
import org.example.logger.TimeExecutionLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.IntStream;

/**
 * Service responsible for managing animal reproduction within an area.
 */
public class ReproductionService {
    private static final Logger logger = LoggerFactory.getLogger(ReproductionService.class);

    private final RandomizerService randomizerService;
    private final OrganismFactory organismFactory;
    private final TimeExecutionLogger timeExecutionLogger;
    private final PartnerFindingService partnerFindingService;

    /**
     * Constructs a ReproductionService instance.
     *
     * @param randomizerService     Service providing randomization functionalities.
     * @param organismFactory       Factory for creating new organisms.
     * @param timeExecutionLogger   Logger for recording execution times.
     * @param partnerFindingService Service for finding potential reproduction partners.
     */
    public ReproductionService(RandomizerService randomizerService, OrganismFactory organismFactory, TimeExecutionLogger timeExecutionLogger, PartnerFindingService partnerFindingService) {
        this.randomizerService = randomizerService;
        this.organismFactory = organismFactory;
        this.timeExecutionLogger = timeExecutionLogger;
        this.partnerFindingService = partnerFindingService;
    }

    /**
     * Initiates the reproduction process across all locations in the given area.
     *
     * @param area The area within which reproduction should occur.
     */
    public void reproduce(Area area) {
        timeExecutionLogger.logExecutionTime("Reproduction", reproduceAnimals(area));
    }

    /**
     * Creates a runnable task to reproduce animals in each location of the given area.
     *
     * @param area The area containing locations with animals to reproduce.
     * @return A {@link Runnable} task that reproduces animals in each location.
     */
    private Runnable reproduceAnimals(Area area) {
        return () -> area.getLocations()
                         .forEach(this::reproduceAnimalsInLocation);
    }

    /**
     * Reproduces animals within a specific location.
     *
     * @param location The location where animals should reproduce.
     */
    private void reproduceAnimalsInLocation(Location location) {
        location.getGroupedAnimalsByClass().entrySet().parallelStream().forEach(entry -> reproduceAnimalType(entry.getValue(), location));
    }

    /**
     * Reproduces animals of the same type within a location.
     *
     * @param sameTypeAnimals List of animals of the same type.
     * @param location        The location where animals should reproduce.
     */
    private void reproduceAnimalType(List<Animal> sameTypeAnimals, Location location) {
        sameTypeAnimals.forEach(animal -> reproduceAnimal(animal, sameTypeAnimals, location));
    }

    /**
     * Manages reproduction for a single animal.
     *
     * @param animal   The animal to reproduce.
     * @param animals  List of all animals of the same type in the location.
     * @param location The location where reproduction occurs.
     */
    private void reproduceAnimal(Animal animal, List<Animal> animals, Location location) {
        logger.debug("Reproducing animal {} at location {}", animal.getClass().getSimpleName() + animal.getId(), location);

        if (!animal.isAlive() || !animal.isReadyToMate()) {
            logger.debug("Animal {} is not valid for reproduction isAlive={} isReadyToMate={}", animal.getClass().getSimpleName() + animal.getId(), animal.isAlive(), animal.isReadyToMate());
            return;
        }

        var partners = partnerFindingService.getPossiblePartners(animal, animals);
        if (partners.isEmpty()) {
            logger.debug("No possible partners were found.");
            return;
        }

        var partner = getRandomPartner(partners);
        logger.debug("The reproduction pair has been found. Animal {} has {} as a partner", animal.getClass().getSimpleName() + animal.getId(), partner.getClass().getSimpleName() + partner.getId());

        animal.reproduce();
        partner.reproduce();

        var offspring = generateOffspring(animal);
        logger.debug("Offspring list: {}", offspring);

        location.addOrganisms(offspring);
        logger.debug("Added offspring list to location at {}", animal.getCoordinate());
    }

    /**
     * Generates offspring for a given animal based on its species characteristics.
     *
     * @param animal The animal for which offspring should be generated.
     * @return List of generated offspring organisms.
     */
    private List<Organism> generateOffspring(Animal animal) {
        var randomOffspringAmount = randomizerService.getRandomOffspringAmount(animal.getClass());

        return IntStream.range(0, randomOffspringAmount).mapToObj(i -> organismFactory.createOrganism(animal.getClass(), animal.getCoordinate())).toList();
    }

    /**
     * Selects a random partner from the list of potential partners.
     *
     * @param partners List of potential partners for reproduction.
     * @return A randomly selected partner animal.
     */
    private Animal getRandomPartner(List<Animal> partners) {
        var randomPreyIndex = randomizerService.getRandomIndex(partners);

        return partners.get(randomPreyIndex);
    }
}
