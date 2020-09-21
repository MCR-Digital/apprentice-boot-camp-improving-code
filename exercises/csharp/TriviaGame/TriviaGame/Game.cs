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
        List<string> players = new List<string>();

        int[] places = new int[MaxPlayers];
        int[] purses = new int[MaxPlayers];

        bool[] inPenaltyBox = new bool[MaxPlayers];

        LinkedList<string> popQuestions = new LinkedList<string>();
        LinkedList<string> scienceQuestions = new LinkedList<string>();
        LinkedList<string> sportsQuestions = new LinkedList<string>();
        LinkedList<string> rockQuestions = new LinkedList<string>();

        int currentPlayer = 0;
        bool canAnswer;

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
            return (players.Count >= MinPlayers);
        }

        public bool Add(string playerName)
        {
            players.Add(playerName);
            places[players.Count] = 0;
            purses[players.Count] = 0;
            inPenaltyBox[players.Count] = false;

            Console.WriteLine($"{playerName} was added");
            Console.WriteLine($"They are player number {players.Count}");
            return true;
        }

        public void Roll(int roll)
        {
            Console.WriteLine($"{players[currentPlayer]} is the current player");
            Console.WriteLine($"They have rolled a {roll}");

            if (inPenaltyBox[currentPlayer])
            {
                if (CanLeavePenaltyBox(roll))
                {
                    canAnswer = true;

                    PenaltyBoxMessage(canAnswer);
                    places[currentPlayer] = places[currentPlayer] + roll;
                    if (places[currentPlayer] > EndOfBoard)
                        places[currentPlayer] = places[currentPlayer] - LengthOfBoard;

                    Console.WriteLine($"{players[currentPlayer]}'s new location is {places[currentPlayer]}");
                    Console.WriteLine($"The category is {CurrentCategory()}");
                    AskQuestion();
                }
                else
                {
                    PenaltyBoxMessage();
                    canAnswer = false;
                }
            }
            else
            {
                places[currentPlayer] = places[currentPlayer] + roll;
                if (places[currentPlayer] > EndOfBoard) places[currentPlayer] = places[currentPlayer] - LengthOfBoard;

                Console.WriteLine($"{players[currentPlayer]}'s new location is {places[currentPlayer]}");
                Console.WriteLine($"The category is {CurrentCategory()}");
                AskQuestion();
            }
        }

        private void PenaltyBoxMessage(bool gettingOut = false)
        {
            Console.WriteLine(
                $"{players[currentPlayer]} {(gettingOut ? "is" : "is not")} getting out of the penalty box");
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
            if (places[currentPlayer] == 0) return Category.Pop;
            if (places[currentPlayer] == 4) return Category.Pop;
            if (places[currentPlayer] == 8) return Category.Pop;
            if (places[currentPlayer] == 1) return Category.Science;
            if (places[currentPlayer] == 5) return Category.Science;
            if (places[currentPlayer] == 9) return Category.Science;
            if (places[currentPlayer] == 2) return Category.Sports;
            if (places[currentPlayer] == 6) return Category.Sports;
            if (places[currentPlayer] == 10) return Category.Sports;
            return Category.Rock;
        }

        public bool WasCorrectlyAnswered()
        {
            if (inPenaltyBox[currentPlayer])
            {
                if (canAnswer)
                {
                    Console.WriteLine("Answer was correct!!!!");
                    purses[currentPlayer]++;
                    Console.WriteLine($"{players[currentPlayer]} now has {purses[currentPlayer]} Gold Coins.");

                    bool winner = DidPlayerWin();
                    currentPlayer++;
                    if (currentPlayer == players.Count) currentPlayer = FirstPlayer;

                    return winner;
                }
                else
                {
                    currentPlayer++;
                    if (currentPlayer == players.Count) currentPlayer = FirstPlayer;
                    return true;
                }
            }
            else
            {
                Console.WriteLine("Answer was corrent!!!!");
                purses[currentPlayer]++;
                Console.WriteLine($"{players[currentPlayer]} now has {purses[currentPlayer]} Gold Coins.");

                bool winner = DidPlayerWin();
                currentPlayer++;
                if (currentPlayer == players.Count) currentPlayer = FirstPlayer;

                return winner;
            }
        }

        public bool WrongAnswer()
        {
            Console.WriteLine("Question was incorrectly answered");
            Console.WriteLine($"{players[currentPlayer]} was sent to the penalty box");
            inPenaltyBox[currentPlayer] = true;

            currentPlayer++;
            if (currentPlayer == players.Count) currentPlayer = FirstPlayer;
            return true;
        }


        private bool DidPlayerWin()
        {
            return purses[currentPlayer] != WinningScore;
        }
    }
}