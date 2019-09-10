
package com.adaptionsoft.games.trivia.runner;
import java.util.Random;

import com.adaptionsoft.games.uglytrivia.Game;


public class GameRunner {

	private static boolean notAWinner;

	public static void main(String[] args) {
		Game aGame = new Game();
		
		aGame.addPlayer("Chet");
		aGame.addPlayer("Pat");
		aGame.addPlayer("Sue");
		
		Random randomNumber = new Random(Integer.parseInt(args[0]));
	
		do {
			
			aGame.rollDice(randomNumber.nextInt(5) + 1);
			
			if (randomNumber.nextInt(9) == 7) {
				notAWinner = aGame.isIncorrectlyAnswered();
			} else {
				notAWinner = aGame.isCorrectlyAnswered();
			}
			
			
			
		} while (notAWinner);
		
	}
}
