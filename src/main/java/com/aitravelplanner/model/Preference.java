package com.aitravelplanner.model;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "preferences")
public class Preference {
    @Id
    private String id;
    private String userId;
    private String destination;
    private double budget;
    private int days;
    private String travelType;
    private List<String> interests = new ArrayList<>();
    private String season;
    private Instant createdAt = Instant.now();

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }
    public double getBudget() { return budget; }
    public void setBudget(double budget) { this.budget = budget; }
    public int getDays() { return days; }
    public void setDays(int days) { this.days = days; }
    public String getTravelType() { return travelType; }
    public void setTravelType(String travelType) { this.travelType = travelType; }
    public List<String> getInterests() { return interests; }
    public void setInterests(List<String> interests) { this.interests = interests; }
    public String getSeason() { return season; }
    public void setSeason(String season) { this.season = season; }
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
}
