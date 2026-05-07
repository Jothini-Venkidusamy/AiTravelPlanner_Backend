package com.aitravelplanner.controller;

import com.aitravelplanner.dto.PreferenceRequest;
import com.aitravelplanner.model.Trip;
import com.aitravelplanner.service.ItineraryService;
import jakarta.validation.Valid;
import java.security.Principal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/itineraries")
public class ItineraryController {
    private final ItineraryService itineraryService;

    public ItineraryController(ItineraryService itineraryService) {
        this.itineraryService = itineraryService;
    }

    @PostMapping("/generate")
    public Trip generate(@Valid @RequestBody PreferenceRequest request, Principal principal) {
        return itineraryService.generate(request, principal);
    }
}
