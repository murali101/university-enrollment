package com.university.enrollment.controller;

import com.university.enrollment.model.domain.SemesterRequest;
import com.university.enrollment.model.entities.Semester;
import com.university.enrollment.service.SemesterService;
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

import java.time.LocalDateTime;

/**
 * Semester create/ update controller for university application
 * @author murali
 */

@Slf4j
@RestController
@RequestMapping("/semester")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
@Api(value = "Semester Details Rest EndPoint", tags = "Semester Details")
public class SemesterController {

    private SemesterService semesterService;

    public SemesterController(SemesterService semesterService) {
        this.semesterService = semesterService;
    }

    @PostMapping("/create")
    public ResponseEntity createSemester(@RequestBody SemesterRequest request) {
        log.info("SEMESTER CREATION REQUESTED @ - {}", LocalDateTime.now());
        Semester semester = semesterService.createSemester(request);
        return ResponseEntity.ok(semester);
    }

    @PutMapping("/update/{semesterId}")
    public ResponseEntity updateSemester(@RequestBody SemesterRequest request, @PathVariable int semesterId) {

        if (semesterId == 0) {
            return ResponseEntity.badRequest().build();
        }

        log.info("SEMESTER CREATION REQUESTED @ - {}", LocalDateTime.now());
        Semester semester = semesterService.updateSemester(request, semesterId);
        return ResponseEntity.ok(semester);
    }

}
