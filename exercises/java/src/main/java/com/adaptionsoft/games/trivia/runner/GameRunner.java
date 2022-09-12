
package com.adaptionsoft.games.trivia.runner;
import java.util.Random;

import com.adaptionsoft.games.uglytrivia.Game;


public class GameRunner {

	private static boolean isAWinner;

	public static void main(String[] args) {
		Game aGame = new Game();
		
		aGame.addNewPlayer("Chet");
		aGame.addNewPlayer("Pat");
		aGame.addNewPlayer("Sue");
		
		Random rand = new Random(Integer.parseInt(args[0]));
	
		do {
			
			aGame.roll(rand.nextInt(5) + 1);
			
			if (rand.nextInt(9) == 7) {
				isAWinner = aGame.wrongAnswer();
			} else {
				isAWinner = aGame.wasCorrectlyAnswered();
			}
			
			
			
		} while (isAWinner);
		
	}
}
