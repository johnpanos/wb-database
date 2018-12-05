package com.team3256.database.service;

import com.team3256.database.error.DatabaseNotFoundException;
import com.team3256.database.model.hr.User;
import com.team3256.database.model.hr.UserRepository;
import com.team3256.database.model.hr.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public User getUserFromPrincipal(Principal principal) {
        return userRepository.findByEmail(principal.getName()).orElseThrow(DatabaseNotFoundException::new);
    }

    public List<User> getMentors() {
        return userRepository.findByType(UserType.MENTOR);
    }
}
