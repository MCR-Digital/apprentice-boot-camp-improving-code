using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace TriviaGame
{
    public class Game
    {
        readonly List<string> players = new List<string>();

        private const int MAXIMUM_NUMBER_OF_PLAYERS = 6;
        private const int WINNING_TOTAL = 6;
        private const int MINIMUM_NUMBER_OF_PLAYERS = 2;
        private const int TOTAL_NUMBER_OF_QUESTIONS = 50;
        private const int TOTAL_PLACES_OF_BOARD = 12;

        readonly int[] places = new int[MAXIMUM_NUMBER_OF_PLAYERS];
        readonly int[] purses = new int[MAXIMUM_NUMBER_OF_PLAYERS];
        readonly bool[] inPenaltyBox = new bool[MAXIMUM_NUMBER_OF_PLAYERS];
        private int currentPlayer;
        private bool isGettingOutOfPenaltyBox;

        readonly LinkedList<string> popQuestions = new LinkedList<string>();
        readonly LinkedList<string> scienceQuestions = new LinkedList<string>();
        readonly LinkedList<string> sportsQuestions = new LinkedList<string>();
        readonly LinkedList<string> rockQuestions = new LinkedList<string>();

        public Game()
        {
            for (int questionNumber = 0; questionNumber < TOTAL_NUMBER_OF_QUESTIONS; questionNumber++)
            {
                popQuestions.AddLast("Pop Question " + questionNumber);
                scienceQuestions.AddLast(("Science Question " + questionNumber));
                sportsQuestions.AddLast(("Sports Question " + questionNumber));
                rockQuestions.AddLast(CreateRockQuestion(questionNumber));
            }
        }

        public string CreateRockQuestion(int questionNumber)
        {
            return "Rock Question " + questionNumber;
        }

        public bool IsPlayable()
        {
            return (NumberOfPlayers() >= MINIMUM_NUMBER_OF_PLAYERS);
        }

        public bool Add(string playerName)
        {
            players.Add(playerName);
            places[NumberOfPlayers()] = 0;
            purses[NumberOfPlayers()] = 0;
            inPenaltyBox[NumberOfPlayers()] = false;

            Console.WriteLine(playerName + " was added");
            Console.WriteLine("They are player number " + players.Count);
            return true;
        }

        public int NumberOfPlayers()
        {
            return players.Count;
        }

        public void Roll(int roll)
        {
            Console.WriteLine(players[currentPlayer] + " is the current player");
            Console.WriteLine("They have rolled a " + roll);

            if (inPenaltyBox[currentPlayer])
            {
                bool isRollOdd = roll % 2 != 0;
                if (isRollOdd)
                {
                    isGettingOutOfPenaltyBox = true;

                    Console.WriteLine(players[currentPlayer] + " is getting out of the penalty box");
                    MoveToNewLocation(roll);
                    AskQuestion();
                }
                else
                {
                    Console.WriteLine(players[currentPlayer] + " is not getting out of the penalty box");
                    isGettingOutOfPenaltyBox = false;
                }

            }
            else
            {
                MoveToNewLocation(roll);
                AskQuestion();
            }

        }

        public bool IsAnswerCorrect()
        {
            if (inPenaltyBox[currentPlayer])
            {
                if (isGettingOutOfPenaltyBox)
                {
                    Console.WriteLine("Answer was correct!!!!");
                    return IsWinner();
                }
                else
                {
                    CurrentPlayerCount();
                    return true;
                }
            }
            else
            {
                Console.WriteLine("Answer was corrent!!!!");
                return IsWinner();
            }
        }

        public bool IsAnswerWrong()
        {
            Console.WriteLine("Question was incorrectly answered");
            Console.WriteLine(players[currentPlayer] + " was sent to the penalty box");
            inPenaltyBox[currentPlayer] = true;
            CurrentPlayerCount();
            return true;
        }

        private void MoveToNewLocation(int roll)
        {
            places[currentPlayer] = places[currentPlayer] + roll;
            if (places[currentPlayer] > TOTAL_PLACES_OF_BOARD - 1) places[currentPlayer] = places[currentPlayer] - TOTAL_PLACES_OF_BOARD;

            Console.WriteLine(players[currentPlayer]
                    + "'s new location is "
                    + places[currentPlayer]);
            Console.WriteLine("The category is " + CurrentCategory());
        }

        private void AskQuestion()
        {
            if (CurrentCategory() == "Pop")
            {
                AskAndRemoveQuestion(popQuestions);
            }
            if (CurrentCategory() == "Science")
            {
                AskAndRemoveQuestion(scienceQuestions);
            }
            if (CurrentCategory() == "Sports")
            {
                AskAndRemoveQuestion(sportsQuestions);
            }
            if (CurrentCategory() == "Rock")
            {
                AskAndRemoveQuestion(rockQuestions);
            }
        }

        private void AskAndRemoveQuestion(LinkedList<string> questions)
        {
            Console.WriteLine(questions.First());
            questions.RemoveFirst();
        }

        private string CurrentCategory()
        {
            if (places[currentPlayer] == 0) return "Pop";
            if (places[currentPlayer] == 4) return "Pop";
            if (places[currentPlayer] == 8) return "Pop";
            if (places[currentPlayer] == 1) return "Science";
            if (places[currentPlayer] == 5) return "Science";
            if (places[currentPlayer] == 9) return "Science";
            if (places[currentPlayer] == 2) return "Sports";
            if (places[currentPlayer] == 6) return "Sports";
            if (places[currentPlayer] == 10) return "Sports";
            return "Rock";
        }

        private bool IsWinner()
        {
            purses[currentPlayer]++;
            Console.WriteLine(players[currentPlayer]
                    + " now has "
                    + purses[currentPlayer]
                    + " Gold Coins.");

            bool winner = DidPlayerWin();
            CurrentPlayerCount();

            return winner;
        }

        private void CurrentPlayerCount()
        {
            currentPlayer++;
            if (currentPlayer == players.Count) currentPlayer = 0;
        }

        private bool DidPlayerWin()
        {
            return !(purses[currentPlayer] == WINNING_TOTAL);
        }
    }

}