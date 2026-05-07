package com.aitravelplanner.controller;

import com.aitravelplanner.model.Trip;
import com.aitravelplanner.service.TripService;
import java.security.Principal;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/trips")
public class TripController {
    private final TripService tripService;

    public TripController(TripService tripService) {
        this.tripService = tripService;
    }

    @GetMapping
    public List<Trip> all(Principal principal) {
        return tripService.all(principal);
    }

    @PostMapping
    public Trip save(@RequestBody Trip trip, Principal principal) {
        return tripService.save(trip, principal);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id, Principal principal) {
        tripService.delete(id, principal);
    }
}
