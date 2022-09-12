using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

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
                popQuestions.AddLast("Pop Question " + i);
                scienceQuestions.AddLast(("Science Question " + i));
                sportsQuestions.AddLast(("Sports Question " + i));
                rockQuestions.AddLast(CreateRockQuestion(i));
            }
        }

        public string CreateRockQuestion(int index)
        {
            return "Rock Question " + index;
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
                    playerPlace[currentPlayer] = playerPlace[currentPlayer] + rollResult;
                    if (playerPlace[currentPlayer] > 11) playerPlace[currentPlayer] = playerPlace[currentPlayer] - 12;

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

                playerPlace[currentPlayer] = playerPlace[currentPlayer] + rollResult;
                if (playerPlace[currentPlayer] > 11) playerPlace[currentPlayer] = playerPlace[currentPlayer] - 12;

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
            if (playerPlace[currentPlayer] == 0) return "Pop";
            if (playerPlace[currentPlayer] == 4) return "Pop";
            if (playerPlace[currentPlayer] == 8) return "Pop";
            if (playerPlace[currentPlayer] == 1) return "Science";
            if (playerPlace[currentPlayer] == 5) return "Science";
            if (playerPlace[currentPlayer] == 9) return "Science";
            if (playerPlace[currentPlayer] == 2) return "Sports";
            if (playerPlace[currentPlayer] == 6) return "Sports";
            if (playerPlace[currentPlayer] == 10) return "Sports";
            return "Rock";
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
                    if (currentPlayer == playerNames.Count) currentPlayer = 0;

                    return winner;
                }
                else
                {
                    currentPlayer++;
                    if (currentPlayer == playerNames.Count) currentPlayer = 0;
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
                if (currentPlayer == playerNames.Count) currentPlayer = 0;

                return winner;
            }
        }

        public bool IsWrongAnswer()
        {
            Console.WriteLine("Question was incorrectly answered");
            Console.WriteLine(playerNames[currentPlayer] + " was sent to the penalty box");
            inPenaltyBox[currentPlayer] = true;

            currentPlayer++;
            if (currentPlayer == playerNames.Count) currentPlayer = 0;
            return true;
        }


        private bool DidPlayerWin()
        {
            return !(playerPurse[currentPlayer] == 6);
        }
    }

}