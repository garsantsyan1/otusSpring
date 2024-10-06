package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.model.User;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final IOService ioService;


    @Override
    public User getUser() {
        ioService.printLine("Please enter your first name: ");
        String firstName = ioService.readLine();
        ioService.printLine("Please enter your last name: ");
        String lastName = ioService.readLine();

        return new User(firstName, lastName, 0); // Создание нового пользователя с начальным счетом 0
    }

    @Override
    public void updateScore(User user, int score) {
        user.setScore(user.getScore() + score); // Обновление очков конкретного пользователя
    }

    @Override
    public int getUserScore(User user) {
        return user.getScore(); // Получение очков конкретного пользователя
    }
}
