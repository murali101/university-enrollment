package com.university.enrollment.service;

import com.university.enrollment.model.domain.SemesterRequest;
import com.university.enrollment.model.entities.Semester;

public interface SemesterService {

    Semester createSemester(SemesterRequest semesterRequest);

    Semester updateSemester(SemesterRequest semesterRequest, int semesterId);

}
