package org.example.service;

import org.example.entity.organism.animal.Animal;

import java.util.List;

/**
 * The {@code PartnerFindingService} class finds possible partners for an animal.
 */
public class PartnerFindingService {

    /**
     * Returns a list of possible partners for the specified animal.
     *
     * @param animal  the animal for which partners are sought
     * @param animals the list of animals to search for partners
     * @return a list of possible partners for the animal
     */
    public List<Animal> getPossiblePartners(Animal animal, List<Animal> animals) {
        return animals.stream()
                      .filter(partner -> isPossiblePartner(animal, partner))
                      .toList();
    }

    /**
     * Checks if the specified partner is a possible mating partner for the animal.
     *
     * @param animal  the animal
     * @param partner the potential partner
     * @return {@code true} if the partner is a possible mating partner, otherwise {@code false}
     */
    private static boolean isPossiblePartner(Animal animal, Animal partner) {
        return partner.isAlive()
                && partner.isReadyToMate()
                && partner.getClass().equals(animal.getClass())
                && partner != animal;
    }
}
