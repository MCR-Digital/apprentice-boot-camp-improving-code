<?php

namespace trivia;

class GameRunner {
  public function runGame($seed): void
  {
    $notAWinner;
    
    $aGame = new Game();
    
    $aGame->add("Chet");
    $aGame->add("Pat");
    $aGame->add("Sue");
    
    srand($seed);
    do {
      
      $aGame->roll(rand(0,5) + 1);
      
      if (rand(0,9) == 7) {
        $notAWinner = $aGame->wrongAnswer();
      } else {
        $notAWinner = $aGame->wasCorrectlyAnswered();
      }
      
      
      
    } while ($notAWinner);
  }
}