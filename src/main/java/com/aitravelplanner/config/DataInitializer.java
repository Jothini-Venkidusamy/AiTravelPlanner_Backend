package com.aitravelplanner.config;

import com.mongodb.MongoException;
import com.aitravelplanner.model.Destination;
import com.aitravelplanner.repository.DestinationRepository;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {
    private final DestinationRepository destinations;

    public DataInitializer(DestinationRepository destinations) {
        this.destinations = destinations;
    }

    @Override
    public void run(String... args) {
        try {
            if (destinations.count() > 0) return;
            destinations.saveAll(List.of(
                    destination("Goa", List.of("beach", "food", "adventure"), List.of("winter", "summer"), 6200, 4.6, 92, List.of("Baga Beach", "Fort Aguada", "Dudhsagar Falls", "Fontainhas", "Anjuna Market")),
                    destination("Manali", List.of("adventure", "nature", "culture"), List.of("winter", "spring"), 7200, 4.5, 78, List.of("Solang Valley", "Hadimba Temple", "Rohtang Pass", "Mall Road", "Old Manali")),
                    destination("Jaipur", List.of("culture", "shopping", "food"), List.of("winter", "spring"), 5400, 4.4, 64, List.of("Amber Fort", "Hawa Mahal", "City Palace", "Johari Bazaar", "Chokhi Dhani")),
                    destination("Kerala", List.of("nature", "food", "culture"), List.of("monsoon", "winter"), 6800, 4.7, 58, List.of("Alleppey Backwaters", "Munnar Tea Gardens", "Fort Kochi", "Varkala Cliff", "Periyar Sanctuary")),
                    destination("Dubai", List.of("shopping", "adventure", "food"), List.of("winter"), 14500, 4.5, 47, List.of("Burj Khalifa", "Dubai Mall", "Desert Safari", "Marina Walk", "Gold Souk"))
            ));
        } catch (MongoException | DataAccessException ex) {
            System.err.println("MongoDB seed skipped because Atlas is not reachable: " + ex.getMessage());
        }
    }

    private Destination destination(String name, List<String> interests, List<String> seasons, double cost, double rating, int popularity, List<String> places) {
        Destination destination = new Destination();
        destination.setName(name);
        destination.setCountry(name.equals("Dubai") ? "UAE" : "India");
        destination.setInterests(interests);
        destination.setBestSeasons(seasons);
        destination.setAverageDailyCost(cost);
        destination.setRating(rating);
        destination.setPopularityScore(popularity);
        destination.setPlaces(places);
        return destination;
    }
}
