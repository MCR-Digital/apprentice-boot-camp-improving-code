package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;
import java.util.List;

public class GameBoard {

    Player player;
    List<GameBoardPosition> board = new ArrayList<>();

    public GameBoard() {
        board.add(new GameBoardPosition("Pop"));
        board.add(new GameBoardPosition("Science"));
        board.add(new GameBoardPosition("Sports"));
        board.add(new GameBoardPosition("Rock"));
        board.add(new GameBoardPosition("Pop"));
        board.add(new GameBoardPosition("Science"));
        board.add(new GameBoardPosition("Sports"));
        board.add(new GameBoardPosition("Rock"));
        board.add(new GameBoardPosition("Pop"));
        board.add(new GameBoardPosition("Science"));
        board.add(new GameBoardPosition("Sports"));
        board.add(new GameBoardPosition("Rock"));
    }
}
