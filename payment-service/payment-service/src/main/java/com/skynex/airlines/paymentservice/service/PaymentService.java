package com.skynex.airlines.paymentservice.service;

import com.skynex.airlines.paymentservice.client.BookingClient;
import com.skynex.airlines.paymentservice.model.Payment;
import com.skynex.airlines.paymentservice.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private BookingClient bookingClient;

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    public Optional<Payment> getPaymentById(Long id) {
        return paymentRepository.findById(id);
    }

    public Payment makePayment(Payment payment) {
        // 🔹 Fetch booking details using Feign (Booking-Service)
        Map<String, Object> bookingData = bookingClient.getBookingById(payment.getBookingId());

        if (bookingData == null || bookingData.isEmpty()) {
            throw new IllegalArgumentException("Booking not found with ID: " + payment.getBookingId());
        }

        // 🔹 Get total amount from booking-service
        Double totalAmount = ((Number) bookingData.get("totalPrice")).doubleValue();

        // 🔹 Generate unique transaction ID
        payment.setTransactionId(UUID.randomUUID().toString());
        payment.setAmount(totalAmount);
        payment.setPaymentTime(OffsetDateTime.now());
        payment.setStatus("PAID");

        return paymentRepository.save(payment);
    }

    public void cancelPayment(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Payment not found: " + id));
        payment.setStatus("FAILED");
        paymentRepository.save(payment);
    }
}
