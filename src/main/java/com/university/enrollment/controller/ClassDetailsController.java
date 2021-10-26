package com.university.enrollment.controller;

import com.university.enrollment.exception.StudentClassNotFoundException;
import com.university.enrollment.model.domain.ClassDetailsRequest;
import com.university.enrollment.model.entities.ClassDetails;
import com.university.enrollment.service.ClassDetailsService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Class create/ update controller for university application
 * @author murali
 */

@Slf4j
@RestController
@RequestMapping("/class")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
@Api(value = "Student Class Details Rest EndPoint", tags = "Student Class Details")
public class ClassDetailsController {

    private ClassDetailsService classDetailsService;

    public ClassDetailsController(ClassDetailsService classDetailsService) {
        this.classDetailsService = classDetailsService;
    }

    @PostMapping("/create")
    public ResponseEntity createClassDetails(@RequestBody ClassDetailsRequest request) {
        log.info("CREATION OF CLASS DETAILS - {}", request);
        ClassDetails classDetails = classDetailsService.createClassDetails(request);
        return ResponseEntity.ok(classDetails);
    }

    @PutMapping("/update/{classId}")
    public ResponseEntity updateClassDetails(@RequestBody ClassDetailsRequest request, @PathVariable int classId) {
        if (classId == 0) {
            throw new StudentClassNotFoundException();
        }
        log.info("MODIFYING OF CLASS DETAILS - {}", request);
        ClassDetails classDetails = classDetailsService.modifyClassDetails(request, classId);
        return ResponseEntity.ok(classDetails);
    }

}
