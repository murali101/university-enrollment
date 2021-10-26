package com.university.enrollment.service.impl

import com.university.enrollment.exception.MaxCreditsException
import com.university.enrollment.exception.StudentClassNotFoundException
import com.university.enrollment.exception.SemesterNotFoundException
import com.university.enrollment.exception.StudentNotFoundException
import com.university.enrollment.exception.UserExistsException
import com.university.enrollment.model.domain.UserDeleteRequest
import com.university.enrollment.model.domain.UserRequest
import com.university.enrollment.model.domain.UserUpdateRequest
import com.university.enrollment.model.entities.ClassDetails
import com.university.enrollment.model.entities.RoleTypes
import com.university.enrollment.model.entities.Semester
import com.university.enrollment.model.entities.User
import com.university.enrollment.repository.ClassDetailsRepository
import com.university.enrollment.repository.SemesterRepository
import com.university.enrollment.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import spock.lang.Specification

class UserServiceImplSpec extends Specification {

    def userRepository = Mock(UserRepository)
    def classDetailsRepository = Mock(ClassDetailsRepository)
    def semesterRepository = Mock(SemesterRepository)
    def passwordEncoder = Mock(PasswordEncoder)

    def userService = new UserServiceImpl(userRepository, classDetailsRepository, semesterRepository, passwordEncoder)

    UserRequest request = new UserRequest()
    User user = new User()

    def setup() {
        request.setUserName("loginUserName")
        request.setSemester("Fall Semester")
        request.setClassName("3A, 3B")

        user.setUserName("loginUserName")
    }

    def "Create user - user already exists"() {
        given:
        userRepository.findByUserName("loginUserName") >> Mock(User)

        when:
        userService.createUser(request)

        then:
        thrown(UserExistsException.class)

    }

    def "Create user - semester not exists"() {
        given:
        userRepository.findByUserName("loginUserName") >> null
        semesterRepository.findByName("Fall Semester") >> null
        when:
        userService.createUser(request)

        then:
        thrown(SemesterNotFoundException.class)

    }

    def "Create user - class not exists"() {
        given:
        userRepository.findByUserName("loginUserName") >> null
        semesterRepository.findByName("Fall Semester") >> Mock(Semester)
        classDetailsRepository.findByName("3A") >> Mock(ClassDetails)
        classDetailsRepository.findByName("3B") >> null
        when:
        userService.createUser(request)

        then:
        thrown(StudentClassNotFoundException.class)

    }

    def "Create user - max credits Exception"() {
        given:
        userRepository.findByUserName("loginUserName") >> null
        semesterRepository.findByName("Fall Semester") >> Mock(Semester)

        ClassDetails details = new ClassDetails()
        details.setCredits(12)
        details.setName("3A")

        classDetailsRepository.findByName("3A") >> details
        classDetailsRepository.findByName("3B") >> details
        when:
        userService.createUser(request)

        then:
        thrown(MaxCreditsException.class)

    }

    def "Create user"() {
        given:
        userRepository.findByUserName("loginUserName") >> null
        semesterRepository.findByName("Fall Semester") >> Mock(Semester)

        ClassDetails details = new ClassDetails()
        details.setCredits(6)
        details.setName("3A")

        classDetailsRepository.findByName("3A") >> details
        classDetailsRepository.findByName("3B") >> details

        request.setFirstName("first name")
        request.setLastName("last name")
        request.setNationality("US")
        request.setRole(RoleTypes.ROLE_USER)
        request.setPassword("password")
        passwordEncoder.encode(*_) >> "Enc Password"
        userRepository.save(*_) >> user

        when:
        def response = userService.createUser(request)

        then:
        response != null
        response.getUserName() == "loginUserName"

    }

    def "delete user - not found"() {
        given:
        UserDeleteRequest request = new UserDeleteRequest()
        request.setId(11)
        userRepository.findById(*_) >> Optional.ofNullable(null)

        when:
        userService.deleteStudent(request)
        then:
        thrown(StudentNotFoundException.class)

    }

    def "delete user -  found"() {
        given:
        UserDeleteRequest request = new UserDeleteRequest()
        request.setId(11)
        userRepository.findById(*_) >> Optional.of(new User())

        when:
        def res = userService.deleteStudent(request)
        then:
        res != null
    }

    def "getAllStudents - StudentClassNotFoundException"() {
        given:
        classDetailsRepository.findByName(*_) >> null
        when:
        userService.getAllStudents("3A")
        then:
        thrown(StudentClassNotFoundException.class)
    }

    def "getAllStudents"() {
        given:
        ClassDetails details = new ClassDetails()
        details.setName("3A")
        classDetailsRepository.findByName(*_) >> details
        List<User> userList = new ArrayList<>()
        User user1 = new User()
        user1.setUserName("1")
        userList.add(user1)
        User user2 = new User()
        user2.setUserName("2")
        userList.add(user2)
        userRepository.findAllByClassName("3A") >> userList
        when:
        def resposne = userService.getAllStudents("3A")
        then:
        resposne.size() == 2
        resposne.get(0).getUserName() == "1"
        resposne.get(1).getUserName() == "2"
    }

    def "getStudent - present"() {
        given:
        userRepository.findById(*_) >> Optional.of(user)
        when:
        def response = userService.getStudent(1)
        then:
        response != null
    }

    def "getStudent - not present"() {
        given:
        userRepository.findById(*_) >> Optional.ofNullable(null)
        when:
        def response = userService.getStudent(1)
        then:
        thrown(StudentNotFoundException.class)
    }

    def "update student - not present"() {
        given:
        UserUpdateRequest request = new UserUpdateRequest()
        request.setId(1)
        userRepository.findById(*_) >> Optional.ofNullable(null)
        when:
        userService.updateStudent(request)
        then:
        thrown(StudentNotFoundException.class)
    }

    def "update student -  present"() {
        given:
        UserUpdateRequest request = new UserUpdateRequest()
        request.setId(1)
        request.setClassName("3A")
        userRepository.findById(*_) >> Optional.of(user)
        ClassDetails details = new ClassDetails()
        details.setName("3A")
        classDetailsRepository.findByName(*_) >> details
        userRepository.save(*_) >> user
        when:
        def response = userService.updateStudent(request)
        then:
        response != null
        response.getUserName() == "loginUserName"
    }

    def "update student -  semester not found"() {
        given:
        UserUpdateRequest request = new UserUpdateRequest()
        request.setId(1)
        request.setClassName("3A")
        userRepository.findById(*_) >> Optional.of(user)
        ClassDetails details = new ClassDetails()
        details.setName("3A")
        classDetailsRepository.findByName(*_) >> details
        request.setSemester("Winter Semester")
        semesterRepository.findByName("Fall Semester") >> null
        userRepository.save(*_) >> user
        when:
        userService.updateStudent(request)
        then:
        thrown(SemesterNotFoundException.class)
    }

    def "update student -  semester found"() {
        given:
        UserUpdateRequest request = new UserUpdateRequest()
        request.setId(1)
        request.setClassName("3A")
        userRepository.findById(*_) >> Optional.of(user)
        ClassDetails details = new ClassDetails()
        details.setName("3A")
        classDetailsRepository.findByName(*_) >> details
        request.setSemester("Winter Semester")
        semesterRepository.findByName(*_) >> Mock(Semester)
        userRepository.save(*_) >> user
        when:
        def response = userService.updateStudent(request)
        then:
        response != null
        response.getUserName() == "loginUserName"
    }

}
