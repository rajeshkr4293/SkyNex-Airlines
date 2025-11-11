package com.skynex.airlines.paymentservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Entity
@Table(name = "payments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // reference to booking-service booking id
    @Column(nullable = false)
    private Long bookingId;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    private String status; // e.g. "PAID", "FAILED", "PENDING"

    @Column(nullable = false)
    private OffsetDateTime paymentTime;

    @Column
    private String method; // e.g. "CARD", "UPI", "NETBANKING"

    @Column(unique = true)
    private String transactionId;
}
