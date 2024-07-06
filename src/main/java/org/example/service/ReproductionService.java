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

public class ReproductionService {
    private static final Logger logger = LoggerFactory.getLogger(ReproductionService.class);

    private final RandomizerService randomizerService;
    private final OrganismFactory organismFactory;
    private final TimeExecutionLogger timeExecutionLogger;
    private final PartnerFindingService partnerFindingService;

    public ReproductionService(RandomizerService randomizerService, OrganismFactory organismFactory, TimeExecutionLogger timeExecutionLogger, PartnerFindingService partnerFindingService) {
        this.randomizerService = randomizerService;
        this.organismFactory = organismFactory;
        this.timeExecutionLogger = timeExecutionLogger;
        this.partnerFindingService = partnerFindingService;
    }

    public void reproduce(Area area) {
        timeExecutionLogger.logExecutionTime("Reproduction", reproduceAnimals(area));
    }

    private Runnable reproduceAnimals(Area area) {
        return () -> area.getLocations()
                         .forEach(this::reproduceAnimalsInLocation);
    }

    private void reproduceAnimalsInLocation(Location location) {
        location.getGroupedAnimalsByClass().entrySet().parallelStream().forEach(entry -> reproduceAnimalType(entry.getValue(), location));
    }

    private void reproduceAnimalType(List<Animal> sameTypeAnimals, Location location) {
        sameTypeAnimals.forEach(animal -> reproduceAnimal(animal, sameTypeAnimals, location));
    }

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

    private List<Organism> generateOffspring(Animal animal) {
        var randomOffspringAmount = randomizerService.getRandomOffspringAmount(animal.getClass());

        return IntStream.range(0, randomOffspringAmount).mapToObj(i -> organismFactory.createOrganism(animal.getClass(), animal.getCoordinate())).toList();
    }

    private Animal getRandomPartner(List<Animal> partners) {
        var randomPreyIndex = randomizerService.getRandomIndex(partners);

        return partners.get(randomPreyIndex);
    }
}
