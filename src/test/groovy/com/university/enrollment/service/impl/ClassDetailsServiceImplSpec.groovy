package com.university.enrollment.service.impl

import com.university.enrollment.exception.StudentClassNotFoundException
import com.university.enrollment.model.domain.ClassDetailsRequest
import com.university.enrollment.model.entities.ClassDetails
import com.university.enrollment.repository.ClassDetailsRepository
import spock.lang.Specification

class ClassDetailsServiceImplSpec extends Specification {

    def classDetailsRepository = Mock(ClassDetailsRepository)

    ClassDetailsServiceImpl classDetailsService = new ClassDetailsServiceImpl(classDetailsRepository)

    def "createClassDetails - present"() {
        given:
        ClassDetailsRequest request = new ClassDetailsRequest()
        request.setName("3A")

        ClassDetails details = new ClassDetails()
        details.setName("3A")
        classDetailsRepository.findByName("3A") >> details

        when:
        def response = classDetailsService.createClassDetails(request)

        then:
        response.getName() == "3A"

    }

    def "createClassDetails - not present"() {
        given:
        ClassDetailsRequest request = new ClassDetailsRequest()
        request.setName("3A")
        request.setCredits(5)
        request.setDescription("description")
        classDetailsRepository.findByName(*_) >> null
        ClassDetails details = new ClassDetails()
        details.setName("3A")
        details.setCredits(5)
        details.setDescription("description")
        classDetailsRepository.save(*_) >> details

        when:
        def response = classDetailsService.createClassDetails(request)

        then:
        response.getName() == "3A"
        response.getCredits() == 5
        response.getDescription() == "description"

    }

    def "modify class details - not present"() {
        given:
        ClassDetailsRequest request = new ClassDetailsRequest()
        request.setName("3A")
        request.setCredits(5)
        request.setDescription("description")

        classDetailsRepository.findById(1) >> Optional.ofNullable(null)

        when:
        def response = classDetailsService.modifyClassDetails(request, 1)

        then:
        thrown(StudentClassNotFoundException.class)

    }


    def "modify class details - present"() {
        given:
        ClassDetailsRequest request = new ClassDetailsRequest()
        request.setName("3A")
        request.setCredits(5)
        request.setDescription("description")

        ClassDetails details = new ClassDetails()
        details.setName("3A")
        details.setCredits(5)
        details.setDescription("description")
        classDetailsRepository.findById(1) >> Optional.of(details)
        classDetailsRepository.save(*_) >> details

        when:
        def response = classDetailsService.modifyClassDetails(request, 1)

        then:
        response.getName() == "3A"
        response.getCredits() == 5
        response.getDescription() == "description"

    }

}
