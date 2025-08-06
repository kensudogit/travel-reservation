package com.travel.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tours")
@EntityListeners(AuditingEntityListener.class)
public class Tour {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank
    @Size(max = 200)
    private String name;
    
    @NotBlank
    @Size(max = 1000)
    private String description;
    
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destination_id", nullable = false)
    private Destination destination;
    
    @NotNull
    @Positive
    private BigDecimal price;
    
    @NotNull
    @Positive
    private Integer duration; // in days
    
    @NotNull
    @Positive
    private Integer maxCapacity;
    
    @NotNull
    @Positive
    private Integer currentCapacity;
    
    @NotNull
    private LocalDate startDate;
    
    @NotNull
    private LocalDate endDate;
    
    @Enumerated(EnumType.STRING)
    private TourType type = TourType.GROUP;
    
    @Enumerated(EnumType.STRING)
    private TourStatus status = TourStatus.AVAILABLE;
    
    @Size(max = 500)
    private String imageUrl;
    
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;
    
    @OneToMany(mappedBy = "tour", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Reservation> reservations = new HashSet<>();
    
    public enum TourType {
        GROUP, PRIVATE, CUSTOM
    }
    
    public enum TourStatus {
        AVAILABLE, FULL, CANCELLED, COMPLETED
    }
    
    // Constructors
    public Tour() {}
    
    public Tour(String name, String description, Destination destination, BigDecimal price, 
                Integer duration, Integer maxCapacity, LocalDate startDate, LocalDate endDate) {
        this.name = name;
        this.description = description;
        this.destination = destination;
        this.price = price;
        this.duration = duration;
        this.maxCapacity = maxCapacity;
        this.currentCapacity = maxCapacity;
        this.startDate = startDate;
        this.endDate = endDate;
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
    
    public Destination getDestination() {
        return destination;
    }
    
    public void setDestination(Destination destination) {
        this.destination = destination;
    }
    
    public BigDecimal getPrice() {
        return price;
    }
    
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    
    public Integer getDuration() {
        return duration;
    }
    
    public void setDuration(Integer duration) {
        this.duration = duration;
    }
    
    public Integer getMaxCapacity() {
        return maxCapacity;
    }
    
    public void setMaxCapacity(Integer maxCapacity) {
        this.maxCapacity = maxCapacity;
    }
    
    public Integer getCurrentCapacity() {
        return currentCapacity;
    }
    
    public void setCurrentCapacity(Integer currentCapacity) {
        this.currentCapacity = currentCapacity;
    }
    
    public LocalDate getStartDate() {
        return startDate;
    }
    
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
    
    public LocalDate getEndDate() {
        return endDate;
    }
    
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
    
    public TourType getType() {
        return type;
    }
    
    public void setType(TourType type) {
        this.type = type;
    }
    
    public TourStatus getStatus() {
        return status;
    }
    
    public void setStatus(TourStatus status) {
        this.status = status;
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
    
    public Set<Reservation> getReservations() {
        return reservations;
    }
    
    public void setReservations(Set<Reservation> reservations) {
        this.reservations = reservations;
    }
} 