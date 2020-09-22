using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace TriviaGame
{
    public class Game
    {
        public Player _player;
        public Board _board;

        private readonly int totalQuestions = 50;

        private readonly string popCategory = "Pop";
        private readonly string scienceCategory = "Science";
        private readonly string sportsCategory = "Sports";
        private readonly string rockCategory = "Rock";

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
            return (_player.GetTotalPlayersCount() >= 2);
        }
        
        public void RollDice(int rollResult)
        {
            Console.WriteLine(_player.totalPlayers[currentPlayer] + " is the current player");
            Console.WriteLine("They have rolled a " + rollResult);
            if (_player.playerPenaltyBoxState[currentPlayer])
            {
                if (_board.IsRollResultOdd(rollResult))
                {
                    isGettingOutOfPenaltyBox = true;
                    Console.WriteLine(_player.totalPlayers[currentPlayer] + " is getting out of the penalty box");
                    _player.playerBoardPositionState[currentPlayer] = UpdatePlayerPositionState(_player.playerBoardPositionState[currentPlayer], rollResult);
                    if (IsPlayerPositionGreaterThanEleven(_player.playerBoardPositionState[currentPlayer]))
                    {
                        _player.playerBoardPositionState[currentPlayer] = ResetPlayerToPositionZero(_player.playerBoardPositionState[currentPlayer]);
                    }
                    PrintPlayerPositionAndQuestionCategory();
                    AskQuestion();
                }
                else
                {
                    Console.WriteLine(_player.totalPlayers[currentPlayer] + " is not getting out of the penalty box");
                    isGettingOutOfPenaltyBox = false;
                }
            }
            else
            {
                _player.playerBoardPositionState[currentPlayer] = _player.playerBoardPositionState[currentPlayer] + rollResult;
                if (_player.playerBoardPositionState[currentPlayer] > 11) _player.playerBoardPositionState[currentPlayer] = _player.playerBoardPositionState[currentPlayer] - 12;
                PrintPlayerPositionAndQuestionCategory();
                AskQuestion();
            }
        }

        private void PrintPlayerPositionAndQuestionCategory()
        {
            Console.WriteLine(_player.totalPlayers[currentPlayer]
                    + "'s new location is "
                    + _player.playerBoardPositionState[currentPlayer]);
            Console.WriteLine("The category is " + FindCurrentQuestionCategoryByPlayerPosition());
        }

        private int ResetPlayerToPositionZero(int v)
        {
            var resetPosition = v - 12;
            return resetPosition;
        }

        private bool IsPlayerPositionGreaterThanEleven(int v)
        {
            return v > 11 ? true : false;
        }

        private static int UpdatePlayerPositionState(int currentPlayerPositionState, int rollResult)
        {
            return currentPlayerPositionState + rollResult;
        }

        private void AskQuestion()
        {
            if (FindCurrentQuestionCategoryByPlayerPosition() == popCategory)
            {
                Console.WriteLine(popQuestions.First());
                popQuestions.RemoveFirst();
            }
            if (FindCurrentQuestionCategoryByPlayerPosition() == scienceCategory)
            {
                Console.WriteLine(scienceQuestions.First());
                scienceQuestions.RemoveFirst();
            }
            if (FindCurrentQuestionCategoryByPlayerPosition() == sportsCategory)
            {
                Console.WriteLine(sportsQuestions.First());
                sportsQuestions.RemoveFirst();
            }
            if (FindCurrentQuestionCategoryByPlayerPosition() == rockCategory)
            {
                Console.WriteLine(rockQuestions.First());
                rockQuestions.RemoveFirst();
            }
        }


        private string FindCurrentQuestionCategoryByPlayerPosition()
        {
            var playerPosition = _player.playerBoardPositionState[currentPlayer];

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
            if (_player.playerPenaltyBoxState[currentPlayer])
            {
                if (isGettingOutOfPenaltyBox)
                {
                    Console.WriteLine("Answer was correct!!!!");
                    IncreasePlayerPurseTotal();
                    PrintPlayerPurseCount();
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
                _player.playerPurseTotalState[currentPlayer]++;
                PrintPlayerPurseCount();
                bool winner = DidPlayerWin();
                MoveToNextPlayer();
                return winner;
            }
        }

        private void PrintPlayerPurseCount()
        {
            Console.WriteLine(_player.totalPlayers[currentPlayer]
                    + " now has "
                    + _player.playerPurseTotalState[currentPlayer]
                    + " Gold Coins.");
        }

        private void MoveToNextPlayer()
        {
            currentPlayer++;
            if (currentPlayer == _player.totalPlayers.Count) currentPlayer = 0;
        }

        private void IncreasePlayerPurseTotal()
        {
            _player.playerPurseTotalState[currentPlayer]++;
        }

        public bool WasIncorrectlyAnswered()
        {
            Console.WriteLine("Question was incorrectly answered");
            Console.WriteLine(_player.totalPlayers[currentPlayer] + " was sent to the penalty box");
            _player.playerPenaltyBoxState[currentPlayer] = true;

            MoveToNextPlayer();
            return true;
        }


        private bool DidPlayerWin()
        {
            return !(_player.playerPurseTotalState[currentPlayer] == 6);
        }
    }

}