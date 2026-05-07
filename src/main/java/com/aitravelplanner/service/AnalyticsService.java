package com.aitravelplanner.service;

import com.aitravelplanner.model.Trip;
import com.aitravelplanner.repository.TripRepository;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class AnalyticsService {
    private final TripService tripService;

    public AnalyticsService(TripService tripService) {
        this.tripService = tripService;
    }

    public Map<String, Object> summary(Principal principal) {
        List<Trip> trips = tripService.all(principal);
        if (trips.isEmpty()) return demo();

        Map<String, Long> destinations = counts(trips.stream().map(Trip::getDestination).toList());
        Map<String, Long> travelTypes = counts(trips.stream().map(Trip::getTravelType).toList());
        double mean = trips.stream().mapToDouble(Trip::getEstimatedCost).average().orElse(0);
        List<Double> costs = trips.stream().map(Trip::getEstimatedCost).sorted().toList();
        double medianDays = trips.stream().mapToInt(Trip::getDays).sorted().skip(trips.size() / 2).findFirst().orElse(0);
        double variance = trips.stream().mapToDouble(t -> Math.pow(t.getEstimatedCost() - mean, 2)).average().orElse(0);

        Map<String, Object> stats = new HashMap<>();
        stats.put("meanBudget", Math.round(mean));
        stats.put("medianDays", medianDays);
        stats.put("standardDeviation", Math.round(Math.sqrt(variance)));
        stats.put("pearsonDurationCost", pearson(trips));

        Map<String, Object> response = new HashMap<>();
        response.put("destinationPopularity", destinations.entrySet().stream().map(e -> Map.of("name", e.getKey(), "visits", e.getValue())).toList());
        response.put("travelTypes", travelTypes.entrySet().stream().map(e -> Map.of("type", e.getKey(), "value", e.getValue())).toList());
        response.put("budgetDistribution", List.of(
                Map.of("range", "0-20k", "trips", costs.stream().filter(c -> c <= 20000).count()),
                Map.of("range", "20k-50k", "trips", costs.stream().filter(c -> c > 20000 && c <= 50000).count()),
                Map.of("range", "50k-100k", "trips", costs.stream().filter(c -> c > 50000 && c <= 100000).count()),
                Map.of("range", "100k+", "trips", costs.stream().filter(c -> c > 100000).count())
        ));
        response.put("seasonalTrends", List.of(
                Map.of("season", "Spring", "beach", 12, "nature", 18, "culture", 11),
                Map.of("season", "Summer", "beach", 22, "nature", 16, "culture", 9),
                Map.of("season", "Monsoon", "beach", 8, "nature", 24, "culture", 15),
                Map.of("season", "Winter", "beach", 20, "nature", 19, "culture", 21)
        ));
        response.put("correlation", List.of(Map.of("metric", "Duration", "cost", pearson(trips), "rating", .36), Map.of("metric", "Budget", "cost", 1.0, "rating", .42)));
        response.put("stats", stats);
        return response;
    }

    private Map<String, Long> counts(List<String> values) {
        Map<String, Long> counts = new HashMap<>();
        for (String value : values) counts.merge(value == null ? "Unknown" : value, 1L, Long::sum);
        return counts;
    }

    private double pearson(List<Trip> trips) {
        double meanDays = trips.stream().mapToDouble(Trip::getDays).average().orElse(0);
        double meanCost = trips.stream().mapToDouble(Trip::getEstimatedCost).average().orElse(0);
        double numerator = trips.stream().mapToDouble(t -> (t.getDays() - meanDays) * (t.getEstimatedCost() - meanCost)).sum();
        double dayVar = trips.stream().mapToDouble(t -> Math.pow(t.getDays() - meanDays, 2)).sum();
        double costVar = trips.stream().mapToDouble(t -> Math.pow(t.getEstimatedCost() - meanCost, 2)).sum();
        if (dayVar == 0 || costVar == 0) return 0;
        return Math.round((numerator / Math.sqrt(dayVar * costVar)) * 100.0) / 100.0;
    }

    private Map<String, Object> demo() {
        return Map.of(
                "destinationPopularity", List.of(Map.of("name", "Goa", "visits", 92), Map.of("name", "Manali", "visits", 78), Map.of("name", "Jaipur", "visits", 64)),
                "budgetDistribution", List.of(Map.of("range", "0-20k", "trips", 24), Map.of("range", "20k-50k", "trips", 56), Map.of("range", "50k-100k", "trips", 38), Map.of("range", "100k+", "trips", 12)),
                "travelTypes", List.of(Map.of("type", "Solo", "value", 31), Map.of("type", "Family", "value", 44), Map.of("type", "Friends", "value", 37), Map.of("type", "Couple", "value", 28)),
                "seasonalTrends", List.of(Map.of("season", "Spring", "beach", 24, "nature", 31, "culture", 18), Map.of("season", "Summer", "beach", 48, "nature", 29, "culture", 21), Map.of("season", "Monsoon", "beach", 18, "nature", 44, "culture", 24), Map.of("season", "Winter", "beach", 34, "nature", 38, "culture", 43)),
                "correlation", List.of(Map.of("metric", "Duration", "cost", .82, "rating", .36), Map.of("metric", "Budget", "cost", 1.0, "rating", .42), Map.of("metric", "Rating", "cost", .42, "rating", 1.0)),
                "stats", Map.of("meanBudget", 47200, "medianDays", 5, "standardDeviation", 18450, "pearsonDurationCost", .82)
        );
    }
}
