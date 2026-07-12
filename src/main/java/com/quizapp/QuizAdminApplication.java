package com.quizapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class QuizAdminApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(QuizAdminApplication.class, args);
    }

    // Needed so the app can also be deployed as a traditional WAR on an external Tomcat
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(QuizAdminApplication.class);
    }
}
