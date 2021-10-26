package com.university.enrollment.startup;

import com.university.enrollment.config.UniversityConfig;
import com.university.enrollment.model.entities.RoleTypes;
import com.university.enrollment.model.entities.User;
import com.university.enrollment.model.entities.UserTypes;
import com.university.enrollment.repository.UserRepository;
import com.university.enrollment.repository.UserTypesrepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;

@Slf4j
@Component
public class UniversityServerStartupProcessor {

    private UniversityConfig universityConfig;

    private UserTypesrepository userTypesrepository;

    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public UniversityServerStartupProcessor(UniversityConfig universityConfig, UserTypesrepository userTypesrepository,
                                            UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.universityConfig = universityConfig;
        this.userTypesrepository = userTypesrepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void createUserTypes() {
        log.info("LOADING USER TYPES START- {}", LocalDateTime.now());
        for (String name: universityConfig.getUserTypes()) {
            UserTypes userTypes = new UserTypes();
            userTypes.setName(name);
            UserTypes userType = userTypesrepository.save(userTypes);
            log.info("USER TYPE CREATED ==> {}", userType);
        }
        log.info("LOADING USER TYPES END- {}", LocalDateTime.now());

        log.info("CREATING ADMIN ACCOUNT FOR UNIVERSITY");

        if (userRepository.findByUserName("admin") == null) {
            User user = new User();
            user.setUserName("admin");
            user.setFirstName("admin user");
            user.setPassword(passwordEncoder.encode("admin"));
            user.setCreatedTime(LocalDateTime.now());
            user.setRole(RoleTypes.ROLE_ADMIN);
            User u2 = userRepository.save(user);
            log.info("USER CREATED - {}", u2);
        }

    }
}
