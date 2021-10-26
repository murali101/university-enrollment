package com.university.enrollment.service;

import com.university.enrollment.model.domain.UserDeleteRequest;
import com.university.enrollment.model.domain.UserRequest;
import com.university.enrollment.model.domain.UserUpdateRequest;
import com.university.enrollment.model.entities.User;

import java.util.List;

public interface UserService {

    User createUser(UserRequest request);

    User updateStudent(UserUpdateRequest request);

    User deleteStudent(UserDeleteRequest request);

    List<User> getAllStudents(String className);

    User getStudent(Long id);
}
