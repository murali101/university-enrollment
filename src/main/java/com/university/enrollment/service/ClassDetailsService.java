package com.university.enrollment.service;

import com.university.enrollment.model.domain.ClassDetailsRequest;
import com.university.enrollment.model.entities.ClassDetails;

public interface ClassDetailsService {

    ClassDetails createClassDetails(ClassDetailsRequest ClassDetailsRequest);

    ClassDetails modifyClassDetails(ClassDetailsRequest ClassDetailsRequest, int classId);
}
