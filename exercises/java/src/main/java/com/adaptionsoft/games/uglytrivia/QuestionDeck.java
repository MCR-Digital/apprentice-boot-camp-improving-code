package com.adaptionsoft.games.uglytrivia;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class QuestionDeck {

    private List<Question> questions;

    public QuestionDeck(String... categories) {
        this.questions = Arrays.stream(categories).map(Question::new).collect(Collectors.toList());
    }

    public Integer getQuestionNumberAtCategory(String category) {
        Question question = getQuestion(category);
        return question.getQuestionNumber();
    }

    private Question getQuestion(String category) {
        return questions.stream()
                .filter(target -> category.equals(target.getCategory()))
                .collect(Collectors.toList()).get(0);
    }

    public void pickUpQuestion(String category) {
        Question question = getQuestion(category);

        String questionString = category + "question " + question.getQuestionNumber();
        question.updateQuestionNumber();
    }
}
