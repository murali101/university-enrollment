package com.university.enrollment.repository;

import com.university.enrollment.model.entities.UserTypes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTypesrepository extends JpaRepository<UserTypes, Integer> {

}
