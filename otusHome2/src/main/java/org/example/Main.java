package org.example;

import org.example.service.QuizServiceImpl;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;


@ComponentScan
@Configuration
@PropertySource("classpath:application.properties")

public class Main {
    public static void main(String[] args) throws Exception {
        // Загружаем контекст Spring
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Main.class);

        // Получаем бин QuizService и запускаем тест
        QuizServiceImpl quizService = context.getBean(QuizServiceImpl.class);
        quizService.startQuiz();
    }
}
