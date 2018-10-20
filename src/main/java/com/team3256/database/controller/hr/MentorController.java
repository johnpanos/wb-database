package com.team3256.database.controller.hr;

import com.team3256.database.error.DatabaseNotFoundException;
import com.team3256.database.model.hr.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/hr/mentor")
public class MentorController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @GetMapping(value = "/")
    @Secured({ "ROLE_ADMIN", "ROLE_MENTOR" })
    private List<User> getMentors() {
        return userRepository.findByType(UserType.MENTOR);
    }

    @PostMapping(value = "/")
    @Secured({ "ROLE_ADMIN", "ROLE_MENTOR" })
    private User createMentor(@RequestBody User mentor) {
        User dbMentor = new User();

        dbMentor.setFirstName(mentor.getFirstName());
        dbMentor.setMiddleName(mentor.getMiddleName());
        dbMentor.setLastName(mentor.getLastName());
        dbMentor.setCellPhone(mentor.getCellPhone());
        dbMentor.setEmail(mentor.getEmail());
        dbMentor.setPassword(new BCryptPasswordEncoder().encode(mentor.getPassword()));
        dbMentor.setBirthday(mentor.getBirthday());
        dbMentor.setGender(mentor.getGender());
        dbMentor.setType(UserType.MENTOR);

        Role mentorRole = roleRepository.findByName("MENTOR").get();
        Role userRole = roleRepository.findByName("USER").get();

        dbMentor.setRoles(Arrays.asList(mentorRole, userRole));

        return userRepository.save(dbMentor);
    }

}
