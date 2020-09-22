using System;

namespace TriviaGame
{
    public class Game
    {
        Questions questions;
        Players players;

        public Game(params string[] playerNames)
        {
            if (playerNames.Length < 2)
            {
                throw new System.ArgumentException("The game needs a minumum of 2 players!");
            }
            questions = new Questions();
            players = new Players();
            foreach (var playerName in playerNames)
            {
                players.AddPlayer(playerName);
            }
        }

        public void ProcessRoll(int diceResult)
        {
            players.GetCurrentPlayer();
            Console.WriteLine(players.currentPlayer.Name + " is the current player");
            Console.WriteLine("They have rolled a " + diceResult);

            if (players.currentPlayer.InPenaltyBox && !IsDiceResultOdd(diceResult))
            {
                Console.WriteLine(players.currentPlayer.Name + " is not getting out of the penalty box");
                players.currentPlayer.GettingOutOfPenaltyBox = false;

            }
            else
            {
                if (players.currentPlayer.InPenaltyBox)
                {
                    players.currentPlayer.GettingOutOfPenaltyBox = true;
                    Console.WriteLine(players.currentPlayer.Name + " is getting out of the penalty box");
                }

                MovePlayer(diceResult);
                questions.AskQuestion(players.currentPlayer.Place);
            }
        }

        public void MovePlayer(int numberOfSpaces)
        {
            players.MoveCurrentPlayer(numberOfSpaces);

            Console.WriteLine(players.currentPlayer.Name
                    + "'s new location is "
                    + players.currentPlayer.Place);
            Console.WriteLine("The category is " + questions.GetCurrentQuestionCategory(players.currentPlayer.Place));
        }

        public bool IsDiceResultOdd(int diceResult)
        {
            return !(diceResult % 2 == 0);
        }

        public bool CorrectAnswer()
        {
            if (players.currentPlayer.InPenaltyBox && !players.currentPlayer.GettingOutOfPenaltyBox)
            {
                players.NextPlayer();
                return true;
            }
            else
            {

                Console.WriteLine("Answer was correct!!!!");
                players.currentPlayer.Purse++;
                Console.WriteLine(players.currentPlayer.Name
                        + " now has "
                        + players.currentPlayer.Purse
                        + " Gold Coins.");

                bool isPlayerWinner = players.currentPlayer.DidPlayerWin();
                players.NextPlayer();

                return isPlayerWinner;
            }
        }

        public bool WrongAnswer()
        {
            Console.WriteLine("Question was incorrectly answered");
            Console.WriteLine(players.currentPlayer.Name + " was sent to the penalty box");
            players.currentPlayer.InPenaltyBox = true;
            players.NextPlayer();

            return true;
        }
    }

}