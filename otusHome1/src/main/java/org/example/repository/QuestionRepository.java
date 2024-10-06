package org.example.repository;

import org.example.model.Question;

import java.util.List;

public interface QuestionRepository {

    List<Question> findAll() throws Exception;
}
