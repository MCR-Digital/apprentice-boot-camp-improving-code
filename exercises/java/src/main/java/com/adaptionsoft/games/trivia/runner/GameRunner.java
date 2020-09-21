
package com.adaptionsoft.games.trivia.runner;
import com.adaptionsoft.games.uglytrivia.Game;

import java.util.Random;


public class GameRunner {

	private static boolean notAWinner;

	public static void main(String[] args) {
		Game game = new Game();
		
		game.addPlayer("Chet");
		game.addPlayer("Pat");
		game.addPlayer("Sue");
		
		Random randomNumber = new Random(Integer.parseInt(args[0]));

	
		do {
			
			game.roll(randomNumber.nextInt(5) + 1);
			
			if (randomNumber.nextInt(9) == 7) {
				notAWinner = game.wrongAnswer();
			} else {
				notAWinner = game.wasCorrectlyAnswered();
			}
			
			
			
		} while (notAWinner);
		
	}
}
