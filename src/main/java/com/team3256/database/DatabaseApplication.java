package com.team3256.database;

import com.team3256.database.model.hr.Role;
import com.team3256.database.model.hr.RoleRepository;
import com.team3256.database.model.hr.User;
import com.team3256.database.model.hr.UserRepository;
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
        Optional<Role> userRoleOptional = roleRepository.findByName("USER");

        if (!adminRoleOptional.isPresent()) {
            Role role = new Role();
            role.setName("ADMIN");
            roleRepository.save(role);
        }

        if (!userRoleOptional.isPresent()) {
            Role role = new Role();
            role.setName("USER");
            roleRepository.save(role);
        }

        Role adminRole = roleRepository.findByName("ADMIN").get();
        Role userRole = roleRepository.findByName("USER").get();

        Optional<User> userOptional = userRepository.findByEmail("admin@localhost");

        if (!userOptional.isPresent()) {
            User user = new User();
            user.setEmail("admin@localhost");
            user.setRoles(Arrays.asList(adminRole, userRole));
            user.setPassword(new BCryptPasswordEncoder().encode("malibupanos"));
            userRepository.save(user);
            System.out.println("CREATED ADMIN ACCOUNT");
        }
    }
}