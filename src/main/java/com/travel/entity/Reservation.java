package com.travel.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import jakarta.validation.constraints.Size;

/**
 * 予約エンティティクラス
 * 
 * 旅行ツアーの予約情報を管理するエンティティです。
 * ユーザー、ツアー、予約詳細、支払い状況などの情報を保持します。
 * JPAの監査機能を使用して作成日時と更新日時を自動管理します。
 * 
 * @author Travel System
 * @version 1.0
 */
@Entity
@Table(name = "reservations")
@EntityListeners(AuditingEntityListener.class)
public class Reservation {

    /** 予約ID（主キー） */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 予約者（ユーザー） */
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /** 予約するツアー */
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tour_id", nullable = false)
    private Tour tour;

    /** 予約人数 */
    @NotNull
    @Positive
    private Integer numberOfPeople;

    /** 総額 */
    @NotNull
    private BigDecimal totalPrice;

    /** 予約ステータス（デフォルト：保留中） */
    @Enumerated(EnumType.STRING)
    private ReservationStatus status = ReservationStatus.PENDING;

    /** 支払いステータス（デフォルト：未払い） */
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus = PaymentStatus.PENDING;

    /** 特別リクエスト（最大500文字） */
    @Size(max = 500)
    private String specialRequests;

    /** 連絡先電話番号（最大100文字） */
    @Size(max = 100)
    private String contactPhone;

    /** 連絡先メールアドレス（最大200文字） */
    @Size(max = 200)
    private String contactEmail;

    /** 作成日時（自動設定、更新不可） */
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /** 更新日時（自動更新） */
    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    /**
     * 予約ステータス列挙型
     * 
     * 予約の現在の状態を表します。
     */
    public enum ReservationStatus {
        /** 保留中 */
        PENDING,
        /** 確認済み */
        CONFIRMED,
        /** キャンセル済み */
        CANCELLED,
        /** 完了 */
        COMPLETED
    }

    /**
     * 支払いステータス列挙型
     * 
     * 支払いの現在の状態を表します。
     */
    public enum PaymentStatus {
        /** 未払い */
        PENDING,
        /** 支払い済み */
        PAID,
        /** 返金済み */
        REFUNDED,
        /** 支払い失敗 */
        FAILED
    }

    // Constructors
    public Reservation() {
    }

    public Reservation(User user, Tour tour, Integer numberOfPeople, BigDecimal totalPrice) {
        this.user = user;
        this.tour = tour;
        this.numberOfPeople = numberOfPeople;
        this.totalPrice = totalPrice;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Tour getTour() {
        return tour;
    }

    public void setTour(Tour tour) {
        this.tour = tour;
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

    public ReservationStatus getStatus() {
        return status;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getSpecialRequests() {
        return specialRequests;
    }

    public void setSpecialRequests(String specialRequests) {
        this.specialRequests = specialRequests;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
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
}