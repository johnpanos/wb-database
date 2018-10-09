package com.team3256.database;

import com.oracle.tools.packager.Log;
import com.team3256.database.model.Role;
import com.team3256.database.model.RoleRepository;
import com.team3256.database.model.User;
import com.team3256.database.model.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

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
        Optional<Role> roleOptional = roleRepository.findByName("ADMIN");

        if (!roleOptional.isPresent()) {
            Role role = new Role();
            role.setName("ADMIN");
            roleRepository.save(role);
        }

        Role role = roleRepository.findByName("ADMIN").get();

        Optional<User> userOptional = userRepository.findByEmail("admin@localhost");

        if (!userOptional.isPresent()) {
            User user = new User();
            user.setEmail("admin@localhost");
            user.setRoles(Arrays.asList(role));
            user.setPassword(new BCryptPasswordEncoder().encode("malibupanos"));
            userRepository.save(user);
            Log.info("CREATED ADMIN ACCOUNT");
        }
    }
}