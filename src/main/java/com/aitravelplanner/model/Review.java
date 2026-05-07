package com.aitravelplanner.model;

import java.time.Instant;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "reviews")
public class Review {
    @Id
    private String id;
    private String userId;
    private String destination;
    private int rating;
    private String comment;
    private Instant createdAt = Instant.now();
}
