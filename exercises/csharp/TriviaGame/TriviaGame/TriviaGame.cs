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
        List<Player> players = new List<Player>();

        private readonly Category pop = new Category(POP_CATEGORY, new LinkedList<Question>());
        private readonly Category science = new Category(SCIENCE_CATEGORY, new LinkedList<Question>());
        private readonly Category sports = new Category(SPORTS_CATEGORY, new LinkedList<Question>());
        private readonly Category rock = new Category(ROCK_CATEGORY, new LinkedList<Question>());

        int currentPlayerIndex = 0;

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

        private Player CurrentPlayer()
        {
            return players[currentPlayerIndex];
        }

        public void RollDice(int roll)
        {
            Console.WriteLine(CurrentPlayer().name + " is the current player");
            Console.WriteLine("They have rolled a " + roll);

            if (CurrentPlayer().isInPenaltyBox)
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
                CurrentPlayer().canAnswerQuestion = true;
                Console.WriteLine(CurrentPlayer().name + " is getting out of the penalty box");
                PlayTurn(roll);
            }
            else
            {
                Console.WriteLine(CurrentPlayer().name + " is not getting out of the penalty box");
                CurrentPlayer().canAnswerQuestion = false;
            }
        }

        private void PlayTurn(int roll)
        {
            CurrentPlayer().location = CurrentPlayer().location + roll;
            if (CurrentPlayer().location > 11) CurrentPlayer().location = CurrentPlayer().location - 12;

            Console.WriteLine(CurrentPlayer().name
                    + "'s new location is "
                    + CurrentPlayer().location);
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
            int playerLocation = CurrentPlayer().location;
            Category result = rock;
            if (playerLocation % NUMBER_OF_CATEGORIES == 0) result = pop;
            if (playerLocation % NUMBER_OF_CATEGORIES == 1) result = science;
            if (playerLocation % NUMBER_OF_CATEGORIES == 2) result = sports;
            return result;
        }

        public bool CanGameContinueAfterCorrectAnswer()
        {
            bool result;
            if (CurrentPlayer().isInPenaltyBox)
            {
                if (CurrentPlayer().canAnswerQuestion)
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
            CurrentPlayer().purse++;
            Console.WriteLine(CurrentPlayer().name
                    + " now has "
                    + CurrentPlayer().purse
                    + " Gold Coins.");
        }

        public bool WrongAnswer()
        {
            Console.WriteLine("Question was incorrectly answered");
            Console.WriteLine(CurrentPlayer().name + " was sent to the penalty box");
            CurrentPlayer().isInPenaltyBox = true;
            ChangeCurrentPlayer();
            return true;
        }

        private void ChangeCurrentPlayer()
        {
            currentPlayerIndex++;
            if (currentPlayerIndex == NumberOfPlayers()) currentPlayerIndex = 0;
        }

        private bool CanGameContinue()
        {
            return !(CurrentPlayer().purse == WINNING_SCORE);
        }
    }

}