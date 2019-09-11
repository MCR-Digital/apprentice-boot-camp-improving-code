package com.adaptionsoft.games;

import java.util.HashMap;
import java.util.Map;

public class Board {

    private static Map<Integer, String> board;

    public Board() {
        generateBoard();
    }

    private void generateBoard() {
        board = new HashMap<>();
        board.put(0, QuestionCategories.POP.getCategory());
        board.put(1, QuestionCategories.SCIENCE.getCategory());
        board.put(2, QuestionCategories.SPORTS.getCategory());
        board.put(3, QuestionCategories.ROCK.getCategory());
        board.put(4, QuestionCategories.POP.getCategory());
        board.put(5, QuestionCategories.SCIENCE.getCategory());
        board.put(6, QuestionCategories.SPORTS.getCategory());
        board.put(7, QuestionCategories.ROCK.getCategory());
        board.put(8, QuestionCategories.POP.getCategory());
        board.put(9, QuestionCategories.SCIENCE.getCategory());
        board.put(10, QuestionCategories.SPORTS.getCategory());
    }

    public String getCurrentCategory(int placeOnBoardOfCurrentPlayer) {

        if (placeOnBoardOfCurrentPlayer % 4 == 0) {
            return QuestionCategories.POP.getCategory();
        } else if (placeOnBoardOfCurrentPlayer % 4 == 1) {
            return QuestionCategories.SCIENCE.getCategory();
        } else if (placeOnBoardOfCurrentPlayer % 4 == 2) {
            return QuestionCategories.SPORTS.getCategory();
        } else {
            return QuestionCategories.ROCK.getCategory();
        }
    }
}
