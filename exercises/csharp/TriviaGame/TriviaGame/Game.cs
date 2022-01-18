using System;
using System.Collections.Generic;
using System.Linq;

namespace TriviaGame
{
    public class Game
    {
        readonly List<string> players = new List<string>();
        static int maxPlayerCount = 6;

        readonly  int[] playerPosition = new int[maxPlayerCount];
        readonly  int[] playerScores = new int[maxPlayerCount];

        readonly  bool[] inPenaltyBox = new bool[maxPlayerCount];
        readonly  int maximumBoardGameSpaces = 12;

        readonly LinkedList<string> popQuestions = new LinkedList<string>();
        readonly LinkedList<string> scienceQuestions = new LinkedList<string>();
        readonly LinkedList<string> sportsQuestions = new LinkedList<string>();
        readonly LinkedList<string> rockQuestions = new LinkedList<string>();

        int currentPlayer;
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

        public void Roll(int diceNumber)
        {
            Console.WriteLine(players[currentPlayer] + " is the current player");
            Console.WriteLine("They have rolled a " + diceNumber);

            if (inPenaltyBox[currentPlayer])
            {
                if (!isOutOfPenaltyBox(diceNumber))
                {
                    Console.WriteLine(players[currentPlayer] + " is not getting out of the penalty box");
                    return;
                }
                Console.WriteLine(players[currentPlayer] + " is getting out of the penalty box");
            }
            PlayTurn(diceNumber);
        }

        private bool isOutOfPenaltyBox(int diceNumber)
        {
            var diceIsOdd = (diceNumber % 2 != 0);

            if (diceIsOdd)
            {
                isGettingOutOfPenaltyBox = true;
            }
            else
            {
                isGettingOutOfPenaltyBox = false;
            }
            return diceIsOdd;
        }

        public void PlayTurn(int diceNumber)
        {
            SetNewPosition(diceNumber);
            Console.WriteLine("The category is " + CurrentCategory());
            AskQuestion();
        }

        public void SetNewPosition(int roll)
        {
            playerPosition [currentPlayer] = playerPosition [currentPlayer] + roll;

            if (playerPosition[currentPlayer] > (maximumBoardGameSpaces - 1))
            {
                playerPosition[currentPlayer] = playerPosition[currentPlayer] - maximumBoardGameSpaces;
            }
            Console.WriteLine(players[currentPlayer] + "'s new location is " + playerPosition[currentPlayer]);
        }

        private void AskQuestion()
        {
            switch (CurrentCategory())
            {
                case "Pop":
                    AskCategoryQuestion(popQuestions);
                    break;

                case "Science":
                    AskCategoryQuestion(scienceQuestions);
                    break;

                case "Sports":
                    AskCategoryQuestion(sportsQuestions);
                    break;

                case "Rock":
                    AskCategoryQuestion(rockQuestions);
                    break;

                default:
                    break;
                    //throw new ArgumentException("No categories for this question");
            }
        }

        private void AskCategoryQuestion(LinkedList<string> questions)
        {
            Console.WriteLine(questions.First());
            questions.RemoveFirst();
        }

        private string CurrentCategory()
        {
            switch (playerPosition[currentPlayer])
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

        public bool WasCorrectlyAnswered()
        {
            if (inPenaltyBox[currentPlayer])
            {
                if (!isGettingOutOfPenaltyBox)
                {
                    MoveToNextPlayer();
                    return true; 
                }

            }
               return IsPlayerWinner();
        }

        private bool IsPlayerWinner()
        {
            IncrementScore();

            bool winner = DidPlayerWin();
            MoveToNextPlayer();

            return winner;
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