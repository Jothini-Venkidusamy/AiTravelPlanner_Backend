package com.aitravelplanner.service;

import com.aitravelplanner.model.Trip;
import com.aitravelplanner.model.User;
import com.aitravelplanner.repository.TripRepository;
import java.security.Principal;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class TripService {
    private final TripRepository trips;
    private final UserService userService;

    public TripService(TripRepository trips, UserService userService) {
        this.trips = trips;
        this.userService = userService;
    }

    public List<Trip> all(Principal principal) {
        User user = userService.current(principal);
        return trips.findByUserIdOrderByCreatedAtDesc(user.getId());
    }

    public Trip save(Trip trip, Principal principal) {
        User user = userService.current(principal);
        trip.setId(null);
        trip.setUserId(user.getId());
        return trips.save(trip);
    }

    public void delete(String id, Principal principal) {
        User user = userService.current(principal);
        trips.findById(id).filter(trip -> user.getId().equals(trip.getUserId())).ifPresent(trips::delete);
    }
}
