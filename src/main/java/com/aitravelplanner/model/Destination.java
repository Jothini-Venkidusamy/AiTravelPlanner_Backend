package com.aitravelplanner.model;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "destinations")
public class Destination {
    @Id
    private String id;
    private String name;
    private String country;
    private List<String> interests = new ArrayList<>();
    private List<String> bestSeasons = new ArrayList<>();
    private double averageDailyCost;
    private double rating;
    private int popularityScore;
    private List<String> places = new ArrayList<>();

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }
    public List<String> getInterests() { return interests; }
    public void setInterests(List<String> interests) { this.interests = interests; }
    public List<String> getBestSeasons() { return bestSeasons; }
    public void setBestSeasons(List<String> bestSeasons) { this.bestSeasons = bestSeasons; }
    public double getAverageDailyCost() { return averageDailyCost; }
    public void setAverageDailyCost(double averageDailyCost) { this.averageDailyCost = averageDailyCost; }
    public double getRating() { return rating; }
    public void setRating(double rating) { this.rating = rating; }
    public int getPopularityScore() { return popularityScore; }
    public void setPopularityScore(int popularityScore) { this.popularityScore = popularityScore; }
    public List<String> getPlaces() { return places; }
    public void setPlaces(List<String> places) { this.places = places; }
}
