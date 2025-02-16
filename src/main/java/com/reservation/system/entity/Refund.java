package com.reservation.system.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "refunds")
public class Refund {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Σύνδεση με κράτηση
    @OneToOne
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;

    @Column(nullable = false)
    private double amount;

    @Column(nullable = false)
    private String status; // PROCESSED, PENDING, FAILED

    @Column(nullable = false)
    private boolean approved = false;

    @ManyToOne
    @JoinColumn(name = "approved_by_admin_id")
    private Admin approvedBy;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    // Μέθοδοι για έγκριση/απόρριψη επιστροφής χρημάτων
    public void approveRefund(Admin admin) {
        this.approved = true;
        this.status = "PROCESSED";
        this.approvedBy = admin;
    }

    public void rejectRefund(Admin admin) {
        this.approved = false;
        this.status = "FAILED";
        this.approvedBy = admin;
    }
}

    // Σύνδεση με admin που εξέδωσε την έγκριση ή απόρριψη\n    @ManyToOne\n    @JoinColumn(name = \"approved_by_admin_id\")\n    private Admin approvedBy;\n    \n    @CreationTimestamp\n    @Column(updatable = false)\n    private LocalDateTime createdAt;\n\n    // Μεθοδολογία για έγκριση / απόρριψη\n    public void approveRefund(Admin admin) {\n        this.approved = true;\n        this.status = \"PROCESSED\";\n        this.approvedBy = admin;\n    }\n\n    public void rejectRefund(Admin admin) {\n        this.approved = false;\n        this.status = \"FAILED\";\n        this.approvedBy = admin;\n    }\n}\n```

