package com.team3256.database.model.hr;

import com.team3256.database.error.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByEmail(s);

        logger.info("User (" + s + ") requesting authentication");

        org.springframework.security.core.userdetails.User.UserBuilder builder = null;
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            logger.info("User with ID: " + user.getId() + " (" + user.getEmail() + ") authenticated");
            builder = org.springframework.security.core.userdetails.User.withUsername(s);
            builder.password(user.getPassword());
            builder.roles(getRoles(user.getRoles()).toArray(new String[0]));
        } else {
            throw new UnauthorizedException("User not found");
        }

        return builder.build();
    }

    private List<String> getRoles(List<Role> roleList) {
        List<String> roleStringList = new ArrayList<>();
        if (roleList.size() > 0) {
            roleList.stream().forEach(role -> roleStringList.add(role.getName()));
        }
        logger.info("Setting roles for user");
        return roleStringList;
    }
}
