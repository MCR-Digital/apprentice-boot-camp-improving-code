using System;
using System.Collections.Generic;
using System.Linq;

namespace TriviaGame
{
    public class Game
    {
        private readonly List<string> players = new List<string>();

        private readonly int[] places = new int[Constants.MaxPlayers];
        private readonly int[] purses = new int[Constants.MaxPlayers];

        private readonly bool[] inPenaltyBox = new bool[Constants.MaxPlayers];

        private readonly LinkedList<string> popularQuestions = new LinkedList<string>();
        private readonly LinkedList<string> scienceQuestions = new LinkedList<string>();
        private readonly LinkedList<string> sportsQuestions = new LinkedList<string>();
        private readonly LinkedList<string> rockQuestions = new LinkedList<string>();

        private int currentPlayer = 0;
        private bool isGettingOutOfPenaltyBox;

        private enum QuestionCategory
        {
            Pop,
            Science,
            Sports,
            Rock
        }

        public Game()
        {
            CreateQuestions();
        }

        public bool HasMinimumRequiredPlayers { get => players.Count >= 2; }
        public int NumberOfPlayers { get => players.Count; }

        private void CreateQuestions()
        {
            for (int i = 0; i < 50; i++)
            {
                int questionNumber = i;

                popularQuestions.AddLast($"Pop Question {questionNumber}");
                scienceQuestions.AddLast($"Science Question {questionNumber}");
                sportsQuestions.AddLast($"Sports Question {questionNumber}");
                rockQuestions.AddLast($"Rock Question {questionNumber}");
            }
        }

        public bool AddPlayer(string playerName)
        {
            players.Add(playerName);
            places[NumberOfPlayers] = 0;
            purses[NumberOfPlayers] = 0;

            inPenaltyBox[NumberOfPlayers] = false;

            Console.WriteLine(playerName + " was added");
            Console.WriteLine("They are player number " + players.Count);
            return true;
        }

        public void RollDice(int roll)
        {
            Console.WriteLine(players[currentPlayer] + " is the current player");
            Console.WriteLine("They have rolled a " + roll);

            if (inPenaltyBox[currentPlayer])
            {
                if (roll % 2 != 0)
                {
                    isGettingOutOfPenaltyBox = true;

                    Console.WriteLine(players[currentPlayer] + " is getting out of the penalty box");
                    places[currentPlayer] = places[currentPlayer] + roll;
                    if (places[currentPlayer] > 11) places[currentPlayer] = places[currentPlayer] - 12;

                    Console.WriteLine(players[currentPlayer]
                            + "'s new location is "
                            + places[currentPlayer]);
                    Console.WriteLine("The category is " + CurrentCategory());
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

                places[currentPlayer] = places[currentPlayer] + roll;
                if (places[currentPlayer] > 11) places[currentPlayer] = places[currentPlayer] - 12;

                Console.WriteLine(players[currentPlayer]
                        + "'s new location is "
                        + places[currentPlayer]);
                Console.WriteLine("The category is " + CurrentCategory());
                AskQuestion();
            }

        }

        private void AskQuestion()
        {
            if (CurrentCategory() == QuestionCategory.Pop)
            {
                Console.WriteLine(popularQuestions.First());
                popularQuestions.RemoveFirst();
            }
            if (CurrentCategory() == QuestionCategory.Science)
            {
                Console.WriteLine(scienceQuestions.First());
                scienceQuestions.RemoveFirst();
            }
            if (CurrentCategory() == QuestionCategory.Sports)
            {
                Console.WriteLine(sportsQuestions.First());
                sportsQuestions.RemoveFirst();
            }
            if (CurrentCategory() == QuestionCategory.Rock)
            {
                Console.WriteLine(rockQuestions.First());
                rockQuestions.RemoveFirst();
            }
        }


        private QuestionCategory CurrentCategory()
        {
            switch (places[currentPlayer])
            {
                case 0:
                case 4:
                case 8:
                    return QuestionCategory.Pop;
                case 1:
                case 5:
                case 9:
                    return QuestionCategory.Science;
                case 2:
                case 6:
                case 10:
                    return QuestionCategory.Sports;
                default:
                    return QuestionCategory.Rock;
            }
        }

        public bool WasCorrectlyAnswered()
        {
            if (inPenaltyBox[currentPlayer])
            {
                if (isGettingOutOfPenaltyBox)
                {
                    Console.WriteLine("Answer was correct!!!!");
                    purses[currentPlayer]++;
                    Console.WriteLine(players[currentPlayer]
                            + " now has "
                            + purses[currentPlayer]
                            + " Gold Coins.");

                    bool winner = DidPlayerWin();
                    currentPlayer++;
                    if (currentPlayer == players.Count) currentPlayer = 0;

                    return winner;
                }
                else
                {
                    currentPlayer++;
                    if (currentPlayer == players.Count) currentPlayer = 0;
                    return true;
                }



            }
            else
            {

                Console.WriteLine("Answer was corrent!!!!");
                purses[currentPlayer]++;
                Console.WriteLine(players[currentPlayer]
                        + " now has "
                        + purses[currentPlayer]
                        + " Gold Coins.");

                bool winner = DidPlayerWin();
                currentPlayer++;
                if (currentPlayer == players.Count) currentPlayer = 0;

                return winner;
            }
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


        private bool DidPlayerWin()
        {
            return purses[currentPlayer] != 6;
        }
    }

}