package com.adaptionsoft.games.uglytrivia;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class QuestionDeckTest {

    @Test
    void categoriesArePutIntoQuestionMapWithAValueOfZero() {
        QuestionDeck questionDeck = new QuestionDeck("Rock", "Pop");

        Integer questionNumber = questionDeck.getQuestionNumberAtCategory("Rock");

        assertThat(questionNumber).isEqualTo(0);
    }

    @Test
    void shouldUpdateQuestionNumberAfterQuestionIsAsked() {
        QuestionDeck questionDeck = new QuestionDeck("Rock", "Pop");

        Category category = questionDeck.getQuestion("Rock");
        category.removeQuestionFromDeck();
        Integer questionNumber = questionDeck.getQuestionNumberAtCategory("Rock");

        assertThat(questionNumber).isEqualTo(1);
    }

    @Test
    void shouldGetCategoryBasedOneBoardSpaceIndex() {
        QuestionDeck questionDeck = new QuestionDeck("Rock", "Pop");

        String currentCategory = questionDeck.getCurrentCategory(5);

        assertThat(currentCategory).isEqualTo("Pop");
    }
}