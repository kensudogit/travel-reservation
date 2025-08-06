package com.travel.service;

import com.travel.entity.Tour;
import com.travel.repository.TourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TourService {
    
    @Autowired
    private TourRepository tourRepository;
    
    @Cacheable("tours")
    public List<Tour> getAllTours() {
        return tourRepository.findAll();
    }
    
    @Cacheable("tours")
    public List<Tour> getAvailableTours() {
        return tourRepository.findAvailableTours();
    }
    
    public Optional<Tour> getTourById(Long id) {
        return tourRepository.findById(id);
    }
    
    public Tour createTour(Tour tour) {
        if (tour.getStartDate().isBefore(LocalDate.now())) {
            throw new RuntimeException("Tour start date cannot be in the past");
        }
        if (tour.getEndDate().isBefore(tour.getStartDate())) {
            throw new RuntimeException("Tour end date cannot be before start date");
        }
        if (tour.getCurrentCapacity() > tour.getMaxCapacity()) {
            throw new RuntimeException("Current capacity cannot exceed max capacity");
        }
        
        return tourRepository.save(tour);
    }
    
    @CacheEvict(value = "tours", allEntries = true)
    public Tour updateTour(Long id, Tour tourDetails) {
        Tour tour = tourRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tour not found"));
        
        tour.setName(tourDetails.getName());
        tour.setDescription(tourDetails.getDescription());
        tour.setDestination(tourDetails.getDestination());
        tour.setPrice(tourDetails.getPrice());
        tour.setDuration(tourDetails.getDuration());
        tour.setMaxCapacity(tourDetails.getMaxCapacity());
        tour.setCurrentCapacity(tourDetails.getCurrentCapacity());
        tour.setStartDate(tourDetails.getStartDate());
        tour.setEndDate(tourDetails.getEndDate());
        tour.setType(tourDetails.getType());
        tour.setStatus(tourDetails.getStatus());
        tour.setImageUrl(tourDetails.getImageUrl());
        
        return tourRepository.save(tour);
    }
    
    @CacheEvict(value = "tours", allEntries = true)
    public void deleteTour(Long id) {
        Tour tour = tourRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tour not found"));
        tourRepository.delete(tour);
    }
    
    public List<Tour> getToursByDestination(Long destinationId) {
        return tourRepository.findByDestinationId(destinationId);
    }
    
    public List<Tour> getToursByStatus(Tour.TourStatus status) {
        return tourRepository.findByStatus(status);
    }
    
    public List<Tour> getToursByType(Tour.TourType type) {
        return tourRepository.findByType(type);
    }
    
    public List<Tour> getToursByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        return tourRepository.findByPriceBetween(minPrice, maxPrice);
    }
    
    public List<Tour> getToursByDurationRange(Integer minDuration, Integer maxDuration) {
        return tourRepository.findByDurationBetween(minDuration, maxDuration);
    }
    
    public List<Tour> getAvailableToursByCountry(String country) {
        return tourRepository.findAvailableToursByCountry(country);
    }
    
    public List<Tour> searchTours(String name) {
        return tourRepository.findByNameOrDescriptionContaining(name);
    }
    
    public List<Tour> getToursByDateRange(LocalDate startDate, LocalDate endDate) {
        return tourRepository.findByStartDateBetween(startDate, endDate);
    }
    
    public List<Tour> getAvailableToursByMaxPrice(BigDecimal maxPrice) {
        return tourRepository.findAvailableToursByMaxPrice(maxPrice);
    }
    
    public List<Tour> getUpcomingTours() {
        return tourRepository.findByStartDateAfter(LocalDate.now());
    }
    
    public void updateTourCapacity(Long tourId, Integer newCapacity) {
        Tour tour = tourRepository.findById(tourId)
                .orElseThrow(() -> new RuntimeException("Tour not found"));
        
        if (newCapacity < 0) {
            throw new RuntimeException("Capacity cannot be negative");
        }
        if (newCapacity > tour.getMaxCapacity()) {
            throw new RuntimeException("Current capacity cannot exceed max capacity");
        }
        
        tour.setCurrentCapacity(newCapacity);
        
        if (newCapacity == 0) {
            tour.setStatus(Tour.TourStatus.FULL);
        } else if (tour.getStatus() == Tour.TourStatus.FULL) {
            tour.setStatus(Tour.TourStatus.AVAILABLE);
        }
        
        tourRepository.save(tour);
    }
    
    public void cancelTour(Long tourId) {
        Tour tour = tourRepository.findById(tourId)
                .orElseThrow(() -> new RuntimeException("Tour not found"));
        
        tour.setStatus(Tour.TourStatus.CANCELLED);
        tourRepository.save(tour);
    }
} 