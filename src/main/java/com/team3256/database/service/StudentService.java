package com.team3256.database.service;

import com.team3256.database.model.hr.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

@Service
public class StudentService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private RoleRepository roleRepository;

    public User create(
            String firstName,
            String middleName,
            String lastName,
            String cellPhone,
            String email,
            String password,
            Date birthday,
            UserGender gender,
            String backupEmail,
            int grade,
            int powerSchoolId,
            String shirtSize,
            String poloSize,
            boolean fll,
            boolean ftc,
            boolean frc
    ) {

        Optional<Student> student = studentRepository.findByPowerSchoolId(powerSchoolId);

        if (student.isPresent()) {
            return null;
        }

        User dbUser = new User();

        dbUser.setFirstName(firstName);
        dbUser.setMiddleName(middleName);
        dbUser.setLastName(lastName);
        dbUser.setCellPhone(cellPhone);
        dbUser.setEmail(email);
        dbUser.setPassword(new BCryptPasswordEncoder().encode(password));
        dbUser.setBirthday(birthday);
        dbUser.setGender(gender);
        dbUser.setType(UserType.STUDENT);

        Role studentRole = roleRepository.findByName("STUDENT").get();
        Role userRole = roleRepository.findByName("USER").get();

        dbUser.setRoles(Arrays.asList(studentRole, userRole));

        Student dbStudent = new Student();

        dbStudent.setBackupEmail(backupEmail);
        dbStudent.setGrade(grade);
        dbStudent.setPowerSchoolId(powerSchoolId);
        dbStudent.setShirtSize(shirtSize);
        dbStudent.setPoloSize(poloSize);
        dbStudent.setInFLL(fll);
        dbStudent.setInFTC(ftc);
        dbStudent.setInFRC(frc);
        dbStudent.setUser(dbUser);

        dbUser.setStudent(dbStudent);

        return userRepository.save(dbUser);
    }
}
