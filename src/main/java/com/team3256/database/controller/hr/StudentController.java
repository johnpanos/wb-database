package com.team3256.database.controller.hr;

import com.team3256.database.error.DatabaseAlreadyExistsException;
import com.team3256.database.error.DatabaseNotFoundException;
import com.team3256.database.model.hr.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/hr/student")
public class StudentController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private RoleRepository roleRepository;

    @GetMapping("/")
    @Secured({ "ROLE_ADMIN", "ROLE_MENTOR" })
    public List<User> getAllStudents() {
        return userRepository.findByType(UserType.STUDENT);
    }

    @PostMapping("/")
    @Secured({ "ROLE_ADMIN", "ROLE_MENTOR" })
    public User createStudent(@RequestBody User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new DatabaseAlreadyExistsException("user with email already exists");
        }

        User dbUser = new User();

        dbUser.setFirstName(user.getFirstName());
        dbUser.setMiddleName(user.getMiddleName());
        dbUser.setLastName(user.getLastName());
        dbUser.setCellPhone(user.getCellPhone());
        dbUser.setEmail(user.getEmail());
        dbUser.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        dbUser.setBirthday(user.getBirthday());
        dbUser.setGender(user.getGender());
        dbUser.setType(UserType.STUDENT);

        Role studentRole = roleRepository.findByName("STUDENT").get();
        Role userRole = roleRepository.findByName("USER").get();

        dbUser.setRoles(Arrays.asList(studentRole, userRole));

        Student dbStudent = new Student();
        Student student = user.getStudent();

        dbStudent.setBackupEmail(student.getBackupEmail());
        dbStudent.setGrade(student.getGrade());
        dbStudent.setPowerSchoolId(student.getPowerSchoolId());
        dbStudent.setShirtSize(student.getShirtSize());
        dbStudent.setPoloSize(student.getPoloSize());
        dbStudent.setInFLL(student.isInFLL());
        dbStudent.setInFTC(student.isInFTC());
        dbStudent.setInFRC(student.isInFRC());
        dbStudent.setUser(dbUser);

        dbUser.setStudent(dbStudent);

        return userRepository.save(dbUser);
    }

    @PutMapping("/{id}")
    @Secured({ "ROLE_ADMIN", "ROLE_MENTOR" })
    public User updateStudent(@PathVariable Integer id, @RequestBody User user) {
        if (!id.equals(user.getId())) {
            throw new DatabaseNotFoundException();
        }

        User dbUser = userRepository.findById(id).orElseThrow(DatabaseNotFoundException::new);

        dbUser.setFirstName(user.getFirstName());
        dbUser.setMiddleName(user.getMiddleName());
        dbUser.setLastName(user.getLastName());
        dbUser.setCellPhone(user.getCellPhone());
        dbUser.setEmail(user.getEmail());
        dbUser.setBirthday(user.getBirthday());
        dbUser.setGender(user.getGender());

        Student dbStudent = dbUser.getStudent();
        Student student = user.getStudent();

        dbStudent.setBackupEmail(student.getBackupEmail());
        dbStudent.setGrade(student.getGrade());
        dbStudent.setPowerSchoolId(student.getPowerSchoolId());
        dbStudent.setShirtSize(student.getShirtSize());
        dbStudent.setPoloSize(student.getPoloSize());
        dbStudent.setInFLL(student.isInFLL());
        dbStudent.setInFTC(student.isInFTC());
        dbStudent.setInFRC(student.isInFRC());
        dbStudent.setUser(dbUser);

        dbUser.setStudent(dbStudent);

        return userRepository.save(dbUser);
    }

    @PutMapping("/{id}/role")
    @Secured({ "ROLE_ADMIN", "ROLE_MENTOR" })
    public User updateRoles(@PathVariable Integer id, @RequestBody List<Integer> roleList) {
        User user = userRepository.findById(id).orElseThrow(DatabaseNotFoundException::new);

        List<Role> roles = roleRepository.findByIdIn(roleList);

        user.setRoles(roles);

        return userRepository.save(user);
    }
}
