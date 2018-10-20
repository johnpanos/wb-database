package com.team3256.database;

import com.team3256.database.model.hr.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

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
        Optional<Role> adminRoleOptional = roleRepository.findByName("ADMIN");
        Optional<Role> mentorRoleOptional = roleRepository.findByName("MENTOR");
        Optional<Role> userRoleOptional = roleRepository.findByName("USER");

        if (!adminRoleOptional.isPresent()) {
            Role role = new Role();
            role.setName("ADMIN");
            roleRepository.save(role);
        }

        if (!mentorRoleOptional.isPresent()) {
            Role role = new Role();
            role.setName("MENTOR");
            roleRepository.save(role);
        }

        if (!userRoleOptional.isPresent()) {
            Role role = new Role();
            role.setName("USER");
            roleRepository.save(role);
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