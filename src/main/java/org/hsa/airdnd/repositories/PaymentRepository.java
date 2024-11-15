package org.hsa.airdnd.repositories;

import java.util.HashMap;
import java.util.Map;

public class PaymentRepository {

    private final Map<String, Double> payData = new HashMap<>();

    public void save(final String id, final Double booking) {
        this.payData.put(id, booking);
    }

    public double findById(final String id) {
        return this.payData.get(id);
    }
}