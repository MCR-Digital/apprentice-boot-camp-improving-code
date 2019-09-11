using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace TriviaGame
{
    public class Game
    {
        private const int MIN_PLAYER_COUNT = 2;

        private readonly List<Player> _players = new List<Player>();
        private readonly Dictionary<Category, QuestionDeck> _questions;

        private int _currentPlayerIndex;
        private bool _isGettingOutOfPenaltyBox;

        public Game()
        {
            _questions = new Dictionary<Category, QuestionDeck>()
            {
                { Category.Pop, new QuestionDeck(Category.Pop) },
                { Category.Science, new QuestionDeck(Category.Science) },
                { Category.Sports, new QuestionDeck(Category.Sports) },
                { Category.Rock, new QuestionDeck(Category.Rock) },
            };
        }

        public bool IsPlayable()
        {
            return (PlayerCount >= MIN_PLAYER_COUNT);
        }

        public void AddPlayer(Player player)
        {
            _players.Add(player);
            GameWriter.WritePlayerAdded(player.Name, _players.Count);
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
            GameWriter.WriteCurrentPlayerRoll(CurrentPlayer.Name, rollNumber);

            if (CurrentPlayer.IsInPenaltyBox)
            {
                if (rollNumber % 2 != 0)
                {
                    _isGettingOutOfPenaltyBox = true;

                    MoveCurrentPlayer(rollNumber);

                    GameWriter.WritePlayerLeavingPenaltyBox(CurrentPlayer.Name);
                    GameWriter.WritePlayerNewLocation(CurrentPlayer.Name, CurrentPlayer.Place);
                    GameWriter.WriteCategory(GetCurrentCategory());

                    PrintQuestionForCurrentCategory();
                }
                else
                {
                    GameWriter.WritePlayerNotLeavingPenaltyBox(CurrentPlayer.Name);
                    _isGettingOutOfPenaltyBox = false;
                }

            }
            else
            {
                MoveCurrentPlayer(rollNumber);

                GameWriter.WritePlayerNewLocation(CurrentPlayer.Name, CurrentPlayer.Place);
                GameWriter.WriteCategory(GetCurrentCategory());

                PrintQuestionForCurrentCategory();
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

        private void PrintQuestionForCurrentCategory()
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
            if (CurrentPlayer.IsInPenaltyBox && !_isGettingOutOfPenaltyBox)
            {
                MoveToNextPlayer();
                return true;
            }

            CurrentPlayer.Coins++;

            GameWriter.WriteAnswerWasCorrect();
            GameWriter.WriteNewCoinAmount(CurrentPlayer.Name, CurrentPlayer.Coins);

            bool winner = GetCurrentPlayerWinStatus();
            MoveToNextPlayer();

            return winner;
        }

        public bool GiveCurrentPlayerWrongAnswer()
        {
            GameWriter.WriteAnswerWasIncorrect(CurrentPlayer.Name);
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