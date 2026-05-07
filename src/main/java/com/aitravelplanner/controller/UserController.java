package com.aitravelplanner.controller;

import com.aitravelplanner.dto.UserResponse;
import com.aitravelplanner.model.User;
import com.aitravelplanner.service.UserService;
import java.security.Principal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public UserResponse me(Principal principal) {
        return UserResponse.from(userService.current(principal));
    }

    @PutMapping("/me")
    public UserResponse update(@RequestBody User user, Principal principal) {
        return userService.update(principal, user);
    }
}
