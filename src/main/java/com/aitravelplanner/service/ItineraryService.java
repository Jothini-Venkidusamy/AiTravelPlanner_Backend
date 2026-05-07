package com.aitravelplanner.service;

import com.aitravelplanner.dto.PreferenceRequest;
import com.aitravelplanner.model.Destination;
import com.aitravelplanner.model.ItineraryDay;
import com.aitravelplanner.model.Preference;
import com.aitravelplanner.model.Trip;
import com.aitravelplanner.model.User;
import com.aitravelplanner.repository.DestinationRepository;
import com.aitravelplanner.repository.PreferenceRepository;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import org.springframework.stereotype.Service;

@Service
public class ItineraryService {
    private final DestinationRepository destinations;
    private final PreferenceRepository preferences;
    private final UserService userService;

    public ItineraryService(DestinationRepository destinations, PreferenceRepository preferences, UserService userService) {
        this.destinations = destinations;
        this.preferences = preferences;
        this.userService = userService;
    }

    public Trip generate(PreferenceRequest request, java.security.Principal principal) {
        User user = userService.current(principal);
        Preference preference = savePreference(request, user.getId());
        Destination destination = destinations.findByNameIgnoreCase(request.destination()).orElseGet(() -> fallbackDestination(request));
        List<String> places = uniquePlaces(destination, request.interests());
        double cost = optimizeCost(request.budget(), destination.getAverageDailyCost() * request.days(), request.travelType());

        Trip trip = new Trip();
        trip.setUserId(user.getId());
        trip.setDestination(preference.getDestination());
        trip.setBudget(preference.getBudget());
        trip.setDays(preference.getDays());
        trip.setTravelType(preference.getTravelType());
        trip.setSeason(preference.getSeason());
        trip.setInterests(preference.getInterests());
        trip.setEstimatedCost(cost);
        trip.setRecommendedPlaces(places);
        trip.setMatchScore(score(destination, request, cost));
        trip.setSchedule(schedule(request, places, cost));
        return trip;
    }

    private Preference savePreference(PreferenceRequest request, String userId) {
        Preference preference = new Preference();
        preference.setUserId(userId);
        preference.setDestination(request.destination());
        preference.setBudget(request.budget());
        preference.setDays(request.days());
        preference.setTravelType(request.travelType());
        preference.setInterests(request.interests() == null ? List.of() : request.interests());
        preference.setSeason(request.season());
        return preferences.save(preference);
    }

    private Destination fallbackDestination(PreferenceRequest request) {
        Destination destination = new Destination();
        destination.setName(request.destination());
        destination.setCountry("India");
        destination.setInterests(request.interests() == null ? List.of("nature", "food") : request.interests());
        destination.setBestSeasons(List.of(request.season(), "winter"));
        destination.setAverageDailyCost(Math.max(3500, request.budget() / Math.max(1, request.days()) * 0.86));
        destination.setRating(4.4);
        destination.setPopularityScore(70);
        destination.setPlaces(List.of("Heritage Walk", "Local Food Trail", "Scenic Viewpoint", "Shopping Market", "Cultural Evening"));
        return destination;
    }

    private List<String> uniquePlaces(Destination destination, List<String> interests) {
        Set<String> places = new LinkedHashSet<>(destination.getPlaces());
        for (String interest : interests == null ? List.<String>of() : interests) {
            places.add(capitalize(interest) + " Experience");
        }
        return new ArrayList<>(places).stream().limit(8).toList();
    }

    private double optimizeCost(double budget, double projected, String travelType) {
        double multiplier = switch (travelType.toLowerCase(Locale.ROOT)) {
            case "family" -> 1.14;
            case "couple" -> 1.05;
            case "solo" -> 0.88;
            default -> 1.0;
        };
        return Math.round(Math.min(budget * 0.98, projected * multiplier));
    }

    private int score(Destination destination, PreferenceRequest request, double estimatedCost) {
        long overlap = request.interests() == null ? 0 : request.interests().stream()
                .filter(destination.getInterests()::contains)
                .count();
        int interestScore = request.interests() == null || request.interests().isEmpty() ? 20 : (int) (40.0 * overlap / request.interests().size());
        int seasonScore = destination.getBestSeasons().contains(request.season()) ? 25 : 12;
        int budgetScore = estimatedCost <= request.budget() ? 20 : 8;
        int popularityScore = Math.min(15, destination.getPopularityScore() / 7);
        return Math.min(98, interestScore + seasonScore + budgetScore + popularityScore);
    }

    private List<ItineraryDay> schedule(PreferenceRequest request, List<String> places, double cost) {
        List<ItineraryDay> days = new ArrayList<>();
        double perDay = Math.round(cost / Math.max(1, request.days()));
        for (int day = 1; day <= request.days(); day++) {
            String place = places.get((day - 1) % places.size());
            String interest = request.interests() == null || request.interests().isEmpty() ? "local" : request.interests().get((day - 1) % request.interests().size());
            days.add(new ItineraryDay(day, capitalize(interest) + " day", List.of(
                    "Morning visit to " + place,
                    "Afternoon " + interest + " activity with optimized route plan",
                    "Evening dining and leisure block"
            ), perDay));
        }
        return days;
    }

    private String capitalize(String value) {
        if (value == null || value.isBlank()) return "Travel";
        return value.substring(0, 1).toUpperCase(Locale.ROOT) + value.substring(1).toLowerCase(Locale.ROOT);
    }
}
