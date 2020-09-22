package com.adaptionsoft.games.board;

import com.adaptionsoft.games.uglytrivia.QuestionTypes;

public class Position {
    private QuestionTypes category;

    public Position(QuestionTypes category) {
        this.category = category;
    }

    public QuestionTypes getCategory() {
        return category;
    }
}
