package com.reservation.system.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "cancellations")
public class Cancellation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Σύνδεση με κράτηση
    @ManyToOne
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;

    @Column(nullable = false)
    private String reason;

    @Column(nullable = false)
    private LocalDateTime cancellationDate;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
}
