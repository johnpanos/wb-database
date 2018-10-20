package com.team3256.database.controller.hr;

import com.team3256.database.error.DatabaseNotFoundException;
import com.team3256.database.model.hr.User;
import com.team3256.database.model.hr.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/hr/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Secured("ROLE_ADMIN")
    @GetMapping("/")
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Secured("ROLE_USER")
    @GetMapping("/info")
    public User getUserInfo(@AuthenticationPrincipal Principal principal) {
        return userRepository.findByEmail(principal.getName()).orElseThrow(DatabaseNotFoundException::new);
    }

    @PutMapping(value = "/{id}")
    @Secured({ "ROLE_ADMIN", "ROLE_MENTOR" })
    public User editUser(@RequestBody User user, @PathVariable("id") Integer id) {
        if (!id.equals(user.getId())) {
            throw new DatabaseNotFoundException();
        }

        return userRepository.findById(id).map(dbUser -> {
            dbUser.setFirstName(user.getFirstName());
            dbUser.setMiddleName(user.getMiddleName());
            dbUser.setLastName(user.getLastName());
            dbUser.setCellPhone(user.getCellPhone());
            dbUser.setEmail(user.getEmail());
            dbUser.setBirthday(user.getBirthday());
            dbUser.setGender(user.getGender());
            return userRepository.save(dbUser);
        }).orElseThrow(DatabaseNotFoundException::new);
    }

    @PutMapping("/")
    @Secured({ "ROLE_USER" })
    public User editCurrentUser(@AuthenticationPrincipal Principal principal, @RequestBody User user) {
        return userRepository.findByEmail(principal.getName()).map(dbUser -> {
            dbUser.setFirstName(user.getFirstName());
            dbUser.setMiddleName(user.getMiddleName());
            dbUser.setLastName(user.getLastName());
            dbUser.setCellPhone(user.getCellPhone());
            dbUser.setEmail(user.getEmail());
            dbUser.setBirthday(user.getBirthday());
            dbUser.setGender(user.getGender());
            return userRepository.save(dbUser);
        }).orElseThrow(DatabaseNotFoundException::new);
    }

    @DeleteMapping(value = "/{id}")
    @Secured({ "ROLE_ADMIN", "ROLE_MENTOR" })
    public Integer deleteUser(@PathVariable Integer id) {
        userRepository.deleteById(id);
        return id;
    }
}
