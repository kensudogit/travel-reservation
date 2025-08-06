package com.travel.dto;

import com.travel.entity.Reservation;
import java.math.BigDecimal;

public class ReservationSummaryDto {
    private Long id;
    private Integer numberOfPeople;
    private BigDecimal totalPrice;
    private Reservation.ReservationStatus status;

    // Constructors
    public ReservationSummaryDto() {
    }

    public ReservationSummaryDto(Long id, Integer numberOfPeople, BigDecimal totalPrice,
            Reservation.ReservationStatus status) {
        this.id = id;
        this.numberOfPeople = numberOfPeople;
        this.totalPrice = totalPrice;
        this.status = status;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumberOfPeople() {
        return numberOfPeople;
    }

    public void setNumberOfPeople(Integer numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Reservation.ReservationStatus getStatus() {
        return status;
    }

    public void setStatus(Reservation.ReservationStatus status) {
        this.status = status;
    }
}