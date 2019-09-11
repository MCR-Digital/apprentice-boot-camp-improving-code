package com.adaptionsoft.games.uglytrivia;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class QuestionDeck {

    private List<Category> categories;

    public QuestionDeck(String... categories) {
        this.categories = Arrays.stream(categories).map(Category::new).collect(Collectors.toList());
    }

    public Integer getQuestionNumberAtCategory(String categoryName) {
        Category question = getQuestion(categoryName);
        return question.getQuestionNumber();
    }

    public Category getQuestion(String category) {
        return categories.stream()
                .filter(target -> category.equals(target.getCategoryName()))
                .collect(Collectors.toList()).get(0);
    }

    String getCurrentCategory(int boardSquareIndex) {
        return categories.get(boardSquareIndex % categories.size()).getCategoryName();
    }
}
