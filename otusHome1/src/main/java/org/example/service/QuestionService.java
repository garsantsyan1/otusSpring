package org.example.service;


import org.example.model.Question;

import java.util.List;

public interface QuestionService {

    List<Question> loadQuestions() throws Exception;

}
