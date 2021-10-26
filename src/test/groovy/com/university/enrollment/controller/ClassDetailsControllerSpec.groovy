package com.university.enrollment.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.university.enrollment.model.domain.ClassDetailsRequest
import com.university.enrollment.model.entities.ClassDetails
import com.university.enrollment.security.AuthenticationService
import com.university.enrollment.service.ClassDetailsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import spock.lang.Specification
import spock.mock.DetachedMockFactory

@AutoConfigureMockMvc
@WebMvcTest(ClassDetailsController.class)
@ActiveProfiles("test")
class ClassDetailsControllerSpec extends Specification{

    @Autowired
    private MockMvc mockMvc

    @Autowired
    ClassDetailsService classDetailsService

    def "/class/create class with ClassDetailsRequest with all valid params - No Authentication"() {
        given:
        ClassDetailsRequest request = new ClassDetailsRequest()
        request.setName("3A")
        request.setCredits(5)
        request.setDescription("3A Class")
        and:
        ClassDetails details = new ClassDetails()
        details.setId(1)
        details.setName("3A")
        details.setCredits(5)
        details.setDescription("3A Class")
        classDetailsService.createClassDetails(*_) >> details

        when:
        def response = mockMvc.perform(MockMvcRequestBuilders.post('/class/create').content(asJsonString(request)))

        then:
        response != null
        response.andExpect(MockMvcResultMatchers.status().is(401))
    }

    @TestConfiguration
    static class StubConfig {
        DetachedMockFactory detachedMockFactory = new DetachedMockFactory()

        @Bean
        ClassDetailsService classDetailsService() {
            return detachedMockFactory.Stub(ClassDetailsService)
        }

        @Bean
        AuthenticationService authenticationService() {
            return detachedMockFactory.Stub(AuthenticationService)
        }

    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }




}
