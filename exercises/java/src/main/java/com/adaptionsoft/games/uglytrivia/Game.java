package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;
import java.util.LinkedList;

public class Game {
    public static final String POP = "Pop";
    public static final String SCIENCE = "Science";
    public static final String SPORTS = "Sports";
    public static final String ROCK = "Rock";
    public static final int WINNING_NUMBER = 6;
    ArrayList players = new ArrayList();
    int[] places = new int[6];
    int[] purses = new int[6];
    boolean[] inPenaltyBox = new boolean[6];

    //List of questions for the game
    LinkedList popQuestions = new LinkedList();
    LinkedList scienceQuestions = new LinkedList();
    LinkedList sportsQuestions = new LinkedList();
    LinkedList rockQuestions = new LinkedList();

    int currentPlayerPlace = 0;
    boolean isGettingOutOfPenaltyBox; //false by default

    public Game() {
        for (int i = 0; i < 50; i++) {
            popQuestions.addLast("Pop Question " + i);
            scienceQuestions.addLast(("Science Question " + i));
            sportsQuestions.addLast(("Sports Question " + i));
            rockQuestions.addLast(createRockQuestion(i));
        }
    }

    public String createRockQuestion(int index) {
        return "Rock Question " + index;
    }

    public boolean isPlayable() {
        return (howManyPlayers() >= 2);
    }

    public boolean addPlayer(String playerName) {


        players.add(playerName);
        places[howManyPlayers()] = 0;
        purses[howManyPlayers()] = 0;
        inPenaltyBox[howManyPlayers()] = false;

        System.out.println(playerName + " was added");
        System.out.println("They are player number " + players.size());
        return true;
    }

    public int howManyPlayers() {
        return players.size();
    }

    public void roll(int roll) {
        System.out.println(players.get(currentPlayerPlace) + " is the current player");
        System.out.println("They have rolled a " + roll);

        // 1. Is the player in the penalty box - yes or no
        // 2. If the player is in the penalty box
        if (inPenaltyBox[currentPlayerPlace]) {
            //If the roll is not even,
            if (roll % 2 != 0) {
                isGettingOutOfPenaltyBox = true;
                System.out.println(players.get(currentPlayerPlace) + " is getting out of the penalty box");
                updatePlayerLocationAndCategory(roll);
                askQuestion();
            } else {
                System.out.println(players.get(currentPlayerPlace) + " is not getting out of the penalty box");
                isGettingOutOfPenaltyBox = false;
            }
        } else {
            updatePlayerLocationAndCategory(roll);
            askQuestion();
        }

    }

    private void updatePlayerLocationAndCategory(int roll) {
        places[currentPlayerPlace] = places[currentPlayerPlace] + roll;

        //If the place of the current player is greater than 11, it takes its current place - 12
        if (places[currentPlayerPlace] > 11)
            places[currentPlayerPlace] = places[currentPlayerPlace] - 12;

        System.out.println(players.get(currentPlayerPlace)
                + "'s new location is "
                + places[currentPlayerPlace]);
        System.out.println("The category is " + currentCategory());
    }

    private void askQuestion() {
        if (currentCategory() == POP)
            System.out.println(popQuestions.removeFirst());
        if (currentCategory() == SCIENCE)
            System.out.println(scienceQuestions.removeFirst());
        if (currentCategory() == SPORTS)
            System.out.println(sportsQuestions.removeFirst());
        if (currentCategory() == ROCK)
            System.out.println(rockQuestions.removeFirst());
    }


    private String currentCategory() {
        if (places[currentPlayerPlace] == 0) return POP;
        if (places[currentPlayerPlace] == 4) return POP;
        if (places[currentPlayerPlace] == 8) return POP;
        if (places[currentPlayerPlace] == 1) return SCIENCE;
        if (places[currentPlayerPlace] == 5) return SCIENCE;
        if (places[currentPlayerPlace] == 9) return SCIENCE;
        if (places[currentPlayerPlace] == 2) return SPORTS;
        if (places[currentPlayerPlace] == 6) return SPORTS;
        if (places[currentPlayerPlace] == 10) return SPORTS;
        return ROCK;
    }

    public boolean wasCorrectlyAnswered() {
        if (inPenaltyBox[currentPlayerPlace]) {
            if (isGettingOutOfPenaltyBox) {
                updateCoins("Answer was correct!!!!");

                boolean winner = didPlayerWin();

                updatePlayerPlace();
                return winner;

            } else {
                updatePlayerPlace();
                return true;
            }


        } else {
            updateCoins("Answer was corrent!!!!");

            boolean winner = didPlayerWin();
            updatePlayerPlace();

            return winner;
        }
    }

    private void updatePlayerPlace() {
        currentPlayerPlace++;
        if (currentPlayerPlace == players.size()) {
            currentPlayerPlace = 0;
        }
    }

    private void updateCoins(String s) {
        System.out.println(s);
        purses[currentPlayerPlace]++;
        System.out.println(players.get(currentPlayerPlace)
                + " now has "
                + purses[currentPlayerPlace]
                + " Gold Coins.");
    }

    public boolean isAnswerWrong() {
        System.out.println("Question was incorrectly answered");
        System.out.println(players.get(currentPlayerPlace) + " was sent to the penalty box");
        inPenaltyBox[currentPlayerPlace] = true;

        updatePlayerPlace();

        return true;
    }


    private boolean didPlayerWin() {
        return (purses[currentPlayerPlace] != WINNING_NUMBER);
    }
}
