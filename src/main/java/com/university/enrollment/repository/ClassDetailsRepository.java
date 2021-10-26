package com.university.enrollment.repository;

import com.university.enrollment.model.entities.ClassDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassDetailsRepository extends JpaRepository<ClassDetails, Integer> {

    @Query("SELECT ClassDetails FROM ClassDetails ClassDetails WHERE ClassDetails.name = ?1")
    ClassDetails findByName(String name);

}
