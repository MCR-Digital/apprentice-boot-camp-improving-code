
package com.adaptionsoft.games.trivia.runner;
import java.util.Random;

import com.adaptionsoft.games.uglytrivia.Game;


public class GameRunner {

	private static boolean isNotAWinner;

	public static void main(String[] args) {
		Game triviaGame = new Game();
		triviaGame.addPlayersToGame("Chet", "Pat", "Sue");

		int seed = Integer.parseInt(args[0]);
		Random randomNumber = new Random(seed);

		do {
			int randomGeneratedNumber = randomNumber.nextInt(5) + 1;
			triviaGame.takeTurn(randomGeneratedNumber);

			if (randomNumber.nextInt(9) == 7) {
				isNotAWinner = triviaGame.isWrongAnswer();
			} else {
				isNotAWinner = triviaGame.isCorrectAnswer();
			}

		} while (isNotAWinner);
	}
}
