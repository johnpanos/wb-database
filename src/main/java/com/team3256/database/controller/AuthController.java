package com.team3256.database.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Arrays;
import java.util.Collection;

@RestController
public class AuthController {
    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test(Authentication authentication) {
        return authentication.getName();
    }
}
