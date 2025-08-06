package com.travel.service;

import com.travel.entity.Destination;
import com.travel.repository.DestinationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DestinationService {
    
    @Autowired
    private DestinationRepository destinationRepository;
    
    @Cacheable("destinations")
    public List<Destination> getAllDestinations() {
        return destinationRepository.findAll();
    }
    
    @Cacheable("destinations")
    public List<Destination> getActiveDestinations() {
        return destinationRepository.findByActive(true);
    }
    
    public Optional<Destination> getDestinationById(Long id) {
        return destinationRepository.findById(id);
    }
    
    public Destination createDestination(Destination destination) {
        if (destinationRepository.findByNameOrDescriptionContaining(destination.getName()).size() > 0) {
            throw new RuntimeException("Destination with similar name already exists");
        }
        return destinationRepository.save(destination);
    }
    
    @CacheEvict(value = "destinations", allEntries = true)
    public Destination updateDestination(Long id, Destination destinationDetails) {
        Destination destination = destinationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Destination not found"));
        
        destination.setName(destinationDetails.getName());
        destination.setDescription(destinationDetails.getDescription());
        destination.setCountry(destinationDetails.getCountry());
        destination.setCity(destinationDetails.getCity());
        destination.setRegion(destinationDetails.getRegion());
        destination.setType(destinationDetails.getType());
        destination.setActive(destinationDetails.isActive());
        destination.setImageUrl(destinationDetails.getImageUrl());
        
        return destinationRepository.save(destination);
    }
    
    @CacheEvict(value = "destinations", allEntries = true)
    public void deleteDestination(Long id) {
        Destination destination = destinationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Destination not found"));
        destinationRepository.delete(destination);
    }
    
    public List<Destination> getDestinationsByCountry(String country) {
        return destinationRepository.findByCountry(country);
    }
    
    public List<Destination> getDestinationsByType(Destination.DestinationType type) {
        return destinationRepository.findByType(type);
    }
    
    public List<Destination> getActiveDestinationsByCountry(String country) {
        return destinationRepository.findByCountryAndActive(country, true);
    }
    
    public List<Destination> searchDestinations(String name) {
        return destinationRepository.findByNameOrDescriptionContaining(name);
    }
    
    public List<String> getAllActiveCountries() {
        return destinationRepository.findAllActiveCountries();
    }
    
    public List<Destination> getDestinationsByRegion(String region) {
        return destinationRepository.findByRegionAndActive(region);
    }
    
    public void toggleDestinationStatus(Long id) {
        Destination destination = destinationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Destination not found"));
        destination.setActive(!destination.isActive());
        destinationRepository.save(destination);
    }
} 