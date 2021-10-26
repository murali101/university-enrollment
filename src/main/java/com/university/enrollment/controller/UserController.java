package com.university.enrollment.controller;

import com.university.enrollment.model.domain.UserDeleteRequest;
import com.university.enrollment.model.domain.UserRequest;
import com.university.enrollment.model.domain.UserUpdateRequest;
import com.university.enrollment.model.entities.User;
import com.university.enrollment.service.UserService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Student or Admin accounts create/ update controller for university application
 * @author murali
 */
@Slf4j
@RestController
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
@Api(value = "Student Details Rest EndPoint", tags = "Student Details")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * create student based on request
     * @param request
     * @return
     */
    @PostMapping("/user/create")
    public ResponseEntity createUser(@RequestBody UserRequest request) {
        log.info("USER CREATION REQUESTED @ - {}", request);
        User user = userService.createUser(request);
        if (user == null) {
            return ResponseEntity.badRequest().build();
        }
        user.setPassword(request.getPassword());
        return ResponseEntity.ok(user);
    }

    /**
     * update student based on request
     * @param request
     * @return
     */
    @PutMapping("/user/update")
    public ResponseEntity updateUser(@RequestBody UserUpdateRequest request) {
        log.info("USER UPDATE REQUESTED @ - {}", request);
        User user = userService.updateStudent(request);
        user.setPassword("");
        return ResponseEntity.ok(user);
    }

    /**
     * delete user based on request
     * @param request
     * @return
     */
    @DeleteMapping("/user")
    public ResponseEntity delteUser(@RequestBody UserDeleteRequest request) {
        log.info("USER UPDATE REQUESTED @ - {}", request);
        User user = userService.deleteStudent(request);
        user.setPassword("");
        return ResponseEntity.ok(user);
    }

    /**
     * fetch student based on class name
     * @param className
     * @return
     */
    @GetMapping(path = "/fetchStudents", params = "class")
    public ResponseEntity getClassStudents(@RequestParam(name = "class") String className) {
        log.info("FETCHING CLASS STUDENTS @ - {}", className);
        List<User> user = userService.getAllStudents(className);
        return ResponseEntity.ok(user);
    }

    /**
     * fetch student based on id
     * @param id
     * @return
     */
    @GetMapping(path = "/fetchStudents", params = "id")
    public ResponseEntity getStudent(@RequestParam(name = "id") Long id) {
        log.info("FETCHING STUDENT @ - {}", id);
        User user = userService.getStudent(id);
        return ResponseEntity.ok(user);
    }

}
