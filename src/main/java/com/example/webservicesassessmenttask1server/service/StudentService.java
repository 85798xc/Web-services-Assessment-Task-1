package com.example.webservicesassessmenttask1server.service;

import com.example.webservicesassessmenttask1server.entity.Student;
import com.example.webservicesassessmenttask1server.repository.StudentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;

    public List<Student> getAllStudentsWithSubjects() {
        return studentRepository.findAll();
    }
}
