using System;
using System.Collections.Generic;
using System.Linq;

namespace TriviaGame
{
    public class Game
    {
        List<string> playerNames = new List<string>();

        int[] playerPlace = new int[6];
        int[] playerPurse = new int[6];

        bool[] inPenaltyBox = new bool[6];

        LinkedList<string> popQuestions = new LinkedList<string>();
        LinkedList<string> scienceQuestions = new LinkedList<string>();
        LinkedList<string> sportsQuestions = new LinkedList<string>();
        LinkedList<string> rockQuestions = new LinkedList<string>();

        int currentPlayer = 0;
        bool isCurrentPlayerLeavingPenaltyBox;

        public Game()
        {
            for (int i = 0; i < 50; i++)
            {
                popQuestions.AddLast(CreateQuestion("Pop", i));
                scienceQuestions.AddLast(CreateQuestion("Science", i));
                sportsQuestions.AddLast(CreateQuestion("Sports", i));
                rockQuestions.AddLast(CreateQuestion("Rock", i));
            }
        }

        public string CreateQuestion(string category, int index)
        {
            return $"{category} Question {index}";
        }

        public bool HasEnoughPlayers()
        {
            return (GetTotalPlayers() >= 2);
        }

        public bool AddPlayer(string playerName)
        {
            playerNames.Add(playerName);
            playerPlace[GetTotalPlayers()] = 0;
            playerPurse[GetTotalPlayers()] = 0;
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

            if (inPenaltyBox[currentPlayer])
            {
                if (rollResult % 2 != 0)
                {
                    isCurrentPlayerLeavingPenaltyBox = true;

                    Console.WriteLine(playerNames[currentPlayer] + " is getting out of the penalty box");
                    playerPlace[currentPlayer] += rollResult;
                    if (playerPlace[currentPlayer] > 11) playerPlace[currentPlayer] -= 12;

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
                if (playerPlace[currentPlayer] > 11) playerPlace[currentPlayer] -= 12;

                Console.WriteLine(playerNames[currentPlayer]
                        + "'s new location is "
                        + playerPlace[currentPlayer]);
                Console.WriteLine("The category is " + GetCurrentCategory());
                AskQuestion();
            }

        }

        private void AskQuestion()
        {
            if (GetCurrentCategory() == "Pop")
            {
                Console.WriteLine(popQuestions.First());
                popQuestions.RemoveFirst();
            }
            if (GetCurrentCategory() == "Science")
            {
                Console.WriteLine(scienceQuestions.First());
                scienceQuestions.RemoveFirst();
            }
            if (GetCurrentCategory() == "Sports")
            {
                Console.WriteLine(sportsQuestions.First());
                sportsQuestions.RemoveFirst();
            }
            if (GetCurrentCategory() == "Rock")
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
                    return "Pop";
                case 1:
                case 5:
                case 9:
                    return "Science";
                case 2:
                case 6:
                case 10:
                    return "Sports";
                default:
                    return "Rock";
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
                    currentPlayer++;
                    IfLastPlayerMoveToFirstPlayer();

                    return winner;
                }
                else
                {
                    currentPlayer++;
                    IfLastPlayerMoveToFirstPlayer();
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
                currentPlayer++;
                IfLastPlayerMoveToFirstPlayer();

                return winner;
            }
        }

        public bool IsWrongAnswer()
        {
            Console.WriteLine("Question was incorrectly answered");
            Console.WriteLine(playerNames[currentPlayer] + " was sent to the penalty box");
            inPenaltyBox[currentPlayer] = true;

            currentPlayer++;
            IfLastPlayerMoveToFirstPlayer();
            return true;
        }

        private int IfLastPlayerMoveToFirstPlayer()
        {
            if (currentPlayer == playerNames.Count)
                return currentPlayer = 0;
            else return currentPlayer;
        }

        private bool DidPlayerWin()
        {
            return !(playerPurse[currentPlayer] == 6);
        }
    }

}