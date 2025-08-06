package com.travel.repository;

import com.travel.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    
    List<Reservation> findByUserId(Long userId);
    
    List<Reservation> findByTourId(Long tourId);
    
    List<Reservation> findByStatus(Reservation.ReservationStatus status);
    
    List<Reservation> findByPaymentStatus(Reservation.PaymentStatus paymentStatus);
    
    List<Reservation> findByUserIdAndStatus(Long userId, Reservation.ReservationStatus status);
    
    @Query("SELECT r FROM Reservation r WHERE r.createdAt >= :startDate")
    List<Reservation> findByCreatedAtAfter(@Param("startDate") LocalDateTime startDate);
    
    @Query("SELECT r FROM Reservation r WHERE r.totalPrice >= :minPrice")
    List<Reservation> findByTotalPriceGreaterThanEqual(@Param("minPrice") BigDecimal minPrice);
    
    @Query("SELECT r FROM Reservation r WHERE r.tour.destination.country = :country")
    List<Reservation> findByDestinationCountry(@Param("country") String country);
    
    @Query("SELECT r FROM Reservation r WHERE r.user.username = :username")
    List<Reservation> findByUsername(@Param("username") String username);
    
    @Query("SELECT COUNT(r) FROM Reservation r WHERE r.tour.id = :tourId AND r.status = 'CONFIRMED'")
    Long countConfirmedReservationsByTourId(@Param("tourId") Long tourId);
    
    @Query("SELECT SUM(r.totalPrice) FROM Reservation r WHERE r.status = 'CONFIRMED' AND r.paymentStatus = 'PAID'")
    BigDecimal getTotalRevenue();
    
    @Query("SELECT r FROM Reservation r WHERE r.tour.startDate BETWEEN :startDate AND :endDate")
    List<Reservation> findByTourStartDateBetween(@Param("startDate") java.time.LocalDate startDate, 
                                                 @Param("endDate") java.time.LocalDate endDate);
} 