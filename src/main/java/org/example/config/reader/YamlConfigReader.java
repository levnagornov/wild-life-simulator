package org.example.config.reader;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import org.example.config.ResourcePaths;
import org.example.config.SimulationConfig;
import org.example.config.TerminationConfig;
import org.example.config.ViewConfig;
import org.example.entity.characteristic.Characteristics;
import org.example.entity.location.LocationType;
import org.example.entity.organism.Organism;
import org.example.entity.organism.OrganismRegistry;
import org.example.exception.InvalidConfigFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * The {@code YamlConfigReader} class provides implementation for reading configuration settings from YAML files.
 * It uses Jackson's YAMLMapper for parsing YAML files.
 */
public class YamlConfigReader implements ConfigReader {
    private final File dietsFile;
    private final File emojisFiles;
    private final File characteristicsFile;
    private final File simulationConfigFile;
    private final File terminationConfigFile;
    private final File viewConfigFile;
    private final OrganismRegistry organismRegistry;
    private final ObjectMapper mapper;

    /**
     * Constructs a new {@code YamlConfigReader} with the specified {@code OrganismRegistry}.
     *
     * @param organismRegistry the organism registry
     */
    public YamlConfigReader(OrganismRegistry organismRegistry) {
        this.dietsFile = ResourcePaths.DIETS_FILE;
        this.emojisFiles = ResourcePaths.EMOJIS_FILE;
        this.characteristicsFile = ResourcePaths.CHARACTERISTICS_FILE;
        this.simulationConfigFile = ResourcePaths.SIMULATION_CONFIG_FILE;
        this.terminationConfigFile = ResourcePaths.TERMINATION_CONFIG_FILE;
        this.viewConfigFile = ResourcePaths.VIEW_CONFIG_FILE;
        this.organismRegistry = organismRegistry;
        this.mapper = new YAMLMapper().enable(JsonParser.Feature.STRICT_DUPLICATE_DETECTION);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<Class<? extends Organism>, Map<Class<? extends Organism>, Double>> readDiets() {
        checkFileExtension(dietsFile);

        try {
            return convertRawDietMap(getRawDietMap());
        } catch (IOException e) {
            throw new InvalidConfigFile("Can't read diet config file. " + e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<Class<? extends Organism>, Characteristics> readCharacteristics() {
        checkFileExtension(characteristicsFile);

        try {
            return convertRawCharacteristicsMap(getRawCharacteristicsMap());
        } catch (IOException e) {
            throw new InvalidConfigFile("Can't read characteristics config file. " + e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<Class<? extends Organism>, String> readOrganismEmojis() {
        checkFileExtension(emojisFiles);

        try {
            return convertRawOrganismEmojiMap(getRawEmojiMap());
        } catch (IOException e) {
            throw new InvalidConfigFile("Can't read emoji file. " + e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<LocationType, String> readLocationTypeEmojis() {
        checkFileExtension(emojisFiles);

        try {
            return converRawLocationTypeEmojiMap(getRawEmojiMap());
        } catch (IOException e) {
            throw new InvalidConfigFile("Can't read emoji file. " + e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SimulationConfig readSimulationConfig() {
        checkFileExtension(simulationConfigFile);

        try {
            return mapper.readValue(simulationConfigFile, SimulationConfig.class);
        } catch (IOException e) {
            throw new InvalidConfigFile("Can't read simulation config file. " + e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TerminationConfig readTerminationConfig() {
        checkFileExtension(terminationConfigFile);

        try {
            return mapper.readValue(terminationConfigFile, TerminationConfig.class);
        } catch (IOException e) {
            throw new InvalidConfigFile("Can't read termination config file. " + e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ViewConfig readViewConfig() {
        checkFileExtension(viewConfigFile);

        try {
            return mapper.readValue(viewConfigFile, ViewConfig.class);
        } catch (IOException e) {
            throw new InvalidConfigFile("Can't read view config file. " + e);
        }
    }

    /**
     * Checks if the given file has a valid YAML extension.
     *
     * @param file the file to check
     * @throws InvalidConfigFile if the file does not have a valid YAML extension
     */
    private void checkFileExtension(File file) {
        var fileName = file.getName().toLowerCase();
        if (!fileName.endsWith(".yml") && !fileName.endsWith(".yaml")) {
            throw new InvalidConfigFile("%s is not a YAML file.".formatted(fileName));
        }
    }

    /**
     * Validates the eating chance value.
     *
     * @param eatChance the eating chance value to check
     * @throws InvalidConfigFile if the eating chance value is invalid
     */
    private void checkEatChance(Double eatChance) {
        if (eatChance < 0) {
            throw new InvalidConfigFile("Diet config file is invalid. Eat chance can't be negative.");
        }

        if (eatChance == 0) {
            throw new InvalidConfigFile("Diet config file is invalid. Eat chance can't be zero.");
        }

        if (eatChance > 1) {
            throw new InvalidConfigFile("Diet config file is invalid. Eat chance can't be greater than 1.");
        }
    }

    /**
     * Validates the characteristics of an organism.
     *
     * @param characteristics the characteristics to check
     * @throws InvalidConfigFile if any characteristic value is invalid
     */
    private void checkCharacteristics(Characteristics characteristics) {
        if (characteristics.weight() < 0
                || characteristics.foodForSatiety() < 0
                || characteristics.moveSpeed() < 0
                || characteristics.maxSpeciesPerCoordinate() < 0
        ) {
            throw new InvalidConfigFile("Characteristics config file is invalid. Characteristics must be positive or 0.");
        }
    }

    /**
     * Reads the raw diet map from the YAML file.
     *
     * @return the raw diet map
     * @throws IOException if an I/O error occurs
     */
    private Map<String, Map<String, Double>> getRawDietMap() throws IOException {
        return mapper.readValue(dietsFile, new TypeReference<>() {
        });
    }

    /**
     * Reads the raw characteristics map from the YAML file.
     *
     * @return the raw characteristics map
     * @throws IOException if an I/O error occurs
     */
    private Map<String, Characteristics> getRawCharacteristicsMap() throws IOException {
        var mapType = mapper.getTypeFactory()
                .constructMapType(Map.class, String.class, Characteristics.class);

        return mapper.readValue(characteristicsFile, mapType);
    }

    /**
     * Reads the raw emoji map from the YAML file.
     *
     * @return the raw emoji map
     * @throws IOException if an I/O error occurs
     */
    private Map<String, Map<String, String>> getRawEmojiMap() throws IOException {
        return mapper.readValue(emojisFiles, new TypeReference<>() {
        });
    }

    /**
     * Converts the raw diet map to a map with organism classes and their respective diet configurations.
     *
     * @param rawDietMap the raw diet map
     * @return the converted diet map
     */
    private Map<Class<? extends Organism>, Map<Class<? extends Organism>, Double>> convertRawDietMap(Map<String, Map<String, Double>> rawDietMap) {
        var dietMap = new HashMap<Class<? extends Organism>, Map<Class<? extends Organism>, Double>>();

        for (var eaterAndPreysMap : rawDietMap.entrySet()) {
            var eater = eaterAndPreysMap.getKey();
            var preyNameAndEatChanceMap = eaterAndPreysMap.getValue();

            var organismClass = organismRegistry.getClassByName(eater);
            var preyClassAndEatChanceMap = new HashMap<Class<? extends Organism>, Double>();

            for (var entry : preyNameAndEatChanceMap.entrySet()) {
                var prey = entry.getKey();
                var eatChance = entry.getValue();

                checkEatChance(eatChance);
                var preyClass = organismRegistry.getClassByName(prey);
                preyClassAndEatChanceMap.put(preyClass, eatChance);
            }

            dietMap.put(organismClass, preyClassAndEatChanceMap);
        }

        return dietMap;
    }

    /**
     * Converts the raw characteristics map to a map with organism classes and their respective characteristics.
     *
     * @param rawCharacteristicsMap the raw characteristics map
     * @return the converted characteristics map
     */
    private Map<Class<? extends Organism>, Characteristics> convertRawCharacteristicsMap(Map<String, Characteristics> rawCharacteristicsMap) {
        var characteristicsMap = new HashMap<Class<? extends Organism>, Characteristics>();

        for (var entry : rawCharacteristicsMap.entrySet()) {
            var name = entry.getKey();
            var value = entry.getValue();
            var organismClass = organismRegistry.getClassByName(name);
            characteristicsMap.put(organismClass, value);
        }

        characteristicsMap.forEach((organism, characteristics) -> checkCharacteristics(characteristics));

        return characteristicsMap;
    }

    /**
     * Converts the raw organism emoji map to a map with organism classes and their respective emojis.
     *
     * @param rawEmojiMap the raw emoji map
     * @return the converted organism emoji map
     */
    private Map<Class<? extends Organism>, String> convertRawOrganismEmojiMap(Map<String, Map<String, String>> rawEmojiMap) {
        var organismEmojiKey = "OrganismEmoji";
        if (!rawEmojiMap.containsKey(organismEmojiKey)) {
            throw new InvalidConfigFile("Can't read organisms emojis from emoji file. File don't have key " + organismEmojiKey);
        }

        var emojiMap = new HashMap<Class<? extends Organism>, String>();

        for (var entry : rawEmojiMap.get(organismEmojiKey).entrySet()) {
            var organismClassName = entry.getKey();
            var emoji = entry.getValue();
            var organismClass = organismRegistry.getClassByName(organismClassName);
            emojiMap.put(organismClass, emoji);
        }

        return emojiMap;
    }

    /**
     * Converts the raw location type emoji map to a map with location types and their respective emojis.
     *
     * @param rawLocationTypeEmojiMap the raw location type emoji map
     * @return the converted location type emoji map
     */
    private Map<LocationType, String> converRawLocationTypeEmojiMap(Map<String, Map<String, String>> rawLocationTypeEmojiMap) {
        var locationTypeEmojiKey = "LocationType";
        if (!rawLocationTypeEmojiMap.containsKey(locationTypeEmojiKey)) {
            throw new InvalidConfigFile("Can't read location type emojis from emoji file. File don't have key " + locationTypeEmojiKey);
        }

        var emojiMap = new HashMap<LocationType, String>();

        for (var entry : rawLocationTypeEmojiMap.get(locationTypeEmojiKey).entrySet()) {
            var locationTypeName = entry.getKey();
            var emoji = entry.getValue();
            var locationType = LocationType.valueOf(locationTypeName.toUpperCase());
            emojiMap.put(locationType, emoji);
        }

        return emojiMap;
    }
}
