using System;
using System.Collections.Generic;
using System.Linq;

namespace TriviaGame
{
    public class TriviaGame
    {
        private const int WINNING_SCORE = 6;
        private const int NUMBER_OF_CATEGORIES = 4;
        private const string POP_CATEGORY = "Pop";
        private const string SCIENCE_CATEGORY = "Science";
        private const string SPORTS_CATEGORY = "Sports";
        private const string ROCK_CATEGORY = "Rock";
        List<string> players = new List<string>();

        int[] playerLocations = new int[6];
        int[] playerPurses = new int[6];

        bool[] inPenaltyBox = new bool[6];

        private readonly Category pop = new Category(POP_CATEGORY, new LinkedList<string>());
        private readonly Category science = new Category(SCIENCE_CATEGORY, new LinkedList<string>());
        private readonly Category sports = new Category(SPORTS_CATEGORY, new LinkedList<string>());
        private readonly Category rock = new Category(ROCK_CATEGORY, new LinkedList<string>());

        int currentPlayer = 0;
        bool playerCanAnswerQuestion;

        public TriviaGame()
        {
            for (int questionIndex = 0; questionIndex < 50; questionIndex++)
            {
                pop.questions.AddLast(CreateQuestionText(pop.name, questionIndex));
                science.questions.AddLast(CreateQuestionText(science.name, questionIndex));
                sports.questions.AddLast(CreateQuestionText(sports.name, questionIndex));
                rock.questions.AddLast(CreateQuestionText(rock.name, questionIndex));
            }
        }

        public string CreateQuestionText(string categoryName, int index)
        {
            return $"{categoryName} Question " + index;
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
                PenaltyBoxPlayerTurn(roll);
            }
            else
            {
                PlayTurn(roll);
            }
        }

        private void PenaltyBoxPlayerTurn(int roll)
        {
            if (roll % 2 != 0)
            {
                playerCanAnswerQuestion = true;
                Console.WriteLine(players[currentPlayer] + " is getting out of the penalty box");
                PlayTurn(roll);
            }
            else
            {
                Console.WriteLine(players[currentPlayer] + " is not getting out of the penalty box");
                playerCanAnswerQuestion = false;
            }
        }

        private void PlayTurn(int roll)
        {
            playerLocations[currentPlayer] = playerLocations[currentPlayer] + roll;
            if (playerLocations[currentPlayer] > 11) playerLocations[currentPlayer] = playerLocations[currentPlayer] - 12;

            Console.WriteLine(players[currentPlayer]
                    + "'s new location is "
                    + playerLocations[currentPlayer]);
            Console.WriteLine("The category is " + CurrentCategory().name);
            AskQuestionFrom(CurrentCategory().questions);
        }

        private void AskQuestionFrom(LinkedList<string> categoryQuestions)
        {
            Console.WriteLine(categoryQuestions.First());
            categoryQuestions.RemoveFirst();
        }

        private Category CurrentCategory()
        {
            int playerLocation = playerLocations[currentPlayer];
            Category result = rock;
            if (playerLocation % NUMBER_OF_CATEGORIES == 0) result = pop;
            if (playerLocation % NUMBER_OF_CATEGORIES == 1) result = science;
            if (playerLocation % NUMBER_OF_CATEGORIES == 2) result = sports;
            return result;
        }

        public bool WasQuestionAnsweredCorrectly()
        {
            if (inPenaltyBox[currentPlayer])
            {
                if (playerCanAnswerQuestion)
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

            bool canGameContinue = CanGameContinue();
            ChangeCurrentPlayer();

            return canGameContinue;
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
            if (currentPlayer == NumberOfPlayers()) currentPlayer = 0;
        }

        private bool CanGameContinue()
        {
            return !(playerPurses[currentPlayer] == WINNING_SCORE);
        }
    }

}