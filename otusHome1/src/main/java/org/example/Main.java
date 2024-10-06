package org.example;

import org.example.service.QuizServiceImpl;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class Main {
    public static void main(String[] args) throws Exception {
        // Загружаем контекст Spring
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");

        // Получаем бин QuizService и запускаем тест
        QuizServiceImpl quizService = context.getBean(QuizServiceImpl.class);
        quizService.startQuiz();
    }
}
