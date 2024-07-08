package org.example.config;

import org.example.task.LifeCycleTask;
import org.example.task.PlantRegrowingTask;
import org.example.entity.area.Island;
import org.example.entity.area.RandomLocationCreationStrategy;
import org.example.entity.characteristic.CharacteristicsFactory;
import org.example.entity.coordinate.CoordinateFactory;
import org.example.entity.location.LocationFactory;
import org.example.entity.organism.OrganismFactory;
import org.example.entity.organism.OrganismRegistry;
import org.example.logger.TimeExecutionLogger;
import org.example.provider.DietProvider;
import org.example.provider.EmojiProvider;
import org.example.service.*;
import org.example.config.reader.YamlConfigReader;
import org.example.view.ConsoleView;
import org.slf4j.LoggerFactory;

public class DependencyContainer {
    private final LifeCycleTask lifeCycleTask;
    private final PlantRegrowingTask plantRegrowingTask;

    public DependencyContainer() {
        var timeExecutionLogger = new TimeExecutionLogger(LoggerFactory.getLogger(TimeExecutionLogger.class));
        var organismRegistry = new OrganismRegistry();
        var configReader = new YamlConfigReader(organismRegistry);
        var simulationConfig = configReader.readSimulationConfig();
        var terminationConfig = configReader.readTerminationConfig();
        var viewConfig = configReader.readViewConfig();

        var characteristicsFactory = new CharacteristicsFactory(configReader);
        var randomizerService = new RandomizerService(characteristicsFactory);
        var coordinateFactory = new CoordinateFactory();
        var locationFactory = new LocationFactory(randomizerService);
        var organismFactory = new OrganismFactory(organismRegistry, characteristicsFactory);

        var randomLocationCreationStrategy = new RandomLocationCreationStrategy(simulationConfig.height(), simulationConfig.width(), locationFactory, coordinateFactory);
        var area = new Island(simulationConfig.height(), simulationConfig.width(), randomLocationCreationStrategy);

        var lockCoordinator = new LockCoordinator();
        var validationService = new ValidationService(characteristicsFactory);
        var coordinateCalculator = new MovementCalculator(coordinateFactory, validationService, randomizerService);

        var emojiRepository = new EmojiProvider(configReader);
        var dietRepository = new DietProvider(configReader);

        var delayService = new DelayService(simulationConfig.iterationMinLatency());
        var partnerFindingService = new PartnerFindingService();
        var reproductionService = new ReproductionService(randomizerService, organismFactory, timeExecutionLogger, partnerFindingService);
        var statisticsService = new StatisticsService();
        var cleanupService = new DeadOrganismRemover(statisticsService);
        var feedingService = new FeedingService(dietRepository, randomizerService, statisticsService, timeExecutionLogger);
        var movementService = new MovementService(coordinateCalculator, lockCoordinator, timeExecutionLogger);
        var populationService = new PopulationService(organismRegistry, organismFactory, randomizerService);
        var terminationService = new TerminationService(terminationConfig, statisticsService);
        var hungerService = new HungerService(simulationConfig.hungerRatio());
        var simulationService = new SimulationService(movementService, feedingService, reproductionService, hungerService);

        var consoleView = new ConsoleView(statisticsService, emojiRepository, coordinateFactory, viewConfig);

        lifeCycleTask = new LifeCycleTask(consoleView, area, simulationService, populationService, statisticsService, terminationService, cleanupService, delayService);
        plantRegrowingTask = new PlantRegrowingTask(area, populationService);
    }

    public LifeCycleTask getSimulationTask() {
        return lifeCycleTask;
    }

    public PlantRegrowingTask getPlantRegrowingTask() {
        return plantRegrowingTask;
    }
}
