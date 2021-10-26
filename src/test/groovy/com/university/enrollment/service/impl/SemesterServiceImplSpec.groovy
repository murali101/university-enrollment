package com.university.enrollment.service.impl


import com.university.enrollment.exception.SemesterNotFoundException
import com.university.enrollment.model.domain.SemesterRequest
import com.university.enrollment.model.entities.Semester
import com.university.enrollment.repository.SemesterRepository
import spock.lang.Specification

import java.time.LocalDateTime

class SemesterServiceImplSpec extends Specification {

    def semesterRepository = Mock(SemesterRepository)

    SemesterServiceImpl semesterServiceImpl = new SemesterServiceImpl(semesterRepository)

    SemesterRequest request = new SemesterRequest()
    Semester semester = new Semester()

    def setup() {
        request.setName("Fall Semester")
        request.setDescription("Start on oct 2021")

        semester.setName("Fall Semester")
        semester.setDescription("Start on oct 2021")
        semester.setCreatedTime(LocalDateTime.now())
        semester.setUpdatedTime(LocalDateTime.now())
    }

    def "Create Semester - not present"() {
        given:
        semesterRepository.findByName(*_) >> null
        semesterRepository.save(*_) >> semester

        when:
        def response = semesterServiceImpl.createSemester(request)

        then:
        response.getName() == "Fall Semester"

    }

    def "Create Semester - present"() {
        given:
        semesterRepository.findByName(*_) >> semester
        semesterRepository.save(*_) >> semester

        when:
        def response = semesterServiceImpl.createSemester(request)

        then:
        response.getName() == "Fall Semester"
        response.getDescription() == "Start on oct 2021"

    }

    def "Update Semester - not present"() {
        given:
        semesterRepository.findById(*_) >> Optional.ofNullable(null)

        when:
        def response = semesterServiceImpl.updateSemester(request, 1)

        then:
        thrown(SemesterNotFoundException.class)

    }


    def "Update Semester - present"() {
        given:
        semesterRepository.findById(*_) >> Optional.of(semester)
        semesterRepository.save(*_) >> semester

        when:
        def response = semesterServiceImpl.updateSemester(request, 1)

        then:
        response.getName() == "Fall Semester"
        response.getDescription() == "Start on oct 2021"

    }
}
