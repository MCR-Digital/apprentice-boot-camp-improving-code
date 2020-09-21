using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace TriviaGame
{
    public class Game
    {
        private const int WinningScore = 6;
        private const int MaxPlayers = 6;
        private const int MinPlayers = 2;
        private const int EndOfBoard = 11;
        private const int LengthOfBoard = 12;
        private const int FirstPlayer = 0;
        List<string> gamePlayers = new List<string>();

        int[] playerPositions = new int[MaxPlayers];
        int[] playerPurses = new int[MaxPlayers];

        bool[] inPenaltyBox = new bool[MaxPlayers];

        LinkedList<string> popQuestions = new LinkedList<string>();
        LinkedList<string> scienceQuestions = new LinkedList<string>();
        LinkedList<string> sportsQuestions = new LinkedList<string>();
        LinkedList<string> rockQuestions = new LinkedList<string>();

        int currentPlayer = 0;
        bool playerCanAnswerQuestion;

        public Game()
        {
            for (int i = 0; i < 50; i++)
            {
                AddDefaultQuestions(i);
            }
        }

        private void AddDefaultQuestions(int i)
        {
            popQuestions.AddLast($"Pop Question {i}");
            scienceQuestions.AddLast($"Science Question {i}");
            sportsQuestions.AddLast($"Sports Question {i}");
            rockQuestions.AddLast($"Rock Question {i}");
        }

        public bool IsPlayable()
        {
            return (gamePlayers.Count >= MinPlayers);
        }

        public bool Add(string playerName)
        {
            gamePlayers.Add(playerName);
            playerPositions[gamePlayers.Count] = 0;
            playerPurses[gamePlayers.Count] = 0;
            inPenaltyBox[gamePlayers.Count] = false;

            Console.WriteLine($"{playerName} was added");
            Console.WriteLine($"They are player number {gamePlayers.Count}");
            return true;
        }

        public void Roll(int roll)
        {
            var playerName = gamePlayers[currentPlayer];
            Console.WriteLine($"{playerName} is the current player");
            Console.WriteLine($"They have rolled a {roll}");

            if (inPenaltyBox[currentPlayer])
            {
                if (CanLeavePenaltyBox(roll))
                {
                    playerCanAnswerQuestion = true;

                    PenaltyBoxMessage(playerCanAnswerQuestion);
                    playerPositions[currentPlayer] = playerPositions[currentPlayer] + roll;
                    if (playerPositions[currentPlayer] > EndOfBoard)
                        playerPositions[currentPlayer] = playerPositions[currentPlayer] - LengthOfBoard;

                    Console.WriteLine($"{playerName}'s new location is {playerPositions[currentPlayer]}");
                    Console.WriteLine($"The category is {CurrentCategory()}");
                    AskQuestion();
                }
                else
                {
                    PenaltyBoxMessage();
                    playerCanAnswerQuestion = false;
                }
            }
            else
            {
                playerPositions[currentPlayer] = playerPositions[currentPlayer] + roll;
                if (playerPositions[currentPlayer] > EndOfBoard) playerPositions[currentPlayer] = playerPositions[currentPlayer] - LengthOfBoard;

                Console.WriteLine($"{playerName}'s new location is {playerPositions[currentPlayer]}");
                Console.WriteLine($"The category is {CurrentCategory()}");
                AskQuestion();
            }
        }

        private void PenaltyBoxMessage(bool gettingOut = false)
        {
            Console.WriteLine(
                $"{gamePlayers[currentPlayer]} {(gettingOut ? "is" : "is not")} getting out of the penalty box");
        }

        private static bool CanLeavePenaltyBox(int roll)
        {
            return roll % 2 != 0;
        }

        private void AskQuestion()
        {
            if (CurrentCategory() == Category.Pop)
            {
                Console.WriteLine(popQuestions.First());
                popQuestions.RemoveFirst();
            }

            if (CurrentCategory() == Category.Science)
            {
                Console.WriteLine(scienceQuestions.First());
                scienceQuestions.RemoveFirst();
            }

            if (CurrentCategory() == Category.Sports)
            {
                Console.WriteLine(sportsQuestions.First());
                sportsQuestions.RemoveFirst();
            }

            if (CurrentCategory() == Category.Rock)
            {
                Console.WriteLine(rockQuestions.First());
                rockQuestions.RemoveFirst();
            }
        }


        private string CurrentCategory()
        {
            var playerPosition = playerPositions[currentPlayer];
            if (playerPosition == 0) return Category.Pop;
            if (playerPosition == 4) return Category.Pop;
            if (playerPosition == 8) return Category.Pop;
            if (playerPosition == 1) return Category.Science;
            if (playerPosition == 5) return Category.Science;
            if (playerPosition == 9) return Category.Science;
            if (playerPosition == 2) return Category.Sports;
            if (playerPosition == 6) return Category.Sports;
            if (playerPosition == 10) return Category.Sports;
            return Category.Rock;
        }

        public bool WasCorrectlyAnswered()
        {
            if (inPenaltyBox[currentPlayer])
            {
                if (playerCanAnswerQuestion)
                {
                    Console.WriteLine("Answer was correct!!!!");
                    playerPurses[currentPlayer]++;
                    Console.WriteLine($"{gamePlayers[currentPlayer]} now has {playerPurses[currentPlayer]} Gold Coins.");

                    bool winner = DidPlayerWin();
                    currentPlayer++;
                    if (currentPlayer == gamePlayers.Count) currentPlayer = FirstPlayer;

                    return winner;
                }
                else
                {
                    currentPlayer++;
                    if (currentPlayer == gamePlayers.Count) currentPlayer = FirstPlayer;
                    return true;
                }
            }
            else
            {
                Console.WriteLine("Answer was corrent!!!!");
                playerPurses[currentPlayer]++;
                Console.WriteLine($"{gamePlayers[currentPlayer]} now has {playerPurses[currentPlayer]} Gold Coins.");

                bool winner = DidPlayerWin();
                currentPlayer++;
                if (currentPlayer == gamePlayers.Count) currentPlayer = FirstPlayer;

                return winner;
            }
        }

        public bool WrongAnswer()
        {
            Console.WriteLine("Question was incorrectly answered");
            Console.WriteLine($"{gamePlayers[currentPlayer]} was sent to the penalty box");
            inPenaltyBox[currentPlayer] = true;

            currentPlayer++;
            if (currentPlayer == gamePlayers.Count) currentPlayer = FirstPlayer;
            return true;
        }


        private bool DidPlayerWin()
        {
            return playerPurses[currentPlayer] != WinningScore;
        }
    }
}