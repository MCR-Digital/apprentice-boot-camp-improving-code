using System;
using System.CodeDom;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace TriviaGame
{
    public class Game
    {
        private readonly List<string> players = new List<string>();
                
        private readonly int[] places = new int[6];
        private readonly int[] purses = new int[6];

        private readonly bool[] inPenaltyBox = new bool[6];

        private readonly Dictionary<Category, LinkedList<string>> questionCategories = new Dictionary<Category, LinkedList<string>>
        {
            { Category.Pop, new LinkedList<string>() },
            { Category.Science, new LinkedList<string>() },
            { Category.Sports, new LinkedList<string>() },
            { Category.Rock, new LinkedList<string>() }
        };

        private int currentPlayer;
        private bool skipPlayersTurn;

        private const int TotalCategories = 4;
        private const int TotalPlaces = 12;
        private const int TotalQuestions = 50;
        private const int WinAmount = 6;


        public Game()
        {
            for (int questionNumber = 0; questionNumber < TotalQuestions; questionNumber++)
            {
                questionCategories[Category.Pop].AddLast("Pop Question " + questionNumber);
                questionCategories[Category.Science].AddLast(("Science Question " + questionNumber));
                questionCategories[Category.Sports].AddLast(("Sports Question " + questionNumber));
                questionCategories[Category.Rock].AddLast("Rock Question " + questionNumber);
            }
        }

        public bool IsPlayable()
        {
            return (GetTotalPlayers() >= 2);
        }

        public bool AddPlayer(string playerName)
        {
            players.Add(playerName);
            places[GetTotalPlayers()] = 0;
            purses[GetTotalPlayers()] = 0;
            inPenaltyBox[GetTotalPlayers()] = false;

            Console.WriteLine(playerName + " was added");
            Console.WriteLine("They are player number " + players.Count);
            return true;
        }

        public int GetTotalPlayers()
        {
            return players.Count;
        }

        public void Roll(int roll)
        {
            Console.WriteLine(players[currentPlayer] + " is the current player");
            Console.WriteLine("They have rolled a " + roll);

            if (inPenaltyBox[currentPlayer])
            {
                if (roll % 2 == 0)
                {
                    Console.WriteLine(players[currentPlayer] + " is not getting out of the penalty box");
                    skipPlayersTurn = true;
                    return;
                }
                skipPlayersTurn = false;
                Console.WriteLine(players[currentPlayer] + " is getting out of the penalty box");
            }
            MovePlayer(roll);
        }

        private void MovePlayer(int roll)
        {
            places[currentPlayer] = (places[currentPlayer] + roll) % TotalPlaces;

            Console.WriteLine(players[currentPlayer]
                              + "'s new location is "
                              + places[currentPlayer]);
            Console.WriteLine("The category is " + CurrentCategory());
            AskQuestion();
        }

        private void AskQuestion()
        {
            var cat = CurrentCategory();

            Console.WriteLine(questionCategories[cat].First());
            questionCategories[cat].RemoveFirst();
        }


        private Category CurrentCategory()
        {
            var remainder = places[currentPlayer] % TotalCategories;

            switch (remainder)
            {
                case 0:
                    return Category.Pop;
                case 1:
                    return Category.Science;
                case 2:
                    return Category.Sports;
                default:
                    return Category.Rock;
            }
        }

        public bool CheckForWinner()
        {
            if (inPenaltyBox[currentPlayer] && skipPlayersTurn)
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

        public bool WrongAnswer()
        {
            Console.WriteLine("Question was incorrectly answered");
            Console.WriteLine(players[currentPlayer] + " was sent to the penalty box");
            inPenaltyBox[currentPlayer] = true;

            currentPlayer++;
            if (currentPlayer == players.Count) currentPlayer = 0;
            return true;
        }


        private bool CheckShouldContinueGame()
        {
            return !(purses[currentPlayer] == WinAmount);
        }

        private void ToNextPlayer()
        {
            currentPlayer++;
            if (currentPlayer == players.Count) currentPlayer = 0;
        }

        private void AddCoin()
        {
            purses[currentPlayer]++;
            Console.WriteLine(players[currentPlayer]
                              + " now has "
                              + purses[currentPlayer]
                              + " Gold Coins.");
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