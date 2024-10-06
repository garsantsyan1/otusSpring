package org.example.repository;

import org.example.model.Question;
import org.example.model.QuestionOption;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


@Repository
public class CsvQuestionRepository implements QuestionRepository {

    private final Resource csvResource;

    public CsvQuestionRepository(Resource csvResource) {
        this.csvResource = csvResource;
    }

    @Override
    public List<Question> findAll() throws Exception {
        List<Question> questions = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(csvResource.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");

                String questionText = parts[0];
                String[] optionTitles = parts[1].split("\\|");
                String[] isCorrectArray = parts[2].split("\\|");

                List<QuestionOption> options = new ArrayList<>();
                for (int i = 0; i < optionTitles.length; i++) {
                    boolean isCorrect = Boolean.parseBoolean(isCorrectArray[i]);
                    options.add(new QuestionOption(optionTitles[i], isCorrect));
                }

                questions.add(new Question(questionText, options));
            }
        }

        return questions;
    }

}
