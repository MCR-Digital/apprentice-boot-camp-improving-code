using System;
using System.Collections.Generic;
using System.Linq;

namespace TriviaGame
{
    public class TriviaGame
    {
        private const int WINNING_SCORE = 6;
        private const int NUMBER_OF_CATEGORIES = 4;
        private const int NUMBER_OF_QUESTIONS_PER_CATEGORY = 50;
        private const int MINIMUM_NUMBER_OF_PLAYERS = 2;
        private const string POP_CATEGORY = "Pop";
        private const string SCIENCE_CATEGORY = "Science";
        private const string SPORTS_CATEGORY = "Sports";
        private const string ROCK_CATEGORY = "Rock";
        readonly List<Player> players = new List<Player>();

        private readonly Category pop = new Category(POP_CATEGORY, new LinkedList<Question>());
        private readonly Category science = new Category(SCIENCE_CATEGORY, new LinkedList<Question>());
        private readonly Category sports = new Category(SPORTS_CATEGORY, new LinkedList<Question>());
        private readonly Category rock = new Category(ROCK_CATEGORY, new LinkedList<Question>());

        int currentPlayerIndex = 0;
        Player currentPlayer;

        public TriviaGame()
        {
            for (int questionIndex = 0; questionIndex < NUMBER_OF_QUESTIONS_PER_CATEGORY; questionIndex++)
            {
                pop.AddQuestion(questionIndex);
                science.AddQuestion(questionIndex);
                sports.AddQuestion(questionIndex);
                rock.AddQuestion(questionIndex);
            }
        }

        public bool IsGamePlayable()
        {
            return (NumberOfPlayers() >= MINIMUM_NUMBER_OF_PLAYERS);
        }

        public bool AddPlayer(string playerName)
        {
            players.Add(new Player(playerName));

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
            currentPlayer = players[currentPlayerIndex];
            Console.WriteLine(currentPlayer.name + " is the current player");
            Console.WriteLine("They have rolled a " + roll);

            if (currentPlayer.isInPenaltyBox)
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
            if (CanPlayerEscapePenaltyBox(roll))
            {
                currentPlayer.canAnswerQuestion = true;
                WritePenaltyBoxMessage(currentPlayer.canAnswerQuestion);
                PlayTurn(roll);
            }
            else
            {
                currentPlayer.canAnswerQuestion = false;
                WritePenaltyBoxMessage(currentPlayer.canAnswerQuestion);
            }
        }

        private static bool CanPlayerEscapePenaltyBox(int roll)
        {
            return roll % 2 != 0;
        }

        private void WritePenaltyBoxMessage(bool isEspcaing)
        {
            Console.WriteLine(
                $"{currentPlayer.name} {(isEspcaing ? "is" : "is not")} getting out of the penalty box");
        }

        private void PlayTurn(int roll)
        {
            currentPlayer.location = currentPlayer.location + roll;
            if (currentPlayer.location > 11)
            {
                currentPlayer.location = currentPlayer.location - 12;
            }

            Console.WriteLine(currentPlayer.name
                    + "'s new location is "
                    + currentPlayer.location);
            Console.WriteLine("The category is " + CurrentCategory().name);
            AskQuestionFrom(CurrentCategory().questions);
        }

        private void AskQuestionFrom(LinkedList<Question> categoryQuestions)
        {
            Console.WriteLine(categoryQuestions.First().text);
            categoryQuestions.RemoveFirst();
        }

        private Category CurrentCategory()
        {
            int playerLocation = currentPlayer.location;
            Category result = rock;
            if (playerLocation % NUMBER_OF_CATEGORIES == 0)
            {
                result = pop;
            }
            if (playerLocation % NUMBER_OF_CATEGORIES == 1)
            {
                result = science;
            }
            if (playerLocation % NUMBER_OF_CATEGORIES == 2)
            {
                result = sports;
            }
            return result;
        }

        public bool CanGameContinueAfterCorrectAnswer()
        {
            bool result;
            if (currentPlayer.isInPenaltyBox)
            {
                if (currentPlayer.canAnswerQuestion)
                {
                    CorrectAnswer("Answer was correct!!!!");
                    result = CanGameContinue();
                }
                else
                {
                    result =  true;
                }
            }
            else
            {
                CorrectAnswer("Answer was corrent!!!!");
                result = CanGameContinue();
            }
            ChangeCurrentPlayer();
            return result;
        }

        private void CorrectAnswer(string answerMessage)
        {
            Console.WriteLine(answerMessage);
            currentPlayer.purse++;
            Console.WriteLine(currentPlayer.name
                    + " now has "
                    + currentPlayer.purse
                    + " Gold Coins.");
        }

        public bool WrongAnswer()
        {
            Console.WriteLine("Question was incorrectly answered");
            Console.WriteLine(currentPlayer.name + " was sent to the penalty box");
            currentPlayer.isInPenaltyBox = true;
            ChangeCurrentPlayer();
            return true;
        }

        private void ChangeCurrentPlayer()
        {
            currentPlayerIndex++;
            if (currentPlayerIndex == NumberOfPlayers())
            {
                currentPlayerIndex = 0;
            }
        }

        private bool CanGameContinue()
        {
            return currentPlayer.purse != WINNING_SCORE;
        }
    }

}