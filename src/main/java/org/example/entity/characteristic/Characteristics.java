package org.example.entity.characteristic;

/**
 * The {@code Characteristics} record represents the characteristics of an organism.
 * It contains information about the organism's weight, maximum species per coordinate,
 * move speed, food required for satiety, and the starting satiety ratio.
 *
 * @param weight the weight of the organism
 * @param maxSpeciesPerCoordinate the maximum number of this species that can occupy the same coordinate
 * @param moveSpeed the speed at which the organism can move
 * @param foodForSatiety the amount of food required for the organism to reach satiety
 * @param startSatietyRatio the initial satiety ratio of the organism
 */
public record Characteristics(double weight,
                              int maxSpeciesPerCoordinate,
                              int moveSpeed,
                              double foodForSatiety,
                              double startSatietyRatio) {
}
