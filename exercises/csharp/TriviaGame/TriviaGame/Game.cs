using System;
using System.Collections.Generic;
using System.Linq;

namespace TriviaGame
{
    public class Game
    {
        private const int maxPlayers = 6;
        private const int maxCoins = 6;
        private const int minimumPlayers = 2;
        private int numberOfQuestions = 50;

        private const string popCategory = "Pop";
        private const string sportsCategory = "Sports";
        private const string scienceCategory = "Science";
        private const string rockCategory = "Rock";

        List<string> playerNames = new List<string>();
        LinkedList<string> popQuestions = new LinkedList<string>();
        LinkedList<string> scienceQuestions = new LinkedList<string>();
        LinkedList<string> sportsQuestions = new LinkedList<string>();
        LinkedList<string> rockQuestions = new LinkedList<string>();

        private int currentPlayer;
        bool isCurrentPlayerLeavingPenaltyBox;
        int[] playerPlace = new int[maxPlayers];
        int[] playerPurse = new int[maxPlayers];
        bool[] inPenaltyBox = new bool[maxPlayers];
        private int _totalPlaces;

        public Game()
        {
            for (int i = 0; i < numberOfQuestions; i++)
            {
                popQuestions.AddLast(CreateQuestion(popCategory, i));
                scienceQuestions.AddLast(CreateQuestion(scienceCategory, i));
                sportsQuestions.AddLast(CreateQuestion(sportsCategory, i));
                rockQuestions.AddLast(CreateQuestion(rockCategory, i));
            }
        }

        public string CreateQuestion(string category, int index)
        {
            return $"{category} Question {index}";
        }

        public bool HasEnoughPlayers()
        {
            return (GetTotalPlayers() >= minimumPlayers);
        }

        public bool AddPlayer(string playerName)
        {
            playerNames.Add(playerName);
            inPenaltyBox[GetTotalPlayers()] = false;

            Console.WriteLine(playerName + " was added");
            Console.WriteLine("They are player number " + playerNames.Count);
            return true;
        }

        public int GetTotalPlayers()
        {
            return playerNames.Count;
        }

        public void ProcessPlayerTurn(int rollResult)
        {
            Console.WriteLine(playerNames[currentPlayer] + " is the current player");
            Console.WriteLine("They have rolled a " + rollResult);
            
            _totalPlaces = 12;
            if (inPenaltyBox[currentPlayer])
            {
                if (isOdd(rollResult))
                {
                    isCurrentPlayerLeavingPenaltyBox = true;

                    SetPenaltyBoxStatus(isCurrentPlayerLeavingPenaltyBox);
                    
                    MoveCurrentPlayer(rollResult);
                    AskQuestion();
                }
                else
                {
                    isCurrentPlayerLeavingPenaltyBox = false;
                    SetPenaltyBoxStatus(isCurrentPlayerLeavingPenaltyBox);
                }
            }
            else
            { 
                MoveCurrentPlayer(rollResult);
                AskQuestion();
            }

        }

        // Can refactor into two methods, for sake of clarity
        private void SetPenaltyBoxStatus(bool status)
        {
            if (status)
            {
                Console.WriteLine(playerNames[currentPlayer] + " is getting out of the penalty box");
            }
            else
            {
                Console.WriteLine(playerNames[currentPlayer] + " is not getting out of the penalty box");
            }
        }

        private void MoveCurrentPlayer(int rollResult)
        {
            playerPlace[currentPlayer] += rollResult;
            if (playerPlace[currentPlayer] >= _totalPlaces) playerPlace[currentPlayer] -= _totalPlaces;
            Console.WriteLine(playerNames[currentPlayer] + "'s new location is " + playerPlace[currentPlayer]);
            Console.WriteLine("The category is " + GetCurrentCategory());
        }

        private void AskQuestion()
        {
            var currentQuestions = GetCategoryQuestions();

                Console.WriteLine(currentQuestions.First());
                currentQuestions.RemoveFirst();
        }

        private LinkedList<string> GetCategoryQuestions()
        {
            switch (GetCurrentCategory())
            {
                case "Pop":
                    return popQuestions;
                case "Science":
                    return scienceQuestions;
                case "Sports":
                    return sportsQuestions;
                default :
                    return rockQuestions;
            }
        }

        private string GetCurrentCategory()
        {
            if (playerPlace[currentPlayer] % 4 == 0)
            {
                return popCategory;
            }

            if (playerPlace[currentPlayer] % 4 == 1)
            {
                return scienceCategory;
            }

            if (playerPlace[currentPlayer] % 4 == 2)
            {
                return sportsCategory;
            }

            return rockCategory;
        }

        public bool IsCorrectAnswer()
        {
            if (inPenaltyBox[currentPlayer])
            {
                if (isCurrentPlayerLeavingPenaltyBox)
                {
                    Console.WriteLine("Answer was correct!!!!");
                    playerPurse[currentPlayer]++;
                    Console.WriteLine(playerNames[currentPlayer] + " now has " + playerPurse[currentPlayer] + " Gold Coins.");

                    bool winner = DidPlayerWin();
                    MoveToNextPlayer();

                    return winner;
                }
                else
                {
                    MoveToNextPlayer();
                    return true;
                }
            }
            else
            {
                Console.WriteLine("Answer was corrent!!!!");
                playerPurse[currentPlayer]++;
                Console.WriteLine(playerNames[currentPlayer] + " now has " + playerPurse[currentPlayer] + " Gold Coins.");

                bool winner = DidPlayerWin();
                MoveToNextPlayer();

                return winner;
            }
        }

        public bool IsWrongAnswer()
        {
            Console.WriteLine("Question was incorrectly answered");
            Console.WriteLine(playerNames[currentPlayer] + " was sent to the penalty box");
            inPenaltyBox[currentPlayer] = true;

            MoveToNextPlayer();

            return true;
        }

        private void MoveToNextPlayer()
        {
            currentPlayer++;
            if (currentPlayer == playerNames.Count) currentPlayer = 0;
        }

        private bool DidPlayerWin()
        {
            return playerPurse[currentPlayer] != maxCoins;
        }

        private static bool isOdd(int rollResult)
        {
            return rollResult % 2 != 0;
        }
    }

}