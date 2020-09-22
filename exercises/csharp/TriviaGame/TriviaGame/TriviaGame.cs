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
        List<Player> players = new List<Player>();

        int[] playerPurses = new int[6];

        bool[] inPenaltyBox = new bool[6];

        private readonly Category pop = new Category(POP_CATEGORY, new LinkedList<string>());
        private readonly Category science = new Category(SCIENCE_CATEGORY, new LinkedList<string>());
        private readonly Category sports = new Category(SPORTS_CATEGORY, new LinkedList<string>());
        private readonly Category rock = new Category(ROCK_CATEGORY, new LinkedList<string>());

        int currentPlayerIndex = 0;
        bool playerCanAnswerQuestion;

        public TriviaGame()
        {
            for (int questionIndex = 0; questionIndex < 50; questionIndex++)
            {
                pop.AddQuestion(questionIndex);
                science.AddQuestion(questionIndex);
                sports.AddQuestion(questionIndex);
                rock.AddQuestion(questionIndex);
            }
        }

        public bool IsGamePlayable()
        {
            return (NumberOfPlayers() >= 2);
        }

        public bool AddPlayer(string playerName)
        {
            players.Add(new Player(playerName));
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

        private Player CurrentPlayer()
        {
            return players[currentPlayerIndex];
        }

        public void RollDice(int roll)
        {
            Console.WriteLine(players[currentPlayerIndex].playerName + " is the current player");
            Console.WriteLine("They have rolled a " + roll);

            if (inPenaltyBox[currentPlayerIndex])
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
                Console.WriteLine(CurrentPlayer().playerName + " is getting out of the penalty box");
                PlayTurn(roll);
            }
            else
            {
                Console.WriteLine(CurrentPlayer().playerName + " is not getting out of the penalty box");
                playerCanAnswerQuestion = false;
            }
        }

        private void PlayTurn(int roll)
        {
            CurrentPlayer().playerLocation = CurrentPlayer().playerLocation + roll;
            if (CurrentPlayer().playerLocation > 11) CurrentPlayer().playerLocation = CurrentPlayer().playerLocation - 12;

            Console.WriteLine(CurrentPlayer().playerName
                    + "'s new location is "
                    + CurrentPlayer().playerLocation);
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
            int playerLocation = CurrentPlayer().playerLocation;
            Category result = rock;
            if (playerLocation % NUMBER_OF_CATEGORIES == 0) result = pop;
            if (playerLocation % NUMBER_OF_CATEGORIES == 1) result = science;
            if (playerLocation % NUMBER_OF_CATEGORIES == 2) result = sports;
            return result;
        }

        public bool CanGameContinueAfterCorrectAnswer()
        {
            bool result;
            if (inPenaltyBox[currentPlayerIndex])
            {
                if (playerCanAnswerQuestion)
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
            CurrentPlayer().playerPurse++;
            Console.WriteLine(CurrentPlayer().playerName
                    + " now has "
                    + CurrentPlayer().playerPurse
                    + " Gold Coins.");
        }

        public bool WrongAnswer()
        {
            Console.WriteLine("Question was incorrectly answered");
            Console.WriteLine(CurrentPlayer().playerName + " was sent to the penalty box");
            inPenaltyBox[currentPlayerIndex] = true;
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
            return !(CurrentPlayer().playerPurse == WINNING_SCORE);
        }
    }

}