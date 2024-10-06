package org.example.service;

import org.example.model.User;

public interface UserService {

    User getUser(); // Получение пользователя

    void updateScore(User user, int score); // Обновление очков пользователя через параметр

    int getUserScore(User user);

}
