package com.aitravelplanner.service;

import com.aitravelplanner.dto.UserResponse;
import com.aitravelplanner.model.User;
import com.aitravelplanner.repository.UserRepository;
import java.security.Principal;
import java.util.ArrayList;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository users;

    public UserService(UserRepository users) {
        this.users = users;
    }

    public User current(Principal principal) {
        return users.findByEmail(principal.getName()).orElseThrow();
    }

    public UserResponse update(Principal principal, User patch) {
        User user = current(principal);
        if (patch.getName() != null && !patch.getName().isBlank()) {
            user.setName(patch.getName());
        }
        user.setHomeCity(patch.getHomeCity());
        if (patch.getPreferredCurrency() != null && !patch.getPreferredCurrency().isBlank()) {
            user.setPreferredCurrency(patch.getPreferredCurrency());
        }
        user.setPreferredInterests(patch.getPreferredInterests() == null ? new ArrayList<>() : patch.getPreferredInterests());
        return UserResponse.from(users.save(user));
    }
}
