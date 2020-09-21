using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace TriviaGame
{
    public class Game
    {
        // Place all of the models in a separate file
        // Add all models through dependency injection?

        List<string> totalPlayers = new List<string>();

        private readonly int totalQuestions = 50;

        private readonly string popCategory = "Pop";
        private readonly string scienceCategory = "Science";
        private readonly string sportsCategory = "Sports";
        private readonly string rockCategory = "Rock";

        int[] playerBoardPositionState = new int[6];
        int[] playerPurseTotalState = new int[6];

        bool[] playerPenaltyBoxState = new bool[6];

        LinkedList<string> popQuestions = new LinkedList<string>();
        LinkedList<string> scienceQuestions = new LinkedList<string>();
        LinkedList<string> sportsQuestions = new LinkedList<string>();
        LinkedList<string> rockQuestions = new LinkedList<string>();

        int currentPlayer = 0;
        bool isGettingOutOfPenaltyBox;

        public Game()
        {
            for (int i = 0; i < totalQuestions; i++)
            {
                popQuestions.AddLast($"{popCategory} Question " + i);
                scienceQuestions.AddLast(($"{scienceCategory} Question " + i));
                sportsQuestions.AddLast(($"{sportsCategory} Question " + i));
                rockQuestions.AddLast(($"{rockCategory} Question " + i));

            }
        }

        public bool IsPlayable()
        {
            return (GetTotalPlayersCount() >= 2);
        }

        public bool AddPlayer(string playerName)
        {


            totalPlayers.Add(playerName);
            playerBoardPositionState[GetTotalPlayersCount()] = 0;
            playerPurseTotalState[GetTotalPlayersCount()] = 0;
            playerPenaltyBoxState[GetTotalPlayersCount()] = false;

            Console.WriteLine(playerName + " was added");
            Console.WriteLine("They are player number " + totalPlayers.Count);
            return true;
        }

        public int GetTotalPlayersCount()
        {
            return totalPlayers.Count;
        }

        private static bool IsRollResultOdd(int rollResult)
        {
            if (rollResult % 2 != 0)
            {
                return true;
            }
            else
            {
                return false;
            }
        }

        public void RollDice(int rollResult)
        {
            Console.WriteLine(totalPlayers[currentPlayer] + " is the current player");
            Console.WriteLine("They have rolled a " + rollResult);

            if (playerPenaltyBoxState[currentPlayer])
            {
                if (IsRollResultOdd(rollResult))
                {
                    isGettingOutOfPenaltyBox = true;

                    Console.WriteLine(totalPlayers[currentPlayer] + " is getting out of the penalty box");

                    playerBoardPositionState[currentPlayer] = UpdatePlayerPositionState(playerBoardPositionState[currentPlayer], rollResult);

                    if (playerBoardPositionState[currentPlayer] > 11) playerBoardPositionState[currentPlayer] = playerBoardPositionState[currentPlayer] - 12;

                    Console.WriteLine(totalPlayers[currentPlayer]
                            + "'s new location is "
                            + playerBoardPositionState[currentPlayer]);
                    Console.WriteLine("The category is " + FindCurrentQuestionCategory());
                    AskQuestion();
                }
                else
                {
                    Console.WriteLine(totalPlayers[currentPlayer] + " is not getting out of the penalty box");
                    isGettingOutOfPenaltyBox = false;
                }

            }
            else
            {

                playerBoardPositionState[currentPlayer] = playerBoardPositionState[currentPlayer] + rollResult;
                if (playerBoardPositionState[currentPlayer] > 11) playerBoardPositionState[currentPlayer] = playerBoardPositionState[currentPlayer] - 12;

                Console.WriteLine(totalPlayers[currentPlayer]
                        + "'s new location is "
                        + playerBoardPositionState[currentPlayer]);
                Console.WriteLine("The category is " + FindCurrentQuestionCategory());
                AskQuestion();
            }

        }

        private static int UpdatePlayerPositionState(int currentPlayerPositionState, int rollResult)
        {
            return currentPlayerPositionState + rollResult;
        }

        private void AskQuestion()
        {
            if (FindCurrentQuestionCategory() == popCategory)
            {
                Console.WriteLine(popQuestions.First());
                popQuestions.RemoveFirst();
            }
            if (FindCurrentQuestionCategory() == scienceCategory)
            {
                Console.WriteLine(scienceQuestions.First());
                scienceQuestions.RemoveFirst();
            }
            if (FindCurrentQuestionCategory() == sportsCategory)
            {
                Console.WriteLine(sportsQuestions.First());
                sportsQuestions.RemoveFirst();
            }
            if (FindCurrentQuestionCategory() == rockCategory)
            {
                Console.WriteLine(rockQuestions.First());
                rockQuestions.RemoveFirst();
            }
        }


        private string FindCurrentQuestionCategory()
        {
            var playerPosition = playerBoardPositionState[currentPlayer];

            if (playerPosition == 0 || playerPosition ==  4 || playerPosition == 8)
            {
                return popCategory;
            }
            else if (playerPosition == 1 || playerPosition == 5 || playerPosition == 9)
            {
                return scienceCategory;
            }
            else if (playerPosition == 2 || playerPosition == 6 || playerPosition == 10)
            {
                return sportsCategory;
            }
            else
            {
                return rockCategory;
            }
        }

        public bool WasCorrectlyAnswered()
        {
            if (playerPenaltyBoxState[currentPlayer])
            {
                if (isGettingOutOfPenaltyBox)
                {
                    Console.WriteLine("Answer was correct!!!!");

                    playerPurseTotalState[currentPlayer] = IncreasePlayerPurseTotal(playerPurseTotalState[currentPlayer]);
                    Console.WriteLine(totalPlayers[currentPlayer]
                            + " now has "
                            + playerPurseTotalState[currentPlayer]
                            + " Gold Coins.");

                    bool winner = DidPlayerWin();
                    currentPlayer++;
                    if (currentPlayer == totalPlayers.Count) currentPlayer = 0;

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
                playerPurseTotalState[currentPlayer]++;
                Console.WriteLine(totalPlayers[currentPlayer]
                        + " now has "
                        + playerPurseTotalState[currentPlayer]
                        + " Gold Coins.");

                bool winner = DidPlayerWin();
                MoveToNextPlayer();
                return winner;
            }
        }

        private void MoveToNextPlayer()
        {
            currentPlayer++;
            if (currentPlayer == totalPlayers.Count) currentPlayer = 0;
        }

        private int IncreasePlayerPurseTotal(int currentPlayerPurseTotal)
        {
            var newPlayerPurseTotal = currentPlayerPurseTotal + 1;
            return newPlayerPurseTotal;
        }

        public bool WasIncorrectlyAnswered()
        {
            Console.WriteLine("Question was incorrectly answered");
            Console.WriteLine(totalPlayers[currentPlayer] + " was sent to the penalty box");
            playerPenaltyBoxState[currentPlayer] = true;

            MoveToNextPlayer();
            return true;
        }


        private bool DidPlayerWin()
        {
            return !(playerPurseTotalState[currentPlayer] == 6);
        }
    }

}