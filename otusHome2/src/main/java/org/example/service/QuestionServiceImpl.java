package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.model.Question;
import org.example.repository.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;

    @Override
    public List<Question> loadQuestions() throws Exception {
        return questionRepository.findAll();
    }
}
