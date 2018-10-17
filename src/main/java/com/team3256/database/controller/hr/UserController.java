package com.team3256.database.controller.hr;

import com.team3256.database.error.DatabaseNotFoundException;
import com.team3256.database.model.hr.User;
import com.team3256.database.model.hr.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping("/api/hr/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Secured("ROLE_USER")
    @GetMapping("/info")
    public User getUserInfo(@AuthenticationPrincipal Principal principal) {
        Optional<User> userOptional = userRepository.findByEmail(principal.getName());

        if (!userOptional.isPresent()) {
            throw new DatabaseNotFoundException("no user found with email - " + principal.getName());
        }

        return userOptional.get();
    }
}
