package com.example.webservicesassessmenttask1server.service;

import com.example.webservicesassessmenttask1server.entity.Student;
import com.example.webservicesassessmenttask1server.repository.StudentRepository;
import com.example.webservicesassessmenttask1server.repository.SubjectRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;
    private final SubjectRepository subjectRepository;

    public Student createStudent(String name, int age) {
        Student student = new Student();
        student.setName(name);
        student.setAge(age);
        return studentRepository.save(student);
    }

    public Student getStudentWithSubjects(Long id) {
        return studentRepository.findByIdWithSubjects(id)
                .orElseThrow(() -> new EntityNotFoundException("Student not found"));
    }

    public List<Student> getAllStudentsWithSubjects() {
        return studentRepository.findAll();
    }

    public void deleteStudent(Long id) {
        studentRepository.deleteById(Math.toIntExact(id));
    }
}