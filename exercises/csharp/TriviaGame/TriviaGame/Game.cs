using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace TriviaGame
{
    public class Game
    {
        List<string> players = new List<string>();

        int[] places = new int[6];
        int[] purses = new int[6];

        bool[] inPenaltyBox = new bool[6];

        LinkedList<string> popQuestions = new LinkedList<string>();
        LinkedList<string> scienceQuestions = new LinkedList<string>();
        LinkedList<string> sportsQuestions = new LinkedList<string>();
        LinkedList<string> rockQuestions = new LinkedList<string>();

        int currentPlayer = 0;
        bool isGettingOutOfPenaltyBox;

        public Game()
        {
            for (int questionNumber = 0; questionNumber < 50; questionNumber++)
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
            return (NumberOfPlayers() >= 2);
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
                if (roll % 2 != 0)
                {
                    isGettingOutOfPenaltyBox = true;

                    Console.WriteLine(players[currentPlayer] + " is getting out of the penalty box");
                    MoveToNewLocationAndAskQuestion(roll);
                }
                else
                {
                    Console.WriteLine(players[currentPlayer] + " is not getting out of the penalty box");
                    isGettingOutOfPenaltyBox = false;
                }

            }
            else
            {
                MoveToNewLocationAndAskQuestion(roll);
            }

        }

        private void MoveToNewLocationAndAskQuestion(int roll)
        {
            places[currentPlayer] = places[currentPlayer] + roll;
            if (places[currentPlayer] > 11) places[currentPlayer] = places[currentPlayer] - 12;

            Console.WriteLine(players[currentPlayer]
                    + "'s new location is "
                    + places[currentPlayer]);
            Console.WriteLine("The category is " + CurrentCategory());
            AskQuestion();
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

        public bool WasCorrectlyAnswered()
        {
            if (inPenaltyBox[currentPlayer])
            {
                if (isGettingOutOfPenaltyBox)
                {
                    Console.WriteLine("Answer was correct!!!!");
                    return isWinner();
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
                return isWinner();
            }
        }

        private bool isWinner()
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

        public bool WrongAnswer()
        {
            Console.WriteLine("Question was incorrectly answered");
            Console.WriteLine(players[currentPlayer] + " was sent to the penalty box");
            inPenaltyBox[currentPlayer] = true;
            CurrentPlayerCount();
            return true;
        }

        private void CurrentPlayerCount()
        {
            currentPlayer++;
            if (currentPlayer == players.Count) currentPlayer = 0;
        }

        private bool DidPlayerWin()
        {
            return !(purses[currentPlayer] == 6);
        }
    }

}