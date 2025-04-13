package com.example.webservicesassessmenttask1server;

import com.example.webservicesassessmenttask1server.console.ConsoleMenu;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class WebServicesAssessmentTask11Application {

    public static void main(String[] args) {
        SpringApplication.run(WebServicesAssessmentTask11Application.class, args);
    }

    @Bean
    public CommandLineRunner run(ConsoleMenu consoleMenu) {
        return args -> consoleMenu.start();
    }
}
