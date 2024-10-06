package org.example.repository;

import lombok.RequiredArgsConstructor;
import org.example.model.Question;
import org.example.model.QuestionOption;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


@Repository
@RequiredArgsConstructor
public class CsvQuestionRepository implements QuestionRepository {

    private final Resource csvResource;
    private final QuestionCsvParser csvParser;

    @Override
    public List<Question> findAll() throws Exception {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(csvResource.getInputStream()))) {
            return csvParser.parse(reader);
        }
    }

}
