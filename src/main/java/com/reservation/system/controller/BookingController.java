package com.reservation.system.controller;


import com.reservation.system.entity.Booking;
import com.reservation.system.entity.Admin;
import com.reservation.system.entity.User;
import com.reservation.system.repository.BookingRepository;
import com.reservation.system.repository.AdminRepository;
import com.reservation.system.repository.UserRepository;
import com.reservation.system.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private EmailService emailService;

    // Endpoint για δημιουργία κράτησης
    @PostMapping("/create")
    public ResponseEntity<Booking> createBooking(@RequestBody Booking booking) {
        Optional<User> user = userRepository.findById(booking.getUser().getId());
        if (user.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        booking.setUser(user.get());
        booking.setStatus("PENDING");
        Booking savedBooking = bookingRepository.save(booking);
        emailService.sendEmail(user.get().getEmail(), "Booking Created", "Your booking request is pending approval.");
        return ResponseEntity.ok(savedBooking);
    }

    // Endpoint για έγκριση κράτησης από admin
    @PutMapping("/{bookingId}/approve/admin/{adminId}")
    public ResponseEntity<Booking> approveBooking(@PathVariable Long bookingId, @PathVariable Long adminId) {
        Optional<Admin> admin = adminRepository.findById(adminId);
        if (admin.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        return bookingRepository.findById(bookingId).map(booking -> {
            booking.setStatus("CONFIRMED");
            bookingRepository.save(booking);
            emailService.sendEmail(booking.getUser().getEmail(), "Booking Approved", "Your booking has been approved.");
            return ResponseEntity.ok(booking);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Endpoint για απόρριψη κράτησης από admin
    @PutMapping("/{bookingId}/reject/admin/{adminId}")
    public ResponseEntity<Booking> rejectBooking(@PathVariable Long bookingId, @PathVariable Long adminId) {
        Optional<Admin> admin = adminRepository.findById(adminId);
        if (admin.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        return bookingRepository.findById(bookingId).map(booking -> {
            booking.setStatus("REJECTED");
            bookingRepository.save(booking);
            emailService.sendEmail(booking.getUser().getEmail(), "Booking Rejected", "Your booking has been rejected.");
            return ResponseEntity.ok(booking);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Endpoint για λήψη όλων των κρατήσεων (για αναφορές)
    @GetMapping("/all")
    public ResponseEntity<List<Booking>> getAllBookings() {
        return ResponseEntity.ok(bookingRepository.findAll());
    }
}
