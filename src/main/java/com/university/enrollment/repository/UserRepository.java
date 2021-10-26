package com.university.enrollment.repository;

import com.university.enrollment.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT User FROM User User WHERE User.userName = ?1")
    User findByUserName(String name);

    @Query("select t from User t join t.classDetails u where u.name = :className")
    List<User> findAllByClassName(@Param("className") String className);



}
