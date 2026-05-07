package com.aitravelplanner.repository;

import com.aitravelplanner.model.Preference;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PreferenceRepository extends MongoRepository<Preference, String> {
    List<Preference> findByUserIdOrderByCreatedAtDesc(String userId);
}
