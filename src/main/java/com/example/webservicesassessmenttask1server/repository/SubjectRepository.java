package com.example.webservicesassessmenttask1server.repository;

import com.example.webservicesassessmenttask1server.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
    List<Subject> findByStudentId(Long studentId);
}