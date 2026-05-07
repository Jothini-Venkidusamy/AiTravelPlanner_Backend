package com.aitravelplanner.service;

import com.aitravelplanner.dto.RecommendationResponse;
import com.aitravelplanner.model.Destination;
import com.aitravelplanner.model.Preference;
import com.aitravelplanner.model.User;
import com.aitravelplanner.repository.DestinationRepository;
import com.aitravelplanner.repository.PreferenceRepository;
import java.security.Principal;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Service;

@Service
public class RecommendationService {
    private final DestinationRepository destinations;
    private final PreferenceRepository preferences;
    private final UserService userService;

    public RecommendationService(DestinationRepository destinations, PreferenceRepository preferences, UserService userService) {
        this.destinations = destinations;
        this.preferences = preferences;
        this.userService = userService;
    }

    public List<RecommendationResponse> personalized(Principal principal) {
        User user = userService.current(principal);
        List<Preference> history = preferences.findByUserIdOrderByCreatedAtDesc(user.getId());
        Set<String> interests = new LinkedHashSet<>(user.getPreferredInterests() == null ? List.of() : user.getPreferredInterests());
        history.stream().limit(5).flatMap(p -> p.getInterests().stream()).forEach(interests::add);
        if (interests.isEmpty()) interests.addAll(List.of("nature", "food", "culture"));

        return destinations.findAll().stream()
                .map(destination -> toRecommendation(destination, interests))
                .sorted(Comparator.comparing(RecommendationResponse::score).reversed())
                .limit(8)
                .toList();
    }

    private RecommendationResponse toRecommendation(Destination destination, Set<String> interests) {
        long overlap = destination.getInterests().stream().filter(interests::contains).count();
        int score = Math.min(98, 55 + (int) overlap * 12 + destination.getPopularityScore() / 10);
        String fit = destination.getAverageDailyCost() < 6000 ? "High" : destination.getAverageDailyCost() < 10000 ? "Medium" : "Premium";
        return new RecommendationResponse(
                destination.getName(),
                score,
                fit,
                destination.getInterests(),
                "Matches " + overlap + " preference categories with rating " + destination.getRating()
        );
    }
}
