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

/**
 * The {@code DependencyContainer} class is responsible for creating and injecting all the dependencies
 * required for the simulation. It initializes various services, factories, and tasks needed for the simulation.
 */
public class DependencyContainer {
    private final LifeCycleTask lifeCycleTask;
    private final PlantRegrowingTask plantRegrowingTask;

    /**
     * Constructs a new {@code DependencyContainer} and initializes all the dependencies required for the simulation.
     */
    public DependencyContainer() {
        // Logger for tracking time execution
        var timeExecutionLogger = new TimeExecutionLogger(LoggerFactory.getLogger(TimeExecutionLogger.class));

        // Registry for organism classes
        var organismRegistry = new OrganismRegistry();

        // Config reader for reading YAML configuration files
        var configReader = new YamlConfigReader(organismRegistry);

        // Reading simulation, termination, and view configurations
        var simulationConfig = configReader.readSimulationConfig();
        var terminationConfig = configReader.readTerminationConfig();
        var viewConfig = configReader.readViewConfig();

        // Initializing factories and services
        var characteristicsFactory = new CharacteristicsFactory(configReader);
        var randomizerService = new RandomizerService(characteristicsFactory);
        var coordinateFactory = new CoordinateFactory();
        var locationFactory = new LocationFactory(randomizerService);
        var organismFactory = new OrganismFactory(organismRegistry, characteristicsFactory);

        // Strategy for creating random locations
        var randomLocationCreationStrategy = new RandomLocationCreationStrategy(
                simulationConfig.height(),
                simulationConfig.width(),
                locationFactory,
                coordinateFactory
        );

        // Island area representing the simulation environment
        var area = new Island(
                simulationConfig.height(),
                simulationConfig.width(),
                randomLocationCreationStrategy
        );

        // Various services required for the simulation
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

        // Console view for displaying simulation statistics
        var consoleView = new ConsoleView(statisticsService, emojiRepository, coordinateFactory, viewConfig);

        // Initializing the tasks for the simulation
        lifeCycleTask = new LifeCycleTask(
                consoleView,
                area,
                simulationService,
                populationService,
                statisticsService,
                terminationService,
                cleanupService,
                delayService
        );
        plantRegrowingTask = new PlantRegrowingTask(area, populationService);
    }

    /**
     * Returns the {@code LifeCycleTask} responsible for running the simulation.
     *
     * @return the {@code LifeCycleTask} instance
     */
    public LifeCycleTask getSimulationTask() {
        return lifeCycleTask;
    }

    /**
     * Returns the {@code PlantRegrowingTask} responsible for regrowing plants in the simulation.
     *
     * @return the {@code PlantRegrowingTask} instance
     */
    public PlantRegrowingTask getPlantRegrowingTask() {
        return plantRegrowingTask;
    }
}
