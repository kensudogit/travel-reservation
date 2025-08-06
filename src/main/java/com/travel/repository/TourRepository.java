package com.travel.repository;

import com.travel.entity.Tour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface TourRepository extends JpaRepository<Tour, Long> {
    
    List<Tour> findByDestinationId(Long destinationId);
    
    List<Tour> findByStatus(Tour.TourStatus status);
    
    List<Tour> findByType(Tour.TourType type);
    
    List<Tour> findByDestinationIdAndStatus(Long destinationId, Tour.TourStatus status);
    
    List<Tour> findByStartDateAfter(LocalDate startDate);
    
    List<Tour> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);
    
    List<Tour> findByDurationBetween(Integer minDuration, Integer maxDuration);
    
    @Query("SELECT t FROM Tour t WHERE t.currentCapacity > 0 AND t.status = 'AVAILABLE'")
    List<Tour> findAvailableTours();
    
    @Query("SELECT t FROM Tour t WHERE t.destination.country = :country AND t.status = 'AVAILABLE'")
    List<Tour> findAvailableToursByCountry(@Param("country") String country);
    
    @Query("SELECT t FROM Tour t WHERE t.name LIKE %:name% OR t.description LIKE %:name%")
    List<Tour> findByNameOrDescriptionContaining(@Param("name") String name);
    
    @Query("SELECT t FROM Tour t WHERE t.startDate BETWEEN :startDate AND :endDate")
    List<Tour> findByStartDateBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    
    @Query("SELECT t FROM Tour t WHERE t.price <= :maxPrice AND t.status = 'AVAILABLE' ORDER BY t.price")
    List<Tour> findAvailableToursByMaxPrice(@Param("maxPrice") BigDecimal maxPrice);
} 