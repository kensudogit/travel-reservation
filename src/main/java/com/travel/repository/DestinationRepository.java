package com.travel.repository;

import com.travel.entity.Destination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DestinationRepository extends JpaRepository<Destination, Long> {
    
    List<Destination> findByActive(boolean active);
    
    List<Destination> findByCountry(String country);
    
    List<Destination> findByType(Destination.DestinationType type);
    
    List<Destination> findByCountryAndActive(String country, boolean active);
    
    @Query("SELECT d FROM Destination d WHERE d.name LIKE %:name% OR d.description LIKE %:name%")
    List<Destination> findByNameOrDescriptionContaining(@Param("name") String name);
    
    @Query("SELECT DISTINCT d.country FROM Destination d WHERE d.active = true ORDER BY d.country")
    List<String> findAllActiveCountries();
    
    @Query("SELECT d FROM Destination d WHERE d.region = :region AND d.active = true")
    List<Destination> findByRegionAndActive(@Param("region") String region);
} 