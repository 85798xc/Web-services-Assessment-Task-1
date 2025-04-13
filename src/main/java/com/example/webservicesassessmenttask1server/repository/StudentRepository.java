package com.example.webservicesassessmenttask1server.repository;

import com.example.webservicesassessmenttask1server.entity.Student;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    @EntityGraph(attributePaths = "subjects")
    @Query("SELECT s FROM Student s WHERE s.id = :id")
    Optional<Student> findByIdWithSubjects(@Param("id") Long id);
}
