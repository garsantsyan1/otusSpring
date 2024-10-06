package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.model.Question;
import org.example.model.QuestionOption;
import org.example.model.User;
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

    @Override
    public void startQuiz() throws Exception {
        // Получаем пользователя через сервис
        User user = userService.getUser();

        // Загружаем вопросы
        List<Question> questions = questionService.loadQuestions();
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

            // Разбираем ввод пользователя на массив номеров
            String[] userAnswerIndexes = userAnswerInput.split(",");
            Set<Integer> userAnswers = new HashSet<>();
            for (String index : userAnswerIndexes) {
                userAnswers.add(Integer.parseInt(index.trim()) - 1); // Конвертируем в индексы
            }

            // Проверка правильности: сверяем все выбранные варианты с правильными
            boolean isCorrect = true;
            for (int i = 0; i < options.size(); i++) {
                if (options.get(i).getIsCorrect() && !userAnswers.contains(i) ||
                        !options.get(i).getIsCorrect() && userAnswers.contains(i)) {
                    isCorrect = false;
                    break;
                }
            }

            // Подсчет правильных ответов
            if (isCorrect) {
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
        ioService.printLine("Your total score is: " + userService.getUserScore(user));
    }
}
