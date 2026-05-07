package com.aitravelplanner.model;

import java.time.Instant;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "bookings")
public class Booking {
    @Id
    private String id;
    private String userId;
    private String tripId;
    private String bookingType;
    private double amount;
    private Instant bookedAt = Instant.now();
}
