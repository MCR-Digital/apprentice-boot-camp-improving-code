
package com.adaptionsoft.games.trivia.runner;
import java.util.Random;

import com.adaptionsoft.games.uglytrivia.Game;


public class GameRunner {

	private static boolean isNotAWinner;

	public static void main(String[] args) {
		Game aGame = new Game();
		
		aGame.addPlayer("Chet");
		aGame.addPlayer("Pat");
		aGame.addPlayer("Sue");
		
		Random rand = new Random(Integer.parseInt(args[0]));
	
		do {
			
			aGame.processRoll(rand.nextInt(5) + 1);
			
			if (rand.nextInt(9) == 7) {
				isNotAWinner = aGame.handleIncorrectAnswer();
			} else {
				isNotAWinner = aGame.handleCorrectAnswer();
			}
			
			
			
		} while (isNotAWinner);
		
	}
}
