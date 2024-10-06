package org.example.repository;

import org.example.model.Question;
import org.example.model.QuestionOption;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class QuestionCsvParser {
    public List<Question> parse(BufferedReader reader) throws IOException {
        List<Question> questions = new ArrayList<>();
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
        return questions;
    }
}
