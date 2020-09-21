using System;
using System.Collections.Generic;
using System.Linq;

namespace TriviaGame
{
    public class TriviaGame
    {
        private const int WINNING_SCORE = 6;
        private const string POP_CATEGORY = "Pop";
        private const string SCIENCE_CATEGORY = "Science";
        private const string SPORTS_CATEGORY = "Sports";
        private const string ROCK_CATEGORY = "Rock";
        List<string> players = new List<string>();

        int[] playerLocations = new int[6];
        int[] playerPurses = new int[6];

        bool[] inPenaltyBox = new bool[6];

        LinkedList<string> popQuestions = new LinkedList<string>();
        LinkedList<string> scienceQuestions = new LinkedList<string>();
        LinkedList<string> sportsQuestions = new LinkedList<string>();
        LinkedList<string> rockQuestions = new LinkedList<string>();

        int currentPlayer = 0;
        bool isGettingOutOfPenaltyBox;

        public TriviaGame()
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

        public bool IsGamePlayable()
        {
            return (NumberOfPlayers() >= 2);
        }

        public bool AddPlayer(string playerName)
        {
            players.Add(playerName);
            playerLocations[NumberOfPlayers()] = 0;
            playerPurses[NumberOfPlayers()] = 0;
            inPenaltyBox[NumberOfPlayers()] = false;

            Console.WriteLine(playerName + " was added");
            Console.WriteLine("They are player number " + players.Count);
            return true;
        }

        public int NumberOfPlayers()
        {
            return players.Count;
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
                    PlayTurn(roll);
                }
                else
                {
                    Console.WriteLine(players[currentPlayer] + " is not getting out of the penalty box");
                    isGettingOutOfPenaltyBox = false;
                }
            }
            else
            {
                PlayTurn(roll);
            }
        }

        private void PlayTurn(int roll)
        {
            playerLocations[currentPlayer] = playerLocations[currentPlayer] + roll;
            if (playerLocations[currentPlayer] > 11) playerLocations[currentPlayer] = playerLocations[currentPlayer] - 12;

            Console.WriteLine(players[currentPlayer]
                    + "'s new location is "
                    + playerLocations[currentPlayer]);
            Console.WriteLine("The category is " + CurrentCategory());
            AskQuestion();
        }

        private void AskQuestion()
        {
            if (CurrentCategory() == POP_CATEGORY)
            {
                Console.WriteLine(popQuestions.First());
                popQuestions.RemoveFirst();
            }
            if (CurrentCategory() == SCIENCE_CATEGORY)
            {
                Console.WriteLine(scienceQuestions.First());
                scienceQuestions.RemoveFirst();
            }
            if (CurrentCategory() == SPORTS_CATEGORY)
            {
                Console.WriteLine(sportsQuestions.First());
                sportsQuestions.RemoveFirst();
            }
            if (CurrentCategory() == ROCK_CATEGORY)
            {
                Console.WriteLine(rockQuestions.First());
                rockQuestions.RemoveFirst();
            }
        }

        private string CurrentCategory()
        {
            if (playerLocations[currentPlayer] % 4 == 0) return POP_CATEGORY;
            if (playerLocations[currentPlayer] % 4 == 1) return SCIENCE_CATEGORY;
            if (playerLocations[currentPlayer] % 4 == 2) return SPORTS_CATEGORY;
            return ROCK_CATEGORY;
        }

        public bool WasQuestionAnsweredCorrectly()
        {
            if (inPenaltyBox[currentPlayer])
            {
                if (isGettingOutOfPenaltyBox)
                {
                    return CorrectAnswer("Answer was correct!!!!");
                }
                else
                {
                    ChangeCurrentPlayer();
                    return true;
                }
            }
            else
            {
                return CorrectAnswer("Answer was corrent!!!!");
            }
        }

        private bool CorrectAnswer(string answerMessage)
        {
            Console.WriteLine(answerMessage);
            playerPurses[currentPlayer]++;
            Console.WriteLine(players[currentPlayer]
                    + " now has "
                    + playerPurses[currentPlayer]
                    + " Gold Coins.");

            bool winner = DoesPlayerHaveWinningScore();
            ChangeCurrentPlayer();

            return winner;
        }

        public bool WrongAnswer()
        {
            Console.WriteLine("Question was incorrectly answered");
            Console.WriteLine(players[currentPlayer] + " was sent to the penalty box");
            inPenaltyBox[currentPlayer] = true;
            ChangeCurrentPlayer();
            return true;
        }

        private void ChangeCurrentPlayer()
        {
            currentPlayer++;
            if (currentPlayer == players.Count) currentPlayer = 0;
        }

        private bool DoesPlayerHaveWinningScore()
        {
            return !(playerPurses[currentPlayer] == WINNING_SCORE);
        }
    }

}