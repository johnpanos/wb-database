package com.team3256.database.controller.hr;

import com.team3256.database.model.hr.Role;
import com.team3256.database.model.hr.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/hr/role")
public class RoleController {
    @Autowired
    private RoleRepository roleRepository;

    @GetMapping("/")
    public Iterable<Role> getAllRoles() {
        return roleRepository.findAll();
    }
}
