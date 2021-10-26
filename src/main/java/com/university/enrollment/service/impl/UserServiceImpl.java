package com.university.enrollment.service.impl;

import com.university.enrollment.exception.MaxCreditsException;
import com.university.enrollment.exception.SemesterNotFoundException;
import com.university.enrollment.exception.StudentClassNotFoundException;
import com.university.enrollment.exception.StudentNotFoundException;
import com.university.enrollment.exception.UserExistsException;
import com.university.enrollment.model.domain.UserDeleteRequest;
import com.university.enrollment.model.domain.UserRequest;
import com.university.enrollment.model.domain.UserUpdateRequest;
import com.university.enrollment.model.entities.ClassDetails;
import com.university.enrollment.model.entities.Semester;
import com.university.enrollment.model.entities.User;
import com.university.enrollment.repository.ClassDetailsRepository;
import com.university.enrollment.repository.SemesterRepository;
import com.university.enrollment.repository.UserRepository;
import com.university.enrollment.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Service layer for all user/student details CRUD operations
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private ClassDetailsRepository classDetailsRepository;
    private SemesterRepository semesterRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ClassDetailsRepository classDetailsRepository,
                           SemesterRepository semesterRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.classDetailsRepository = classDetailsRepository;
        this.semesterRepository = semesterRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * create user/ student
     * @param request
     * @return
     */
    @Override
    public User createUser(UserRequest request) {
        User user2 = userRepository.findByUserName(request.getUserName());
        if (user2 != null) throw new UserExistsException();

        Semester semester = null;
        if (StringUtils.isNotEmpty(request.getSemester())) {
            semester = semesterRepository.findByName(request.getSemester());
            if (semester == null) throw new SemesterNotFoundException();
        }

        Set<ClassDetails> classDetailsList = getClassDetails(request.getClassName());
        User user = new User();
        user.setUserName(request.getUserName());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        if (semester != null) {
            user.setSemester(semester);
        }
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setCreatedTime(LocalDateTime.now());
        user.setRole(request.getRole());
        user.setClassDetails(classDetailsList);
        user.setNationality(request.getNationality());
        return userRepository.save(user);
    }

    /**
     * update student/ user
     * @param request
     * @return
     */
    @Override
    public User updateStudent(UserUpdateRequest request) {
        Optional<User> user2 = userRepository.findById(request.getId());
        if (user2.isPresent()) {
            User user = user2.get();
            Set<ClassDetails> classDetailsList = getClassDetails(request.getClassName());
            user.setClassDetails(classDetailsList);

            if (StringUtils.isNotEmpty(request.getSemester())) {
                Semester semester = semesterRepository.findByName(request.getSemester());
                if (semester == null) throw new SemesterNotFoundException();
                user.setSemester(semester);
            }
            return userRepository.save(user);
        }
        throw new StudentNotFoundException();
    }

    /**
     * delete student/ user
     * @param request
     * @return
     */
    @Override
    public User deleteStudent(UserDeleteRequest request) {
        Optional<User> user2 = userRepository.findById(request.getId());
        if (user2.isPresent()) {
            User user = user2.get();
            userRepository.delete(user);
            return user;
        }
        throw new StudentNotFoundException();

    }

    /**
     * get all students based on class name
     * @param className
     * @return
     */
    @Override
    public List<User> getAllStudents(String className) {
        ClassDetails classDetails = classDetailsRepository.findByName(className.trim());
        if (classDetails == null) throw new StudentClassNotFoundException();
        List<User> userList = userRepository.findAllByClassName(className);
        List<User> userList2 = new ArrayList<>();
        for (User user: userList) {
            user.setPassword(null);
            userList2.add(user);
        }
        return userList2;
    }

    /**
     * get student based on id
     * @param id
     * @return
     */
    @Override
    public User getStudent(Long id) {
        Optional<User> user2 = userRepository.findById(id);
        if (user2.isPresent()) {
            User user = user2.get();
            user.setPassword(null);
            return user;
        }
        throw new StudentNotFoundException();
    }

    private Set<ClassDetails> getClassDetails(String className2) {
        int credits = 0;
        Set<ClassDetails> classDetailsList = new HashSet<>();
        for (String className : StringUtils.split(className2, ",")) {
            ClassDetails classDetails = classDetailsRepository.findByName(className.trim());
            if (classDetails == null) throw new StudentClassNotFoundException();
            classDetailsList.add(classDetails);
            credits += classDetails.getCredits();
            if (credits > 20) throw new MaxCreditsException();
        }
        return classDetailsList;
    }

}
