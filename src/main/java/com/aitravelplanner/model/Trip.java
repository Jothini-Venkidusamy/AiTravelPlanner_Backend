package com.aitravelplanner.model;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "trips")
public class Trip {
    @Id
    private String id;
    private String userId;
    private String destination;
    private int days;
    private double budget;
    private double estimatedCost;
    private String travelType;
    private String season;
    private int matchScore;
    private List<String> interests = new ArrayList<>();
    private List<String> recommendedPlaces = new ArrayList<>();
    private List<ItineraryDay> schedule = new ArrayList<>();
    private Instant createdAt = Instant.now();

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }
    public int getDays() { return days; }
    public void setDays(int days) { this.days = days; }
    public double getBudget() { return budget; }
    public void setBudget(double budget) { this.budget = budget; }
    public double getEstimatedCost() { return estimatedCost; }
    public void setEstimatedCost(double estimatedCost) { this.estimatedCost = estimatedCost; }
    public String getTravelType() { return travelType; }
    public void setTravelType(String travelType) { this.travelType = travelType; }
    public String getSeason() { return season; }
    public void setSeason(String season) { this.season = season; }
    public int getMatchScore() { return matchScore; }
    public void setMatchScore(int matchScore) { this.matchScore = matchScore; }
    public List<String> getInterests() { return interests; }
    public void setInterests(List<String> interests) { this.interests = interests; }
    public List<String> getRecommendedPlaces() { return recommendedPlaces; }
    public void setRecommendedPlaces(List<String> recommendedPlaces) { this.recommendedPlaces = recommendedPlaces; }
    public List<ItineraryDay> getSchedule() { return schedule; }
    public void setSchedule(List<ItineraryDay> schedule) { this.schedule = schedule; }
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
}
