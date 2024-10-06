package org.example.model;


import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Answer {
    private List<QuestionOption> questionOptions;
}
