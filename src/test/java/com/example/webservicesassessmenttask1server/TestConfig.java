package com.example.webservicesassessmenttask1server;

import com.example.webservicesassessmenttask1server.console.ConsoleMenu;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import static org.mockito.Mockito.mock;

@SpringBootTest(properties = "spring.main.allow-bean-definition-overriding=true")
@TestConfiguration
public class TestConfig {
    @Bean
    @Primary
    public ConsoleMenu consoleMenu() {
        return mock(ConsoleMenu.class); // Mock the console menu
    }
}