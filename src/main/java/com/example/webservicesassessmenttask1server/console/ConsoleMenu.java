package com.example.webservicesassessmenttask1server.console;

import com.example.webservicesassessmenttask1server.entity.Student;
import com.example.webservicesassessmenttask1server.entity.Subject;
import com.example.webservicesassessmenttask1server.mapper.XMLMapper;
import com.example.webservicesassessmenttask1server.network.Server;
import com.example.webservicesassessmenttask1server.repository.StudentRepository;
import com.example.webservicesassessmenttask1server.service.StudentService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.xml.bind.JAXBException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

@Component
public class ConsoleMenu {

    private final StudentRepository studentRepository;
    private final XMLMapper xmlMapper;
    private final Server server;
    private final Scanner scanner;
    private final StudentService studentService;
    private volatile boolean running = true;

    public ConsoleMenu(StudentRepository studentRepository, XMLMapper xmlMapper, Server server, StudentService studentService) {
        this.studentRepository = studentRepository;
        this.xmlMapper = xmlMapper;
        this.server = server;
        this.studentService = studentService;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        while (running) {
            printMenu();
            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); // consume newline

                switch (choice) {
                    case 1 -> addStudent();
                    case 2 -> addSubjectToStudent();
                    case 3 -> getAllStudents();
                    case 4 -> sendStudentAsXml();
                    case 5 -> exitApplication();
                    default -> System.out.println("Invalid choice. Try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid number!");
                scanner.nextLine(); // clear invalid input
            }
        }
    }
    private void exitApplication() {
        System.out.println("Shutting down...");
        server.stop();
        running = false;
    }

    private void printMenu() {
        System.out.println("\n===== Student Management System =====");
        System.out.println("1. Add new student");
        System.out.println("2. Add subject to student");
        System.out.println("3. View all students");
        System.out.println("4. Send student data as XML");
        System.out.println("5. Exit");
        System.out.print("Enter your choice: ");
    }

    private void addStudent() {
        System.out.print("Enter student name: ");
        String name = scanner.nextLine();
        System.out.print("Enter student age: ");
        int age = scanner.nextInt();
        scanner.nextLine(); // consume newline

        Student student = new Student();
        student.setName(name);
        student.setAge(age);
        studentRepository.save(student);
        System.out.println("Student added successfully!");
    }

    private void addSubjectToStudent() {
        System.out.print("Enter student ID: ");
        long studentId = scanner.nextLong();
        scanner.nextLine(); // consume newline

        Student student = studentRepository.findById((int) studentId)
                .orElseThrow(() -> new EntityNotFoundException("Student not found"));

        System.out.print("Enter subject title: ");
        String title = scanner.nextLine();
        System.out.print("Enter subject credits: ");
        int credits = scanner.nextInt();
        scanner.nextLine(); // consume newline

        Subject subject = new Subject();
        subject.setTitle(title);
        subject.setCredits(credits);
        subject.setStudent(student);

        student.getSubjects().add(subject);
        studentRepository.save(student);
        System.out.println("Subject added to student successfully!");
    }

    private void getAllStudents() {
        List<Student> students = studentService.getAllStudentsWithSubjects();
        if (students.isEmpty()) {
            System.out.println("No students found.");
            return;
        }

        System.out.println("\nList of Students:");
        for (Student student : students) {
            System.out.printf("ID: %d, Name: %s, Age: %d%n",
                    student.getId(), student.getName(), student.getAge());
            if (!student.getSubjects().isEmpty()) {
                System.out.println("  Subjects:");
                for (Subject subject : student.getSubjects()) {
                    System.out.printf("  - %s (Credits: %d)%n",
                            subject.getTitle(), subject.getCredits());
                }
            }
        }
    }

    private void sendStudentAsXml() {
        System.out.print("Enter student ID to send as XML: ");
        long studentId = scanner.nextLong();
        scanner.nextLine(); // consume newline

        Student student = studentRepository.findById((int) studentId)
                .orElseThrow(() -> new EntityNotFoundException("Student not found"));

        try {
            String xml = xmlMapper.transformToXML(student);
            System.out.println("\nGenerated XML:");
            System.out.println(xml);

            // Save to temp file and send
            Path tempFile = java.nio.file.Path.of("temp_student.xml");
            Files.writeString(tempFile, xml);
            server.sendXML(tempFile.toString());
            System.out.println("XML sent successfully!");

            // Clean up
            Files.deleteIfExists(tempFile);
        } catch (JAXBException e) {
            System.err.println("Error converting to XML: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error sending XML: " + e.getMessage());
        }
    }
}