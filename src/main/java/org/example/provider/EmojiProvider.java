package org.example.provider;

import org.example.config.reader.ConfigReader;
import org.example.entity.location.LocationType;
import org.example.entity.organism.Organism;

import java.util.Map;

/**
 * The {@code EmojiProvider} class provides emoji representations for location types and organisms.
 * It reads emoji configurations from a {@code ConfigReader} and stores them in maps.
 */
public class EmojiProvider {
    private final Map<LocationType, String> locationTypeEmojis;
    private final Map<Class<? extends Organism>, String> organismEmojisMap;

    /**
     * Constructs a new {@code EmojiProvider} with the specified configuration reader.
     *
     * @param configReader the configuration reader to use for reading emoji configurations
     */
    public EmojiProvider(ConfigReader configReader) {
        this.locationTypeEmojis = configReader.readLocationTypeEmojis();
        this.organismEmojisMap = configReader.readOrganismEmojis();
    }

    /**
     * Returns the emoji representation for the specified location type.
     * If no emoji is found for the location type, the name of the location type is returned.
     *
     * @param locationType the location type for which to get the emoji
     * @return the emoji representation for the specified location type
     */
    public String getLocationTypeEmoji(LocationType locationType) {
        return locationTypeEmojis.getOrDefault(locationType, locationType.name());
    }

    /**
     * Returns the emoji representation for the specified organism class.
     * If no emoji is found for the organism class, the simple name of the organism class is returned.
     *
     * @param organismClass the class of the organism for which to get the emoji
     * @return the emoji representation for the specified organism class
     */
    public String getOrganismEmoji(Class<? extends Organism> organismClass) {
        return organismEmojisMap.getOrDefault(organismClass, organismClass.getSimpleName());
    }
}
