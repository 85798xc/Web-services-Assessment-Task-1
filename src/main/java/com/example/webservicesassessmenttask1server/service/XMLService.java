package com.example.webservicesassessmenttask1server.service;

import com.example.webservicesassessmenttask1server.entity.Student;
import com.example.webservicesassessmenttask1server.mapper.XMLMapper;
import jakarta.xml.bind.JAXBException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class XMLService {
    private final StudentService studentService;
    private final XMLMapper xmlMapper;

    public String getStudentAsXML(Long studentId) throws JAXBException {
        Student student = studentService.getStudentWithSubjects(studentId);
        return xmlMapper.transformToXML(student);
    }
}