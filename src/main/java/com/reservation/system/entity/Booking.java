package com.reservation.system.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Σύνδεση με τον χρήστη που έκανε την κράτηση
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Παράδειγμα πεδίων κράτησης
    @Column(nullable = false)
    private String date;  // Μπορείς να χρησιμοποιήσεις LocalDate αν το επιθυμείς

    @Column(nullable = false)
    private int passengers;

    @Column(nullable = false)
    private String trip;  // Εδώ μπορεί να αποθηκεύεται το ID ή το όνομα του ταξιδιού

    @Column(nullable = false)
    private String status; // PENDING, CONFIRMED, REJECTED

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
