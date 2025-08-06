package com.travel.controller;

import com.travel.entity.Destination;
import com.travel.service.DestinationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/destinations")
@CrossOrigin(origins = "*")
public class DestinationController {
    
    @Autowired
    private DestinationService destinationService;
    
    @GetMapping
    public ResponseEntity<List<Destination>> getAllDestinations() {
        List<Destination> destinations = destinationService.getAllDestinations();
        return ResponseEntity.ok(destinations);
    }
    
    @GetMapping("/active")
    public ResponseEntity<List<Destination>> getActiveDestinations() {
        List<Destination> destinations = destinationService.getActiveDestinations();
        return ResponseEntity.ok(destinations);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Destination> getDestinationById(@PathVariable Long id) {
        Optional<Destination> destination = destinationService.getDestinationById(id);
        return destination.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Destination> createDestination(@Valid @RequestBody Destination destination) {
        try {
            Destination createdDestination = destinationService.createDestination(destination);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdDestination);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Destination> updateDestination(@PathVariable Long id, @Valid @RequestBody Destination destinationDetails) {
        try {
            Destination updatedDestination = destinationService.updateDestination(id, destinationDetails);
            return ResponseEntity.ok(updatedDestination);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteDestination(@PathVariable Long id) {
        try {
            destinationService.deleteDestination(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/country/{country}")
    public ResponseEntity<List<Destination>> getDestinationsByCountry(@PathVariable String country) {
        List<Destination> destinations = destinationService.getDestinationsByCountry(country);
        return ResponseEntity.ok(destinations);
    }
    
    @GetMapping("/type/{type}")
    public ResponseEntity<List<Destination>> getDestinationsByType(@PathVariable Destination.DestinationType type) {
        List<Destination> destinations = destinationService.getDestinationsByType(type);
        return ResponseEntity.ok(destinations);
    }
    
    @GetMapping("/country/{country}/active")
    public ResponseEntity<List<Destination>> getActiveDestinationsByCountry(@PathVariable String country) {
        List<Destination> destinations = destinationService.getActiveDestinationsByCountry(country);
        return ResponseEntity.ok(destinations);
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<Destination>> searchDestinations(@RequestParam String name) {
        List<Destination> destinations = destinationService.searchDestinations(name);
        return ResponseEntity.ok(destinations);
    }
    
    @GetMapping("/countries")
    public ResponseEntity<List<String>> getAllActiveCountries() {
        List<String> countries = destinationService.getAllActiveCountries();
        return ResponseEntity.ok(countries);
    }
    
    @GetMapping("/region/{region}")
    public ResponseEntity<List<Destination>> getDestinationsByRegion(@PathVariable String region) {
        List<Destination> destinations = destinationService.getDestinationsByRegion(region);
        return ResponseEntity.ok(destinations);
    }
    
    @PatchMapping("/{id}/toggle-status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> toggleDestinationStatus(@PathVariable Long id) {
        try {
            destinationService.toggleDestinationStatus(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
} 