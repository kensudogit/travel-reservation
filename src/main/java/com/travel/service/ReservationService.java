package com.travel.service;

import com.travel.entity.Reservation;
import com.travel.entity.Tour;
import com.travel.entity.User;
import com.travel.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ReservationService {
    
    @Autowired
    private ReservationRepository reservationRepository;
    
    @Autowired
    private TourService tourService;
    
    @Autowired
    private UserService userService;
    
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }
    
    public Optional<Reservation> getReservationById(Long id) {
        return reservationRepository.findById(id);
    }
    
    public Reservation createReservation(Reservation reservation) {
        // Validate tour availability
        Tour tour = tourService.getTourById(reservation.getTour().getId())
                .orElseThrow(() -> new RuntimeException("Tour not found"));
        
        if (tour.getStatus() != Tour.TourStatus.AVAILABLE) {
            throw new RuntimeException("Tour is not available for reservation");
        }
        
        if (tour.getCurrentCapacity() < reservation.getNumberOfPeople()) {
            throw new RuntimeException("Not enough capacity for this reservation");
        }
        
        // Calculate total price
        BigDecimal totalPrice = tour.getPrice().multiply(BigDecimal.valueOf(reservation.getNumberOfPeople()));
        reservation.setTotalPrice(totalPrice);
        
        // Update tour capacity
        tourService.updateTourCapacity(tour.getId(), tour.getCurrentCapacity() - reservation.getNumberOfPeople());
        
        return reservationRepository.save(reservation);
    }
    
    public Reservation updateReservation(Long id, Reservation reservationDetails) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));
        
        // If number of people changed, update tour capacity
        if (!reservation.getNumberOfPeople().equals(reservationDetails.getNumberOfPeople())) {
            Tour tour = reservation.getTour();
            int capacityDifference = reservation.getNumberOfPeople() - reservationDetails.getNumberOfPeople();
            tourService.updateTourCapacity(tour.getId(), tour.getCurrentCapacity() + capacityDifference);
            
            // Recalculate total price
            BigDecimal totalPrice = tour.getPrice().multiply(BigDecimal.valueOf(reservationDetails.getNumberOfPeople()));
            reservation.setTotalPrice(totalPrice);
        }
        
        reservation.setNumberOfPeople(reservationDetails.getNumberOfPeople());
        reservation.setStatus(reservationDetails.getStatus());
        reservation.setPaymentStatus(reservationDetails.getPaymentStatus());
        reservation.setSpecialRequests(reservationDetails.getSpecialRequests());
        reservation.setContactPhone(reservationDetails.getContactPhone());
        reservation.setContactEmail(reservationDetails.getContactEmail());
        
        return reservationRepository.save(reservation);
    }
    
    public void deleteReservation(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));
        
        // Restore tour capacity
        Tour tour = reservation.getTour();
        tourService.updateTourCapacity(tour.getId(), tour.getCurrentCapacity() + reservation.getNumberOfPeople());
        
        reservationRepository.delete(reservation);
    }
    
    public List<Reservation> getReservationsByUser(Long userId) {
        return reservationRepository.findByUserId(userId);
    }
    
    public List<Reservation> getReservationsByTour(Long tourId) {
        return reservationRepository.findByTourId(tourId);
    }
    
    public List<Reservation> getReservationsByStatus(Reservation.ReservationStatus status) {
        return reservationRepository.findByStatus(status);
    }
    
    public List<Reservation> getReservationsByPaymentStatus(Reservation.PaymentStatus paymentStatus) {
        return reservationRepository.findByPaymentStatus(paymentStatus);
    }
    
    public List<Reservation> getReservationsByUserAndStatus(Long userId, Reservation.ReservationStatus status) {
        return reservationRepository.findByUserIdAndStatus(userId, status);
    }
    
    public List<Reservation> getReservationsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return reservationRepository.findByCreatedAtAfter(startDate);
    }
    
    public List<Reservation> getReservationsByDestinationCountry(String country) {
        return reservationRepository.findByDestinationCountry(country);
    }
    
    public List<Reservation> getReservationsByUsername(String username) {
        return reservationRepository.findByUsername(username);
    }
    
    public Long getConfirmedReservationsCountByTour(Long tourId) {
        return reservationRepository.countConfirmedReservationsByTourId(tourId);
    }
    
    public BigDecimal getTotalRevenue() {
        BigDecimal revenue = reservationRepository.getTotalRevenue();
        return revenue != null ? revenue : BigDecimal.ZERO;
    }
    
    public void confirmReservation(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));
        
        reservation.setStatus(Reservation.ReservationStatus.CONFIRMED);
        reservationRepository.save(reservation);
    }
    
    public void cancelReservation(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));
        
        // Restore tour capacity
        Tour tour = reservation.getTour();
        tourService.updateTourCapacity(tour.getId(), tour.getCurrentCapacity() + reservation.getNumberOfPeople());
        
        reservation.setStatus(Reservation.ReservationStatus.CANCELLED);
        reservationRepository.save(reservation);
    }
    
    public void updatePaymentStatus(Long id, Reservation.PaymentStatus paymentStatus) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));
        
        reservation.setPaymentStatus(paymentStatus);
        reservationRepository.save(reservation);
    }
} 