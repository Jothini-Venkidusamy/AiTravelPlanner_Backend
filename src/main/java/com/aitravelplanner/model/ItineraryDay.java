package com.aitravelplanner.model;

import java.util.ArrayList;
import java.util.List;

public class ItineraryDay {
    private int day;
    private String theme;
    private List<String> activities = new ArrayList<>();
    private double estimatedExpense;

    public ItineraryDay() {}

    public ItineraryDay(int day, String theme, List<String> activities, double estimatedExpense) {
        this.day = day;
        this.theme = theme;
        this.activities = activities;
        this.estimatedExpense = estimatedExpense;
    }

    public int getDay() { return day; }
    public void setDay(int day) { this.day = day; }
    public String getTheme() { return theme; }
    public void setTheme(String theme) { this.theme = theme; }
    public List<String> getActivities() { return activities; }
    public void setActivities(List<String> activities) { this.activities = activities; }
    public double getEstimatedExpense() { return estimatedExpense; }
    public void setEstimatedExpense(double estimatedExpense) { this.estimatedExpense = estimatedExpense; }
}
