package org.example.provider;

import org.example.config.reader.ConfigReader;
import org.example.entity.location.LocationType;
import org.example.entity.organism.Organism;

import java.util.Map;

public class EmojiProvider {
    private final Map<LocationType, String> locationTypeEmojis;
    private final Map<Class<? extends Organism>, String> organismEmojisMap;

    public EmojiProvider(ConfigReader configReader) {
        this.locationTypeEmojis = configReader.readLocationTypeEmojis();
        this.organismEmojisMap = configReader.readOrganismEmojis();
    }

    public String getLocationTypeEmoji(LocationType locationType) {
        return locationTypeEmojis.getOrDefault(locationType, locationType.name());
    }

    public String getOrganismEmoji(Class<? extends Organism> organismClass) {
        return organismEmojisMap.getOrDefault(organismClass, organismClass.getSimpleName());
    }
}