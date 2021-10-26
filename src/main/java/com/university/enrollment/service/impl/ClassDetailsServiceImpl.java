package com.university.enrollment.service.impl;

import com.university.enrollment.exception.StudentClassNotFoundException;
import com.university.enrollment.model.domain.ClassDetailsRequest;
import com.university.enrollment.model.entities.ClassDetails;
import com.university.enrollment.repository.ClassDetailsRepository;
import com.university.enrollment.service.ClassDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service layer for all class details CRUD operations
 */

@Slf4j
@Service
public class ClassDetailsServiceImpl implements ClassDetailsService {

    private ClassDetailsRepository classDetailsRepository;

    public ClassDetailsServiceImpl(ClassDetailsRepository classDetailsRepository) {
        this.classDetailsRepository = classDetailsRepository;
    }

    /**
     * create class
     * @param request
     * @return
     */
    @Override
    public ClassDetails createClassDetails(ClassDetailsRequest request) {
        ClassDetails details = classDetailsRepository.findByName(request.getName());
        if (details == null) {
            ClassDetails classDetails = new ClassDetails();
            return saveClassDetails(request, classDetails);
        }
        log.info("CLASS DETAILS EXISTS IN THE SYSTEM - {}" , details);
        return details;
    }

    private ClassDetails saveClassDetails(ClassDetailsRequest request, ClassDetails classDetails) {
        classDetails.setName(request.getName());
        classDetails.setCredits(request.getCredits());
        classDetails.setDescription(request.getDescription());
        return classDetailsRepository.save(classDetails);
    }

    /**
     * update class
     * @param request
     * @param classId
     * @return
     */
    @Override
    public ClassDetails modifyClassDetails(ClassDetailsRequest request, int classId) {
        Optional<ClassDetails> details = classDetailsRepository.findById(classId);
        if (details.isPresent()) {
            ClassDetails details2 = details.get();
            return saveClassDetails(request, details2);
        }
        log.info("CLASS DETAILS NOT EXISTS IN THE SYSTEM - {}" , classId);
        throw new StudentClassNotFoundException();
    }
}
