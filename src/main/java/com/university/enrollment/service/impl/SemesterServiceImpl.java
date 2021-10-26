package com.university.enrollment.service.impl;

import com.university.enrollment.exception.SemesterNotFoundException;
import com.university.enrollment.model.domain.SemesterRequest;
import com.university.enrollment.model.entities.Semester;
import com.university.enrollment.repository.SemesterRepository;
import com.university.enrollment.service.SemesterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Service layer for all semester CRUD operations
 */

@Slf4j
@Service
public class SemesterServiceImpl implements SemesterService {

    private SemesterRepository semesterRepository;

    public SemesterServiceImpl(SemesterRepository semesterRepository) {
        this.semesterRepository = semesterRepository;
    }

    /**
     * create semester
     * @param semesterRequest
     * @return
     */
    @Override
    public Semester createSemester(SemesterRequest semesterRequest) {
        Semester semester = semesterRepository.findByName(semesterRequest.getName());
        if (semester == null) {
            Semester semester2 = new Semester();
            semester2.setName(semesterRequest.getName());
            semester2.setDescription(semesterRequest.getDescription());
            semester2.setCreatedTime(LocalDateTime.now());
            semester2.setUpdatedTime(LocalDateTime.now());
            return semesterRepository.save(semester2);
        }
        log.info("SEMESTER DETAILS EXISTS IN THE SYSTEM - {}" , semester);
        return semester;
    }

    /**
     * update semester
     * @param semesterRequest
     * @param semesterId
     * @return
     */
    @Override
    public Semester updateSemester(SemesterRequest semesterRequest, int semesterId) {
        Optional<Semester> semester = semesterRepository.findById(semesterId);
        if (semester.isPresent()) {
            Semester semester2 = semester.get();
            semester2.setName(semesterRequest.getName());
            semester2.setDescription(semesterRequest.getDescription());
            semester2.setUpdatedTime(LocalDateTime.now());
            return semesterRepository.save(semester2);
        }
        log.info("SEMESTER DETAILS NOT EXISTS IN THE SYSTEM - {}" , semesterId);
        throw new SemesterNotFoundException();
    }
}
