package com.aitravelplanner.dto;

import java.util.List;

public record RecommendationResponse(String name, int score, String budgetFit, List<String> interests, String reason) {}
