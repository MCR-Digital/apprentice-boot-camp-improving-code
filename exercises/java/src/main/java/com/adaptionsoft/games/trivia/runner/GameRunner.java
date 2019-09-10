
package com.adaptionsoft.games.trivia.runner;
import java.util.Random;

import com.adaptionsoft.games.uglytrivia.Game;


public class GameRunner {

	private static boolean notAWinner;

	public static void main(String[] args) {
		Game triviaGame = new Game();
		
		triviaGame.addPlayerToGame("Chet");
		triviaGame.addPlayerToGame("Pat");
		triviaGame.addPlayerToGame("Sue");
		
		Random randomNumber = new Random(Integer.parseInt(args[0]));
	
		do {
			
			triviaGame.takeTurn(randomNumber.nextInt(5) + 1);
			
			if (randomNumber.nextInt(9) == 7) {
				notAWinner = triviaGame.wrongAnswer();
			} else {
				notAWinner = triviaGame.wasCorrectlyAnswered();
			}
			
			
			
		} while (notAWinner);
		
	}
}
