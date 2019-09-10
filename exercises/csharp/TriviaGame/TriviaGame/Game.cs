using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace TriviaGame
{
    public class Game
    {
        List<string> players = new List<string>();

        int[] places = new int[6];
        int[] purses = new int[6];

        bool[] inPenaltyBox = new bool[6];

        private Dictionary<string, LinkedList<string>> questionCategories = new Dictionary<string, LinkedList<string>>();

        int currentPlayer = 0;
        bool isGettingOutOfPenaltyBox;

        public Game()
        {
            questionCategories.Add("Pop", new LinkedList<string>());
            questionCategories.Add("Science", new LinkedList<string>());
            questionCategories.Add("Sports", new LinkedList<string>());
            questionCategories.Add("Rock", new LinkedList<string>());

            for (int questionNo = 0; questionNo < 50; questionNo++)
            {
                questionCategories["Pop"].AddLast("Pop Question " + questionNo);
                questionCategories["Science"].AddLast(("Science Question " + questionNo));
                questionCategories["Sports"].AddLast(("Sports Question " + questionNo));
                questionCategories["Rock"].AddLast("Rock Question " + questionNo);
            }
        }

        public bool IsPlayable()
        {
            return (GetTotalPlayers() >= 2);
        }

        public bool AddPlayer(String playerName)
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
                    isGettingOutOfPenaltyBox = false;
                    return;
                }
                isGettingOutOfPenaltyBox = true;
                Console.WriteLine(players[currentPlayer] + " is getting out of the penalty box");
            }
            MovePlayer(roll);
        }

        private void MovePlayer(int roll)
        {
            places[currentPlayer] = places[currentPlayer] + roll;
            if (places[currentPlayer] > 11) places[currentPlayer] = places[currentPlayer] - 12;

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


        private String CurrentCategory()
        {
            var remainder = places[currentPlayer] % questionCategories.Count;

            switch (remainder)
            {
                case 0:
                    return "Pop";
                case 1:
                    return "Science";
                case 2:
                    return "Sports";
                default:
                    return "Rock";
            }
        }

        public bool CheckForWinner()
        {
            var continueGame = true;
            if (inPenaltyBox[currentPlayer])
            {
                if (isGettingOutOfPenaltyBox)
                {
                    Console.WriteLine("Answer was correct!!!!");
                    AddCoin();

                    continueGame = CheckShouldContinueGame();
                    ToNextPlayer();
                    return continueGame;
                }

                ToNextPlayer();
                return true;
            }
            Console.WriteLine("Answer was corrent!!!!");
            AddCoin();
            
            continueGame = CheckShouldContinueGame();
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
            return !(purses[currentPlayer] == 6);
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

}