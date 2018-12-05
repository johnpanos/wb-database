package com.team3256.database;

import com.team3256.database.model.hr.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

@SpringBootApplication
public class DatabaseApplication {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

	public static void main(String[] args) throws Exception {
		SpringApplication.run(DatabaseApplication.class, args);
	}

    @EventListener(ApplicationReadyEvent.class)
    public void doSomethingAfterStartup() {
        ArrayList<String> roles = new ArrayList<>();

        roles.add("ADMIN");
        roles.add("STUDENT");
        roles.add("MENTOR");
        roles.add("USER");
        roles.add("INV_EDIT");
        roles.add("INV_QUANTITY");
        roles.add("PO_EDIT");
        roles.add("PO_VIEW");

        for (String role : roles) {
            Optional<Role> roleOptional = roleRepository.findByName(role);
            if (!roleOptional.isPresent()) {
                Role dbRole = new Role();
                dbRole.setName(role);
                roleRepository.save(dbRole);
            }
        }

        Role adminRole = roleRepository.findByName("ADMIN").get();
        Role userRole = roleRepository.findByName("USER").get();

        Optional<User> userOptional = userRepository.findByEmail("admin@mywb.vcs.net");

        if (!userOptional.isPresent()) {
            User user = new User();
            user.setEmail("admin@mywb.vcs.net");
            user.setType(UserType.ADMIN);
            user.setRoles(Arrays.asList(adminRole, userRole));
            user.setPassword(new BCryptPasswordEncoder().encode("r0b0tic$"));
            user.setFirstName("Admin");
            user.setMiddleName("N/A");
            user.setLastName("N/A");
            user.setCellPhone("N/A");
            user.setGender(UserGender.MALE);
            user.setBirthday(new Date());
            userRepository.save(user);
        }
    }
}