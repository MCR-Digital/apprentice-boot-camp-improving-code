package com.adaptionsoft.games.board;

import com.adaptionsoft.games.Deck.*;
import com.adaptionsoft.games.uglytrivia.QuestionTypes;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Board implements Playable {

    //stores amount of positions
    //stores decks (questions)
    //Has rules

    List<Position> positions;
    List<Deck> decks;

    public Board() {
        populatePositions();
        populateDeck();
    }

    private void populatePositions() {
        this.positions = List.of(
                new Position(QuestionTypes.Pop),
                new Position(QuestionTypes.Science),
                new Position(QuestionTypes.Sports),
                new Position(QuestionTypes.Rock),
                new Position(QuestionTypes.Pop),
                new Position(QuestionTypes.Science),
                new Position(QuestionTypes.Sports),
                new Position(QuestionTypes.Rock),
                new Position(QuestionTypes.Pop),
                new Position(QuestionTypes.Science),
                new Position(QuestionTypes.Sports),
                new Position(QuestionTypes.Rock)
        );
    }

    private void populateDeck() {
        this.decks = List.of(
                new PopDeck(50),
                new ScienceDeck(50),
                new SportsDeck(50),
                new RockDeck(50)
        );
    }

    public void playerMoves() {

    }

    @Override
    public String getNextQuestion(int position) {
        Optional<Deck> currentCategoryDeck = decks.stream()
                .filter(deck -> deck.getCategory() == positions.get(position).getCategory())
                .findFirst();
        return currentCategoryDeck.map(Deck::getNextQuestion).orElse(null);
    }

}
