using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace TriviaGame
{
    public class Game
    {
        private const int MIN_PLAYER_COUNT = 2;
        private const int MAX_QUESTIONS = 50;

        private readonly List<Player> _players = new List<Player>();
        private readonly Dictionary<Category, QuestionDeck> _questions;

        private int _currentPlayerIndex;
        private bool _isGettingOutOfPenaltyBox;

        public Game()
        {
            _questions = new Dictionary<Category, QuestionDeck>()
            {
                { Category.Pop, new QuestionDeck(Category.Pop, MAX_QUESTIONS) },
                { Category.Science, new QuestionDeck(Category.Science, MAX_QUESTIONS) },
                { Category.Sports, new QuestionDeck(Category.Sports, MAX_QUESTIONS) },
                { Category.Rock, new QuestionDeck(Category.Rock, MAX_QUESTIONS) },
            };
        }

        public bool IsPlayable()
        {
            return (PlayerCount >= MIN_PLAYER_COUNT);
        }

        public void AddPlayer(Player player)
        {
            _players.Add(player);

            Console.WriteLine(player.Name + " was added");
            Console.WriteLine("They are player number " + _players.Count);
        }

        public int PlayerCount
        {
            get
            {
                return _players.Count;
            }
        }

        public Player CurrentPlayer
        {
            get
            {
                return _players[_currentPlayerIndex];
            }
        }

        public void RollDice(int rollNumber)
        {
            Console.WriteLine(CurrentPlayer.Name + " is the current player");
            Console.WriteLine("They have rolled a " + rollNumber);

            if (CurrentPlayer.IsInPenaltyBox)
            {
                if (rollNumber % 2 != 0)
                {
                    _isGettingOutOfPenaltyBox = true;

                    Console.WriteLine(CurrentPlayer.Name + " is getting out of the penalty box");
                    MoveCurrentPlayer(rollNumber);

                    Console.WriteLine(CurrentPlayer.Name
                            + "'s new location is "
                            + CurrentPlayer.Place);
                    Console.WriteLine("The category is " + GetCurrentCategory());
                    PrintQuestionAndRemoveFromList();
                }
                else
                {
                    Console.WriteLine(CurrentPlayer.Name + " is not getting out of the penalty box");
                    _isGettingOutOfPenaltyBox = false;
                }

            }
            else
            {
                MoveCurrentPlayer(rollNumber);

                Console.WriteLine(CurrentPlayer.Name
                        + "'s new location is "
                        + CurrentPlayer.Place);
                Console.WriteLine("The category is " + GetCurrentCategory());
                PrintQuestionAndRemoveFromList();
            }

        }

        private void MoveCurrentPlayer(int places)
        {
            CurrentPlayer.Place += places;

            if (CurrentPlayer.Place > 11)
            {
                CurrentPlayer.Place -= 12;
            }
        }

        private void PrintQuestionAndRemoveFromList()
        {
            var currentCategory = GetCurrentCategory();
            var currentQuestion = _questions[currentCategory].GetNext();
            Console.WriteLine(currentQuestion);
        }


        private Category GetCurrentCategory()
        {
            switch (CurrentPlayer.Place)
            {
                case 0:
                case 4:
                case 8:
                    return Category.Pop;
                case 1:
                case 5:
                case 9:
                    return Category.Science;
                case 2:
                case 6:
                case 10:
                    return Category.Sports;
                default:
                    return Category.Rock;
            }
        }

        public bool wasCorrectlyAnswered()
        {
            if (CurrentPlayer.IsInPenaltyBox)
            {
                if (_isGettingOutOfPenaltyBox)
                {
                    Console.WriteLine("Answer was correct!!!!");
                    CurrentPlayer.Coins++;
                    PrintCurrentPlayerCoins();

                    bool winner = GetCurrentPlayerWinStatus();
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
                Console.WriteLine("Answer was correct!!!!");
                CurrentPlayer.Coins++;
                PrintCurrentPlayerCoins();

                bool winner = GetCurrentPlayerWinStatus();
                MoveToNextPlayer();

                return winner;
            }
        }

        private void PrintCurrentPlayerCoins()
        {
            Console.WriteLine(CurrentPlayer.Name + " now has " + CurrentPlayer.Coins + " Gold Coins.");
        }

        public bool GiveCurrentPlayerWrongAnswer()
        {
            Console.WriteLine("Question was incorrectly answered");
            Console.WriteLine(CurrentPlayer.Name + " was sent to the penalty box");
            CurrentPlayer.IsInPenaltyBox = true;

            MoveToNextPlayer();
            return true;
        }


        private bool GetCurrentPlayerWinStatus()
        {
            return CurrentPlayer.Coins != 6;
        }

        private void MoveToNextPlayer()
        {
            _currentPlayerIndex++;

            if (_currentPlayerIndex == PlayerCount)
            {
                _currentPlayerIndex = 0;
            }
        }
    }
}