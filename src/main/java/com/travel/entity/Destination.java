package com.travel.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "destinations")
@EntityListeners(AuditingEntityListener.class)
public class Destination {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank
    @Size(max = 100)
    @Column(unique = true)
    private String name;
    
    @NotBlank
    @Size(max = 200)
    private String description;
    
    @NotBlank
    @Size(max = 100)
    private String country;
    
    @Size(max = 100)
    private String city;
    
    @Size(max = 100)
    private String region;
    
    @Enumerated(EnumType.STRING)
    private DestinationType type = DestinationType.CITY;
    
    private boolean active = true;
    
    @Size(max = 500)
    private String imageUrl;
    
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;
    
    @OneToMany(mappedBy = "destination", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Tour> tours = new HashSet<>();
    
    public enum DestinationType {
        CITY, COUNTRY, REGION, RESORT, LANDMARK
    }
    
    // Constructors
    public Destination() {}
    
    public Destination(String name, String description, String country) {
        this.name = name;
        this.description = description;
        this.country = country;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getCountry() {
        return country;
    }
    
    public void setCountry(String country) {
        this.country = country;
    }
    
    public String getCity() {
        return city;
    }
    
    public void setCity(String city) {
        this.city = city;
    }
    
    public String getRegion() {
        return region;
    }
    
    public void setRegion(String region) {
        this.region = region;
    }
    
    public DestinationType getType() {
        return type;
    }
    
    public void setType(DestinationType type) {
        this.type = type;
    }
    
    public boolean isActive() {
        return active;
    }
    
    public void setActive(boolean active) {
        this.active = active;
    }
    
    public String getImageUrl() {
        return imageUrl;
    }
    
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    public Set<Tour> getTours() {
        return tours;
    }
    
    public void setTours(Set<Tour> tours) {
        this.tours = tours;
    }
} 