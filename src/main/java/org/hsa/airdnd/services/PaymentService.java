package org.hsa.airdnd.services;

import org.hsa.airdnd.dto.BookingDto;
import org.hsa.airdnd.repositories.PaymentRepository;

import java.util.UUID;

public class PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public double pay(final BookingDto booking, final Double price) {
        if(booking.getTotalGuest() > 3) throw new IllegalArgumentException("Max 3 guest");

        String id = UUID.randomUUID().toString();

        this.paymentRepository.save(id, price);
        return this.paymentRepository.findById(id);
    }
}
