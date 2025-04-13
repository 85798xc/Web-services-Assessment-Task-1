package com.example.webservicesassessmenttask1server.service;

import com.example.webservicesassessmenttask1server.entity.Student;
import com.example.webservicesassessmenttask1server.entity.Subject;
import com.example.webservicesassessmenttask1server.repository.SubjectRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class SubjectService {
    private final SubjectRepository subjectRepository;
    private final StudentService studentService;

    public Subject addSubjectToStudent(Long studentId, String title, int credits) {
        Student student = studentService.getStudentWithSubjects(studentId);

        Subject subject = new Subject();
        subject.setTitle(title);
        subject.setCredits(credits);
        subject.setStudent(student);

        student.getSubjects().add(subject);
        return subjectRepository.save(subject);
    }

    public List<Subject> getSubjectsByStudentId(Long studentId) {
        return subjectRepository.findByStudentId(studentId);
    }

    public void deleteSubject(Long id) {
        subjectRepository.deleteById(id);
    }
}