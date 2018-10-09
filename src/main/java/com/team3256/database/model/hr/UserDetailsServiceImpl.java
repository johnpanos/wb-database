package com.team3256.database.model.hr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByEmail(s);

        System.out.println(s);

        org.springframework.security.core.userdetails.User.UserBuilder builder = null;
        if (userOptional.isPresent()) {
            System.out.println("USER IS PRESENT");
            User user = userOptional.get();
            builder = org.springframework.security.core.userdetails.User.withUsername(s);
            builder.password(user.getPassword());
            builder.roles(getRoles(user.getRoles()).toArray(new String[0]));
        } else {
            throw new UsernameNotFoundException("User not found.");
        }

        return builder.build();
    }

    private List<String> getRoles(List<Role> roleList) {
        List<String> roleStringList = new ArrayList<>();
        if (roleList.size() > 0) {
            roleList.stream().forEach(role -> roleStringList.add(role.getName()));
        }
        return roleStringList;
    }
}
