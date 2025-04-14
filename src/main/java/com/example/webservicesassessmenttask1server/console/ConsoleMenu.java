package com.example.webservicesassessmenttask1server.console;

import com.example.webservicesassessmenttask1server.entity.Student;
import com.example.webservicesassessmenttask1server.entity.Subject;
import com.example.webservicesassessmenttask1server.network.Server;
import com.example.webservicesassessmenttask1server.service.StudentService;
import com.example.webservicesassessmenttask1server.service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

@Component
@RequiredArgsConstructor
public class ConsoleMenu {
    private final StudentService studentService;
    private final SubjectService subjectService;
    private final Server server;
    private final Scanner scanner;
    private volatile boolean running = true;


    public void start() {

        while (running) {
            printMenu();
            try {
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1 -> addStudent();
                    case 2 -> addSubjectToStudent();
                    case 3 -> viewAllStudents();
                    case 4 -> sendStudentAsXml();
                    case 5 -> exitApplication();
                    default -> System.out.println("Invalid choice. Try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid number!");
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void addStudent() {
        System.out.print("Enter student name: ");
        String name = scanner.nextLine();
        System.out.print("Enter student age: ");
        int age = scanner.nextInt();
        scanner.nextLine();

        Student student = studentService.createStudent(name, age);
        System.out.println("Student created with ID: " + student.getId());
    }

    private void addSubjectToStudent() {
        System.out.print("Enter student ID: ");
        Long studentId = scanner.nextLong();
        scanner.nextLine();

        System.out.print("Enter subject title: ");
        String title = scanner.nextLine();
        System.out.print("Enter subject credits: ");
        int credits = scanner.nextInt();
        scanner.nextLine();

        Subject subject = subjectService.addSubjectToStudent(studentId, title, credits);
        System.out.println("Subject added with ID: " + subject.getId());
    }

    private void viewAllStudents() {
        List<Student> students = studentService.getAllStudentsWithSubjects();
        students.forEach(this::printStudentDetails);
    }

    private void printStudentDetails(Student student) {
        System.out.printf("\nID: %d, Name: %s, Age: %d%n",
                student.getId(), student.getName(), student.getAge());

        if (!student.getSubjects().isEmpty()) {
            System.out.println("  Subjects:");
            student.getSubjects().forEach(subject ->
                    System.out.printf("  - %s (Credits: %d)%n",
                            subject.getTitle(), subject.getCredits()));
        }
    }

    private void sendStudentAsXml() {
        System.out.print("Enter student ID to send as XML: ");
        Long studentId = scanner.nextLong();
        scanner.nextLine();

        try {
            server.sendStudentAsXML(studentId);
            System.out.println("XML sent successfully!");
        } catch (Exception e) {
            System.out.println("Error sending XML: " + e.getMessage());
        }
    }

    private void exitApplication() {
        System.out.println("Shutting down...");
        server.stopServer();
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
}