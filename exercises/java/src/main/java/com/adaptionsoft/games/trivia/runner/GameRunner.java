
package com.adaptionsoft.games.trivia.runner;
import java.util.Random;

import com.adaptionsoft.games.uglytrivia.Game;


public class GameRunner {

	private static boolean gameContinues;

	public static void main(String[] args) {
		Game aGame = new Game();
		
		aGame.addPlayer("Chet");
		aGame.addPlayer("Pat");
		aGame.addPlayer("Sue");
		
		Random rand = new Random(Integer.parseInt(args[0]));
	
		do {

			aGame.rollDice(rand.nextInt(5) + 1);
			
			if (rand.nextInt(9) == 7) {
				gameContinues = aGame.currentPlayerGetsAnswerWrong();
			} else {
				gameContinues = aGame.currentPlayerGetsAnswerRight();
			}

			

		} while (gameContinues);
		
	}
}
