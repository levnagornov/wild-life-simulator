package org.example.service;

import org.example.entity.organism.animal.Animal;

import java.util.List;

public class PartnerFindingService {
    public List<Animal> getPossiblePartners(Animal animal, List<Animal> animals) {
        return animals.stream()
                      .filter(partner -> isPossiblePartner(animal, partner))
                      .toList();
    }

    private static boolean isPossiblePartner(Animal animal, Animal partner) {
        return partner.isAlive()
                && partner.isReadyToMate()
                && partner.getClass().equals(animal.getClass())
                && partner != animal;
    }
}
