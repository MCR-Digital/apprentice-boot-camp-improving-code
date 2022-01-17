using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace TriviaGame
{
    public class Game
    {
        readonly List<string> players = new List<string>();
        static int maxPlayerCount = 6;

        int[] playerPosition = new int[maxPlayerCount];
        int[] playerScores = new int[maxPlayerCount];

        bool[] inPenaltyBox = new bool[maxPlayerCount];
        int maximumBoardGameSpaces = 12;

        readonly LinkedList<string> popQuestions = new LinkedList<string>();
        readonly LinkedList<string> scienceQuestions = new LinkedList<string>();
        readonly LinkedList<string> sportsQuestions = new LinkedList<string>();
        readonly LinkedList<string> rockQuestions = new LinkedList<string>();

        int currentPlayer = 0;
        bool isGettingOutOfPenaltyBox;
        readonly int questionsCountPerCategory = 50;
        readonly int winningScore = 6;

        public Game()
        {
            for (int questionIndex = 0; questionIndex < questionsCountPerCategory; questionIndex++)
            {
                popQuestions.AddLast(CreateQuestion("Pop", questionIndex));
                scienceQuestions.AddLast(CreateQuestion("Science", questionIndex));
                sportsQuestions.AddLast(CreateQuestion("Sports", questionIndex));
                rockQuestions.AddLast(CreateQuestion("Rock", questionIndex));
            }
        }

        public string CreateQuestion(string categoryName, int index)
        {
            return $"{categoryName} Question " + index;
        }

        public bool Add(string playerName)
        {
            players.Add(playerName);
            playerPosition[players.Count] = 0;
            playerScores[players.Count] = 0;
            inPenaltyBox[players.Count] = false;

            Console.WriteLine(playerName + " was added");
            Console.WriteLine("They are player number " + players.Count);
            return true;
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
                    SetNewPosition(roll);
                  
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
                SetNewPosition(roll);

                Console.WriteLine("The category is " + CurrentCategory());
                AskQuestion();
            }

        }

        public void SetNewPosition(int diceNumber)
        {
            playerPosition [currentPlayer] = playerPosition [currentPlayer] + diceNumber;
            if (playerPosition[currentPlayer] > (maximumBoardGameSpaces - 1)) 
            playerPosition[currentPlayer] = playerPosition[currentPlayer] - maximumBoardGameSpaces;
            Console.WriteLine(players[currentPlayer]
        + "'s new location is "
        + playerPosition[currentPlayer]);
        }

        private void AskQuestion()
        {
            if (CurrentCategory() == "Pop")
            {
                Console.WriteLine(popQuestions.First());
                popQuestions.RemoveFirst();
            }
            if (CurrentCategory() == "Science")
            {
                Console.WriteLine(scienceQuestions.First());
                scienceQuestions.RemoveFirst();
            }
            if (CurrentCategory() == "Sports")
            {
                Console.WriteLine(sportsQuestions.First());
                sportsQuestions.RemoveFirst();
            }
            if (CurrentCategory() == "Rock")
            {
                Console.WriteLine(rockQuestions.First());
                rockQuestions.RemoveFirst();
            }
        }


        private string CurrentCategory()
        {
            if (playerPosition[currentPlayer] == 0) return "Pop";
            if (playerPosition[currentPlayer] == 4) return "Pop";
            if (playerPosition[currentPlayer] == 8) return "Pop";
            if (playerPosition[currentPlayer] == 1) return "Science";
            if (playerPosition[currentPlayer] == 5) return "Science";
            if (playerPosition[currentPlayer] == 9) return "Science";
            if (playerPosition[currentPlayer] == 2) return "Sports";
            if (playerPosition[currentPlayer] == 6) return "Sports";
            if (playerPosition[currentPlayer] == 10) return "Sports";
            return "Rock";
        }

        public bool WasCorrectlyAnswered()
        {
            if (inPenaltyBox[currentPlayer])
            {
                if (isGettingOutOfPenaltyBox)
                {
                    IncrementScore();

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
                IncrementScore();

                bool winner = DidPlayerWin();
                MoveToNextPlayer();

                return winner;
            }
        }

        public void IncrementScore()
        {
            Console.WriteLine("Answer was correct!!!!");
            playerScores[currentPlayer]++;
            Console.WriteLine(players[currentPlayer]
                    + " now has "
                    + playerScores[currentPlayer]
                    + " Gold Coins.");
        }

        public void MoveToNextPlayer()
        {
            currentPlayer++;
            if (currentPlayer == players.Count) currentPlayer = 0;
        }

        public bool WrongAnswer()
        {
            Console.WriteLine("Question was incorrectly answered");
            Console.WriteLine(players[currentPlayer] + " was sent to the penalty box");
            inPenaltyBox[currentPlayer] = true;

            MoveToNextPlayer();
            return true;
        }

        private bool DidPlayerWin()
        {
            return (playerScores[currentPlayer] != winningScore);
        }
    }

}