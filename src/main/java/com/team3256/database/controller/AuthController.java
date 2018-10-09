package com.team3256.database.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test() {
        return "aye";
    }
}
