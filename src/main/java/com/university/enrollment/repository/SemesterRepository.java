package com.university.enrollment.repository;

import com.university.enrollment.model.entities.Semester;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SemesterRepository extends JpaRepository<Semester, Integer> {

    @Query("SELECT Semester FROM Semester Semester WHERE Semester.name = ?1")
    Semester findByName(String name);

}
