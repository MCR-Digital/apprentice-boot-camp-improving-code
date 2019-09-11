using System;
using System.Collections.Generic;
using System.Linq;

namespace TriviaGame
{
    public class Game
    {
        private readonly List<Player> players = new List<Player>();

        private readonly Deck deck;
        private Player currentPlayer;
        private int playerIndex;

        private const int TotalQuestions = 50;
        private const int WinAmount = 6;
        
        public Game()
        {
            deck = new Deck(TotalQuestions);
        }

        public bool AddPlayer(string playerName)
        {
            players.Add( new Player(playerName) );

            Console.WriteLine(playerName + " was added");
            Console.WriteLine("They are player number " + players.Count);
            return true;
        }

        public void StartANewTurn(Random random)
        {
            currentPlayer = players[playerIndex];
            var roll = GameHandler.RollDice(currentPlayer, random);
            var newSpace = GameHandler.MovePlayer(currentPlayer, roll);
            var category = GameHandler.GetCategoryAtSpace(currentPlayer.CurrentSpace);
            var question = GameHandler.DrawQuestion(currentPlayer.SkipTurn, deck, category);

            OutputProcess(roll, newSpace, category, question);
        }

        public bool AnswerQuestion(Random random)
        {
            return random.Next(9) == 7 ? WrongAnswer() : CheckForWinner();
        }

        private bool CheckForWinner()
        {
            if (currentPlayer.IsInPenaltyBox && currentPlayer.SkipTurn)
            {
                ToNextPlayer();
                return true;
            }

            Console.WriteLine("Answer was correct!!!!");
            AddCoin();

            var continueGame = CheckShouldContinueGame();
            ToNextPlayer();

            return continueGame;
        }

        private bool WrongAnswer()
        {
            Console.WriteLine("Question was incorrectly answered");
            Console.WriteLine(currentPlayer.Name + " was sent to the penalty box");
            currentPlayer.IsInPenaltyBox = true;

            ToNextPlayer();
            return true;
        }
        
        private bool CheckShouldContinueGame()
        {
            return currentPlayer.Coins != WinAmount;
        }

        private void OutputProcess(int roll, int newSpace, Category category, string question)
        {
            Console.WriteLine(currentPlayer.Name + " is the current player");
            Console.WriteLine("They have rolled a " + roll);

            if (currentPlayer.IsInPenaltyBox)
            {
                if (roll % 2 == 0)
                {
                    Console.WriteLine(currentPlayer.Name + " is not getting out of the penalty box");
                    return;
                }
                Console.WriteLine(currentPlayer.Name + " is getting out of the penalty box");
            }

            Console.WriteLine(currentPlayer.Name + "'s new location is " + newSpace);
            Console.WriteLine("The category is " + category);
            Console.WriteLine(question);
        }

        private void ToNextPlayer()
        {
            playerIndex++;
            if (playerIndex == players.Count) playerIndex = 0;
        }

        private void AddCoin()
        {
            currentPlayer.Coins++;
            Console.WriteLine(currentPlayer.Name + " now has " + currentPlayer.Coins + " Gold Coins.");
        }
    }

    public enum Category
    {
        Pop,
        Science,
        Sports,
        Rock
    }
}