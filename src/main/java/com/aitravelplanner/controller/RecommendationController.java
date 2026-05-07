package com.aitravelplanner.controller;

import com.aitravelplanner.dto.RecommendationResponse;
import com.aitravelplanner.service.RecommendationService;
import java.security.Principal;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/recommendations")
public class RecommendationController {
    private final RecommendationService recommendationService;

    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @GetMapping
    public List<RecommendationResponse> all(Principal principal) {
        return recommendationService.personalized(principal);
    }
}
