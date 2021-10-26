package com.university.enrollment.security

import com.university.enrollment.model.entities.RoleTypes
import com.university.enrollment.model.entities.User
import com.university.enrollment.repository.UserRepository
import org.springframework.security.core.userdetails.UsernameNotFoundException
import spock.lang.Specification

class AuthenticationServiceSpec extends Specification {

    def userRepository = Mock(UserRepository)
    def authenticationService = new AuthenticationService(userRepository)


    def "loadUserByUsername" () {
        given:
        User user = new User()
        user.setUserName("loginUsername")
        user.setPassword("admin")
        user.setRole(RoleTypes.ROLE_ADMIN)
        userRepository.findByUserName(*_) >> user
        when:
        def response = authenticationService.loadUserByUsername("admin")
        then:
        response != null
    }

    def "loadUserByUsername - user not found" () {
        given:
        User user = new User()
        user.setUserName("loginUsername")
        user.setPassword("admin")
        user.setRole(RoleTypes.ROLE_ADMIN)
        userRepository.findByUserName(*_) >> null
        when:
        def response = authenticationService.loadUserByUsername("admin")
        then:
        thrown(UsernameNotFoundException.class)
    }
}
