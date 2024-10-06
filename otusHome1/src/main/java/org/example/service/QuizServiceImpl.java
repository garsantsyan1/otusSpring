package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.model.Question;
import org.example.model.QuestionOption;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@RequiredArgsConstructor
public class QuizServiceImpl implements QuizService{

    private final QuestionService questionService;
    private final IOService ioService;

    @Override
    public void startQuiz() throws Exception {
        extracted();

        List<Question> questions = questionService.loadQuestions();
        int correctAnswers = 0;

        for (Question question : questions) {
            ioService.printLine(question.getText());

            List<QuestionOption> options = question.getQuestionOptions();
            for (int i = 0; i < options.size(); i++) {
                ioService.printLine((i + 1) + ": " + options.get(i).getTitle());
            }

            // Получение нескольких ответов от пользователя через запятую
            ioService.printLine("Your answer (choose the numbers, separated by commas if multiple): ");
            String[] userAnswerIndexes = ioService.readLine().split(",");

            // Преобразуем ответы пользователя в индексы
            Set<Integer> userAnswerSet = new HashSet<>();
            for (String answer : userAnswerIndexes) {
                userAnswerSet.add(Integer.parseInt(answer.trim()) - 1);  // Преобразование в индексы
            }

            // Проверка правильности ответов
            boolean isCorrect = true;
            for (int i = 0; i < options.size(); i++) {
                QuestionOption option = options.get(i);
                if (option.getIsCorrect() && !userAnswerSet.contains(i)) {
                    isCorrect = false;  // Правильный ответ не выбран
                    break;
                }
                if (!option.getIsCorrect() && userAnswerSet.contains(i)) {
                    isCorrect = false;  // Неправильный ответ выбран
                    break;
                }
            }

            if (isCorrect) {
                correctAnswers++;
                ioService.printLine("Correct!\n");
            } else {
                ioService.printLine("Incorrect.\n");
            }
        }

        // Результаты
        ioService.printLine("Quiz finished!");
        ioService.printLine("You got " + correctAnswers + " out of " + questions.size() + " correct.");
    }

    private void extracted() {
        ioService.printLine("Please enter your first name: ");
        String firstName = ioService.readLine();
        ioService.printLine("Please enter your last name: ");
        String lastName = ioService.readLine();

        ioService.printLine("Hello, " + firstName + " " + lastName + "! Let's start the quiz.\n");
    }


}