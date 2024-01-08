<?php

namespace trivia;

class GameRunner {
  public function runGame($seed): void
  {
    $notAWinner;
    
    $aGame = new Game();
    
    $aGame->addPlayer("Chet");
    $aGame->addPlayer("Pat");
    $aGame->addPlayer("Sue");
    
    srand($seed);
    do {
      
      $aGame->rollDice(rand(0,5) + 1);
      
      if (rand(0,9) == 7) {
        $notAWinner = $aGame->wrongAnswer();
      } else {
        $notAWinner = $aGame->wasCorrectlyAnswered();
      }
      
      
      
    } while ($notAWinner);
  }
}