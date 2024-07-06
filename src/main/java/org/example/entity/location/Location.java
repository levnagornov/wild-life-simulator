package org.example.entity.location;

import org.example.entity.coordinate.Coordinate;
import org.example.entity.organism.Organism;
import org.example.entity.organism.animal.Animal;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

/**
 * The {@code Location} class represents a location in the simulation.
 * It contains information about the type of location, its coordinates, and the organisms within it.
 * It also provides various methods to interact with and manipulate the organisms present in the location.
 */
public class Location {
    private static final AtomicLong idCounter = new AtomicLong(0);

    private final long id;
    private final LocationType locationType;
    private final Coordinate coordinate;
    private final ReentrantLock reentrantLock;
    private List<Organism> organisms;

    /**
     * Constructs a {@code Location} with the specified location type and coordinate.
     * Initializes a unique ID for the location and creates a lock for thread-safe operations.
     *
     * @param locationType the type of the location
     * @param coordinate the coordinate of the location
     */
    public Location(LocationType locationType, Coordinate coordinate) {
        this.id = idCounter.incrementAndGet();
        this.locationType = locationType;
        this.coordinate = coordinate;
        this.reentrantLock = new ReentrantLock(true);
        this.organisms = new CopyOnWriteArrayList<>();
    }

    /**
     * Returns the list of organisms present in this location.
     *
     * @return the list of organisms
     */
    public List<Organism> getOrganisms() {
        return organisms;
    }

    /**
     * Returns the type of this location.
     *
     * @return the location type
     */
    public LocationType getLocationType() {
        return locationType;
    }

    /**
     * Returns the coordinate of this location.
     *
     * @return the coordinate
     */
    public Coordinate getCoordinate() {
        return coordinate;
    }

    /**
     * Returns the reentrant lock for this location.
     *
     * @return the reentrant lock
     */
    public ReentrantLock getReentrantLock() {
        return reentrantLock;
    }

    /**
     * Returns the unique ID of this location.
     *
     * @return the unique ID
     */
    public long getId() {
        return id;
    }

    /**
     * Adds an organism to this location.
     *
     * @param organism the organism to be added
     */
    public void addOrganism(Organism organism) {
        if (organism != null) {
            organisms.add(organism);
        }
    }

    /**
     * Adds a list of organisms to this location.
     *
     * @param organisms the list of organisms to be added
     */
    public void addOrganisms(List<Organism> organisms) {
        if (organisms != null) {
            this.organisms.addAll(organisms);
        }
    }

    /**
     * Removes a specified organism from this location.
     *
     * @param organism the organism to be removed
     */
    public void removeOrganism(Organism organism) {
        if (organism != null) {
            this.organisms.remove(organism);
        }
    }

    /**
     * Removes all dead organisms from this location.
     */
    public void removeDeadOrganisms() {
        organisms = getAiveOrganisms();
    }

    /**
     * Returns a map grouping the organisms by their superclass.
     *
     * @return the map grouping organisms by superclass
     */
    public Map<Class<?>, List<Organism>> getGroupedOrganismsBySuperclass() {
        return organisms.parallelStream()
                .collect(Collectors.groupingBy(organism -> organism.getClass().getSuperclass()));
    }

    /**
     * Returns a map grouping the organisms by their class.
     *
     * @return the map grouping organisms by class
     */
    public Map<Class<?>, List<Organism>> getGroupedOrganismsByClass() {
        return organisms.parallelStream()
                .collect(Collectors.groupingBy(Organism::getClass));
    }

    /**
     * Returns a map grouping the animals by their class.
     *
     * @return the map grouping animals by class
     */
    public Map<Class<?>, List<Animal>> getGroupedAnimalsByClass() {
        return getAnimals().parallelStream()
                .collect(Collectors.groupingBy(Animal::getClass));
    }

    /**
     * Returns a map counting the number of organisms of each species.
     *
     * @return the map counting the number of organisms of each species
     */
    public Map<Class<? extends Organism>, Integer> getSpeciesCounterMap() {
        var organismCounter = new ConcurrentHashMap<Class<? extends Organism>, Integer>();

        getAiveOrganisms().forEach(organism -> organismCounter.merge(organism.getClass(), 1, Integer::sum));

        return organismCounter;
    }

    /**
     * Returns a list of all animals present in this location.
     *
     * @return the list of animals
     */
    public List<Animal> getAnimals() {
        return organisms.parallelStream()
                .filter(organism -> Animal.class.isAssignableFrom(organism.getClass()))
                .map(organism -> (Animal) organism)
                .toList();
    }

    /**
     * Returns a list of all dead organisms present in this location.
     *
     * @return the list of dead organisms
     */
    public List<Organism> getDeadOrganisms() {
        return organisms.parallelStream()
                .filter(organism -> !organism.isAlive())
                .toList();
    }

    /**
     * Returns a list of all alive organisms present in this location.
     *
     * @return the list of alive organisms
     */
    public CopyOnWriteArrayList<Organism> getAiveOrganisms() {
        return organisms.stream()
                .filter(Organism::isAlive)
                .collect(Collectors.toCollection(CopyOnWriteArrayList::new));
    }

    /**
     * Checks if this location is equal to another object.
     * The result is {@code true} if and only if the argument is not {@code null} and is a {@code Location} object that has the same ID as this object.
     *
     * @param o the object to compare this {@code Location} against
     * @return {@code true} if the given object represents a {@code Location} with the same ID, {@code false} otherwise
     */
    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Location location)) return false;

        return id == location.id;
    }

    /**
     * Returns a hash code value for this location. The hash code is based on the unique ID.
     *
     * @return a hash code value for this location
     */
    @Override
    public int hashCode() {
        return Long.hashCode(id);
    }

    /**
     * Returns a string representation of the location.
     *
     * @return a string representation of the location
     */
    @Override
    public String toString() {
        return "Location{" +
                "id=" + id +
                ", locationType=" + locationType +
                ", coordinate=" + coordinate +
                '}';
    }
}
