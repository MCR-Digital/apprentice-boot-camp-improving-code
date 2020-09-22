using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace TriviaGame
{
    public class Game
    {
        List<Player> players = new List<Player>();
        Questions questions = new Questions();
        int currentPlayerIndex = 0;
        Player currentPlayer;

        public Game()
        {
            
        }

        public void AddPlayer(string playerName)
        {
            players.Add(new Player(playerName));

            Console.WriteLine(playerName + " was added");
            Console.WriteLine("They are player number " + GetPlayerCount());
        }

        public int GetPlayerCount()
        {
            return players.Count;
        }

        public Player GetCurrentPlayer(int currentPlayerIndex)
        {
            return players[currentPlayerIndex];
        }

        public void ProcessRoll(int diceResult)
        {
            currentPlayer = GetCurrentPlayer(currentPlayerIndex);
            Console.WriteLine(currentPlayer.Name + " is the current player");
            Console.WriteLine("They have rolled a " + diceResult);

            if (currentPlayer.InPenaltyBox)
            {
                if (IsDiceResultOdd(diceResult))
                {
                    currentPlayer.GettingOutOfPenaltyBox = true;
                    Console.WriteLine(currentPlayer.Name + " is getting out of the penalty box");

                    MovePlayer(diceResult);
                    questions.AskQuestion(currentPlayer.Place);
                }
                else
                {
                    Console.WriteLine(currentPlayer.Name + " is not getting out of the penalty box");
                    currentPlayer.GettingOutOfPenaltyBox = false;
                }

            }
            else
            {
                MovePlayer(diceResult);
                questions.AskQuestion(currentPlayer.Place);
            }
        }

        public void MovePlayer(int numberOfSpaces)
        {
            var gameBoardSize = 12;

            currentPlayer.Place = currentPlayer.Place + numberOfSpaces;
            if (currentPlayer.Place > gameBoardSize - 1) currentPlayer.Place = currentPlayer.Place - gameBoardSize;

            Console.WriteLine(currentPlayer.Name
                    + "'s new location is "
                    + currentPlayer.Place);
            Console.WriteLine("The category is " + questions.GetCurrentQuestionCategory(currentPlayer.Place));
        }

        public bool IsDiceResultOdd(int diceResult)
        {
            return !(diceResult % 2 == 0);
        }

        public bool CorrectAnswer()
        {
            if (currentPlayer.InPenaltyBox && !currentPlayer.GettingOutOfPenaltyBox)
            {
                    NextPlayer();
                    return true;
            }
            else
            {

                Console.WriteLine("Answer was correct!!!!");
                currentPlayer.Purse++;
                Console.WriteLine(currentPlayer.Name
                        + " now has "
                        + currentPlayer.Purse
                        + " Gold Coins.");

                bool isPlayerWinner = currentPlayer.DidPlayerWin();
                NextPlayer();

                return isPlayerWinner;
            }
        }

        public bool WrongAnswer()
        {
            Console.WriteLine("Question was incorrectly answered");
            Console.WriteLine(currentPlayer.Name + " was sent to the penalty box");
            currentPlayer.InPenaltyBox = true;
            NextPlayer();

            return true;
        }

        public void NextPlayer()
        {
            currentPlayerIndex++;
            if (currentPlayerIndex == GetPlayerCount()) currentPlayerIndex = 0;
        }
    }

}