package com.example.webservicesassessmenttask1server.repository;

import com.example.webservicesassessmenttask1server.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Integer> {
}
