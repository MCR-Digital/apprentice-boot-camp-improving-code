
package com.adaptionsoft.games.trivia.runner;
import java.util.Random;

import com.adaptionsoft.games.uglytrivia.Game;


public class GameRunner {

	private static boolean notAWinner;

	public static void main(String[] args) {
		Game mainGame = new Game();

		mainGame.addPlayer("Chet");
		mainGame.addPlayer("Pat");
		mainGame.addPlayer("Sue");
		
		Random rand = new Random(Integer.parseInt(args[0]));
	
		do {
			
			mainGame.takeNextTurn(rand.nextInt(5) + 1);
			
			if (rand.nextInt(9) == 7) {
				notAWinner = mainGame.wrongAnswer();
			} else {
				notAWinner = mainGame.correctAnswer();
			}
			
			
			
		} while (notAWinner);
		
	}
}
