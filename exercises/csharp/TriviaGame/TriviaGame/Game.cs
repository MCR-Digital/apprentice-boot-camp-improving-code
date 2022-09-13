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

        public void Roll(int rollResult)
        {
            Console.WriteLine(playerNames[currentPlayer] + " is the current player");
            Console.WriteLine("They have rolled a " + rollResult);

            _totalPlaces = 12;
            if (inPenaltyBox[currentPlayer])
            {
                if (isEven(rollResult))
                {
                    isCurrentPlayerLeavingPenaltyBox = true;

                    Console.WriteLine(playerNames[currentPlayer] + " is getting out of the penalty box");
                    playerPlace[currentPlayer] += rollResult;
                    if (playerPlace[currentPlayer] >= _totalPlaces) playerPlace[currentPlayer] -= _totalPlaces;

                    Console.WriteLine(playerNames[currentPlayer]
                            + "'s new location is "
                            + playerPlace[currentPlayer]);
                    Console.WriteLine("The category is " + GetCurrentCategory());
                    AskQuestion();
                }
                else
                {
                    Console.WriteLine(playerNames[currentPlayer] + " is not getting out of the penalty box");
                    isCurrentPlayerLeavingPenaltyBox = false;
                }
            }
            else
            {
                playerPlace[currentPlayer] += rollResult;
                if (playerPlace[currentPlayer] >= _totalPlaces) playerPlace[currentPlayer] -= _totalPlaces;

                Console.WriteLine(playerNames[currentPlayer]
                        + "'s new location is "
                        + playerPlace[currentPlayer]);
                Console.WriteLine("The category is " + GetCurrentCategory());
                AskQuestion();
            }

        }

        private void AskQuestion()
        {
            if (GetCurrentCategory() == popCategory)
            {
                Console.WriteLine(popQuestions.First());
                popQuestions.RemoveFirst();
            }
            if (GetCurrentCategory() == scienceCategory)
            {
                Console.WriteLine(scienceQuestions.First());
                scienceQuestions.RemoveFirst();
            }
            if (GetCurrentCategory() == sportsCategory)
            {
                Console.WriteLine(sportsQuestions.First());
                sportsQuestions.RemoveFirst();
            }
            if (GetCurrentCategory() == rockCategory)
            {
                Console.WriteLine(rockQuestions.First());
                rockQuestions.RemoveFirst();
            }
        }


        private string GetCurrentCategory()
        {
            switch (playerPlace[currentPlayer])
                {
                    case 0:
                    case 4:
                    case 8:
                        return popCategory;
                    case 1:
                    case 5:
                    case 9:
                        return scienceCategory;
                    case 2:
                    case 6:
                    case 10:
                        return sportsCategory;
                    default:
                        return rockCategory;
                }
            }

        public bool IsCorrectAnswer()
        {
            if (inPenaltyBox[currentPlayer])
            {
                if (isCurrentPlayerLeavingPenaltyBox)
                {
                    Console.WriteLine("Answer was correct!!!!");
                    playerPurse[currentPlayer]++;
                    Console.WriteLine(playerNames[currentPlayer]
                            + " now has "
                            + playerPurse[currentPlayer]
                            + " Gold Coins.");

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
                Console.WriteLine(playerNames[currentPlayer]
                        + " now has "
                        + playerPurse[currentPlayer]
                        + " Gold Coins.");

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

        private static bool isEven(int rollResult)
        {
            return rollResult % 2 != 0;
        }
    }

}