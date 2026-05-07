package com.aitravelplanner.dto;

import com.aitravelplanner.model.User;
import java.util.List;

public record UserResponse(String id, String name, String email, String homeCity, String preferredCurrency, List<String> preferredInterests) {
    public static UserResponse from(User user) {
        return new UserResponse(user.getId(), user.getName(), user.getEmail(), user.getHomeCity(), user.getPreferredCurrency(), user.getPreferredInterests());
    }
}
