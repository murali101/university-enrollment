package com.university.enrollment.startup

import com.university.enrollment.config.UniversityConfig
import com.university.enrollment.model.entities.UserTypes
import com.university.enrollment.repository.UserRepository
import com.university.enrollment.repository.UserTypesrepository
import org.springframework.security.crypto.password.PasswordEncoder
import spock.lang.Specification

class UniversityServerStartupProcessorSpec extends Specification {

    def universityConfig = new UniversityConfig()
    def userTypesrepository = Mock(UserTypesrepository)
    def userRepository = Mock(UserRepository)
    def passwordEncoder = Mock(PasswordEncoder)

    def universityServerStartupProcessor = new UniversityServerStartupProcessor(universityConfig,
            userTypesrepository, userRepository, passwordEncoder)

    def setup() {
        List<String> list = new ArrayList<>()
        list.add("ADMIN")
        list.add("USER")
        universityConfig.setUserTypes(list)
        passwordEncoder.encode(*_) >> "Enc Password"
        userTypesrepository.save(*_) >> new UserTypes()
        userRepository.findByUserName(*_) >> null
    }

    def "universityServerStartupProcessor"() {
        when:
        universityServerStartupProcessor.createUserTypes()
        then:
        noExceptionThrown()
    }

}
