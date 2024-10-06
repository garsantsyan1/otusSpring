package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.model.Question;
import org.example.model.QuestionOption;
import org.example.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class QuizServiceImpl implements QuizService {

    private final QuestionService questionService;
    private final UserService userService;
    private final IOService ioService;

    @Value("${quiz.passingScore}")
    private int passingScore; // Минимальное количество правильных ответов для прохождения

    @Value("${quiz.totalQuestions}")
    private int totalQuestions; // Количество вопросов

    @Override
    public void startQuiz() throws Exception {
        // Получаем пользователя через сервис
        User user = userService.getUser();

        // Загружаем вопросы
        List<Question> questions = questionService.loadQuestions();

        // Ограничиваем количество вопросов до totalQuestions
        if (questions.size() > totalQuestions) {
            questions = questions.subList(0, totalQuestions);
        }

        int correctAnswers = 0;

        // Приветствие
        ioService.printLine("Hello, " + user.getName() + " " + user.getLastName() + "! Let's start the quiz.\n");

        // Цикл по вопросам
        for (Question question : questions) {
            ioService.printLine(question.getText());

            List<QuestionOption> options = question.getQuestionOptions();
            for (int i = 0; i < options.size(); i++) {
                ioService.printLine((i + 1) + ": " + options.get(i).getTitle());
            }

            // Получение ответа от пользователя: ввод номеров через запятую
            ioService.printLine("Your answer (choose one or more numbers, separated by commas): ");
            String userAnswerInput = ioService.readLine();

            // Валидация ввода пользователя
            Set<Integer> userAnswers = parseUserAnswers(userAnswerInput, options.size());

            // Проверка правильности ответа
            if (isAnswerCorrect(userAnswers, options)) {
                correctAnswers++;
                ioService.printLine("Correct!\n");
            } else {
                ioService.printLine("Incorrect.\n");
            }
        }

        // Обновляем очки пользователя через сервис
        userService.updateScore(user, correctAnswers);

        // Вывод результатов
        ioService.printLine("Quiz finished!");
        ioService.printLine("You got " + correctAnswers + " out of " + questions.size() + " correct.");

        // Проверка зачёта
        if (correctAnswers >= passingScore) {
            ioService.printLine("Congratulations, you passed the quiz!");
        } else {
            ioService.printLine("Unfortunately, you did not pass the quiz.");
        }

        ioService.printLine("Your total score is: " + userService.getUserScore(user));
    }

    // Метод для валидации и парсинга ответов пользователя
    private Set<Integer> parseUserAnswers(String userAnswerInput, int optionCount) {
        Set<Integer> userAnswers = new HashSet<>();
        String[] userAnswerIndexes = userAnswerInput.split(",");
        for (String index : userAnswerIndexes) {
            try {
                int answerIndex = Integer.parseInt(index.trim()) - 1;
                if (answerIndex >= 0 && answerIndex < optionCount) {
                    userAnswers.add(answerIndex);
                } else {
                    ioService.printLine("Invalid answer number: " + (answerIndex + 1));
                }
            } catch (NumberFormatException e) {
                ioService.printLine("Invalid input format, please enter numbers separated by commas.");
            }
        }
        return userAnswers;
    }

    // Метод для проверки правильности ответа
    private boolean isAnswerCorrect(Set<Integer> userAnswers, List<QuestionOption> options) {
        for (int i = 0; i < options.size(); i++) {
            boolean isCorrect = options.get(i).getIsCorrect();
            if (isCorrect && !userAnswers.contains(i) || !isCorrect && userAnswers.contains(i)) {
                return false; // Ответ неправильный
            }
        }
        return true; // Все условия правильности соблюдены
    }
}