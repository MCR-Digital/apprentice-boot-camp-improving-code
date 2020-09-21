
package com.adaptionsoft.games.trivia.runner;

import java.util.Random;

import com.adaptionsoft.games.uglytrivia.Game;

public class GameRunner {

    private static boolean gameOn;

    public static void main(String[] args) {
        Game aGame = new Game();

        aGame.addPlayer("Chet");
        aGame.addPlayer("Pat");
        aGame.addPlayer("Sue");

        playGame(aGame, args);

    }

    public static void playGame(Game aGame, String... args) {
        Random rand = new Random(Integer.parseInt(args[0]));

        do {

            aGame.rollDice(rand.nextInt(5) + 1);

            if (rand.nextInt(9) == 7) {
                gameOn = aGame.wrongAnswer();
            } else {
                gameOn = aGame.correctAnswer();
            }

        } while (gameOn);
    }
}
