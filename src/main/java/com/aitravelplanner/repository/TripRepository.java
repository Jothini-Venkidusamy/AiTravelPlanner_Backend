package com.aitravelplanner.repository;

import com.aitravelplanner.model.Trip;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TripRepository extends MongoRepository<Trip, String> {
    List<Trip> findByUserIdOrderByCreatedAtDesc(String userId);
}
