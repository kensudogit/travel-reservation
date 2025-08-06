package com.travel.dto;

import com.travel.entity.User;
import java.time.LocalDateTime;
import java.util.List;

public class UserWithReservationsDto {
    private Long id;
    private String username;
    private String email;
    private String fullName;
    private User.UserRole role;
    private boolean enabled;
    private LocalDateTime createdAt;
    private List<ReservationSummaryDto> reservations;

    // Constructors
    public UserWithReservationsDto() {
    }

    public UserWithReservationsDto(Long id, String username, String email, String fullName,
            User.UserRole role, boolean enabled, LocalDateTime createdAt) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.fullName = fullName;
        this.role = role;
        this.enabled = enabled;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public User.UserRole getRole() {
        return role;
    }

    public void setRole(User.UserRole role) {
        this.role = role;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<ReservationSummaryDto> getReservations() {
        return reservations;
    }

    public void setReservations(List<ReservationSummaryDto> reservations) {
        this.reservations = reservations;
    }
}