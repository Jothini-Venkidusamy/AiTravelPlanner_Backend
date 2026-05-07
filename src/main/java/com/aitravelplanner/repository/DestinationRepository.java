package com.aitravelplanner.repository;

import com.aitravelplanner.model.Destination;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DestinationRepository extends MongoRepository<Destination, String> {
    Optional<Destination> findByNameIgnoreCase(String name);
}
