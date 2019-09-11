package com.adaptionsoft.games.uglytrivia;

import java.util.*;

public class Game {
    private static final int NUMBER_OF_CATEGORY_QUESTIONS = 50;
    private static final String POP_QUESTION = "Pop Question ";
    private static final String SCIENCE_QUESTION = "Science Question ";
    private static final String SPORTS_QUESTION = "Sports Question ";
    private static final String ROCK_QUESTION = "Rock Question ";

    private static final String POP = "Pop";
    private static final String SCIENCE = "Science";
    private static final String SPORTS = "Sports";
    private static final String ROCK = "Rock";

    private List<String> players = new ArrayList<>();
    private int[] boardSquares = new int[6];
    private int[] purses = new int[6];
    private boolean[] inPenaltyBox = new boolean[6];
    private int currentPlayer = 0;
    private boolean isGettingOutOfPenaltyBox;

    private LinkedList<String> popQuestions = new LinkedList<>();
    private LinkedList<String> scienceQuestions = new LinkedList<>();
    private LinkedList<String> sportsQuestions = new LinkedList<>();
    private LinkedList<String> rockQuestions = new LinkedList<>();

    public Game(String... categories) {

        generateQuestionsForEachCategory();
    }

    private void generateQuestionsForEachCategory() {
        for (int questionNumber = 0; questionNumber < NUMBER_OF_CATEGORY_QUESTIONS; questionNumber++) {
            popQuestions.addLast(POP_QUESTION + questionNumber);
            scienceQuestions.addLast(SCIENCE_QUESTION + questionNumber);
            sportsQuestions.addLast((SPORTS_QUESTION + questionNumber));
            rockQuestions.addLast(ROCK_QUESTION + questionNumber);
        }
    }

    public void initialisePlayer(String playerName) {
        players.add(playerName);
        boardSquares[getNumberOfPlayers()] = 0;
        purses[getNumberOfPlayers()] = 0;
        inPenaltyBox[getNumberOfPlayers()] = false;

        System.out.println(playerName + " was added");
        System.out.println("They are player number " + players.size());
    }

    private int getNumberOfPlayers() {
        return players.size();
    }

    public void rollDice(int rollValue) {
        System.out.println(players.get(currentPlayer) + " is the current player");
        System.out.println("They have rolled a " + rollValue);

        if (inPenaltyBox[currentPlayer]) {
            checkPenaltyBoxStatus(rollValue);
        }

        if (isGettingOutOfPenaltyBox || !inPenaltyBox[currentPlayer]) {
            movePlayerForward(rollValue);
            askQuestion();
        }
    }

    private void checkPenaltyBoxStatus(int rollValue) {
        if (rollValue % 2 != 0) {
            isGettingOutOfPenaltyBox = true;
            System.out.println(players.get(currentPlayer) + " is getting out of the penalty box");
        } else {
            isGettingOutOfPenaltyBox = false;
            System.out.println(players.get(currentPlayer) + " is not getting out of the penalty box");
        }
    }

    private void movePlayerForward(int rollValue) {
        boardSquares[currentPlayer] = boardSquares[currentPlayer] + rollValue;
        if (boardSquares[currentPlayer] > 11) {
            boardSquares[currentPlayer] = boardSquares[currentPlayer] - 12;
        }

        System.out.println(players.get(currentPlayer)
                + "'s new location is "
                + boardSquares[currentPlayer]);
        System.out.println("The category is " + currentCategory(boardSquares[currentPlayer]));
    }

    private void askQuestion() {
        Map<String, LinkedList<String>> categoryQuestions = new HashMap<>();
        categoryQuestions.put(getCategoryName(POP_QUESTION), popQuestions);
        categoryQuestions.put(getCategoryName(SCIENCE_QUESTION), scienceQuestions);
        categoryQuestions.put(getCategoryName(SPORTS_QUESTION), sportsQuestions);
        categoryQuestions.put(getCategoryName(ROCK_QUESTION), rockQuestions);

        String currentCategory = currentCategory(boardSquares[currentPlayer]);
        System.out.println(categoryQuestions.get(currentCategory).removeFirst());
    }

    private String getCategoryName(String question) {
        return question.split(" ")[0];
    }

    private String currentCategory(int boardSquareIndex) {
        List<String> categoryName = new ArrayList<>(Arrays.asList(POP, SCIENCE, SPORTS, ROCK));
        return categoryName.get(boardSquareIndex % categoryName.size());
    }

    public boolean wasCorrectlyAnswered() {
        boolean isNotWinner;
        if (isGettingOutOfPenaltyBox || !inPenaltyBox[currentPlayer]) {
            System.out.println("Answer was correct!!!!");
            updatePlayerPurse();
            isNotWinner = !hasPlayerWon();
            switchToNextPlayer();
        } else {
            isNotWinner = true;
            switchToNextPlayer();
        }
        return isNotWinner;
    }

    public boolean wrongAnswer() {
        System.out.println("Question was incorrectly answered");
        System.out.println(players.get(currentPlayer) + " was sent to the penalty box");
        inPenaltyBox[currentPlayer] = true;

        switchToNextPlayer();
        return true;
    }

    private void updatePlayerPurse() {
        purses[currentPlayer]++;
        System.out.println(players.get(currentPlayer)
                + " now has "
                + purses[currentPlayer]
                + " Gold Coins.");
    }

    private void switchToNextPlayer() {
        currentPlayer++;
        if (currentPlayer == players.size()) currentPlayer = 0;
    }

    private boolean hasPlayerWon() {
        return purses[currentPlayer] == 6;
    }
}
