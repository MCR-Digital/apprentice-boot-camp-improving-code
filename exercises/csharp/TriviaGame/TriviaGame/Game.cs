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
        private readonly Board _board;
        private int _currentPlayerIndex;
        private bool _isGettingOutOfPenaltyBox;

        public Game(Board board)
        {
            _questions = new Dictionary<Category, QuestionDeck>()
            {
                { Category.Pop, new QuestionDeck(Category.Pop) },
                { Category.Science, new QuestionDeck(Category.Science) },
                { Category.Sports, new QuestionDeck(Category.Sports) },
                { Category.Rock, new QuestionDeck(Category.Rock) },
            };
            _board = board;
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

        public void RollDice(Dice dice)
        {
            int rolled = dice.Roll();

            GameWriter.WriteCurrentPlayerRoll(CurrentPlayer.Name, rolled);

            if (CurrentPlayer.IsInPenaltyBox && rolled % 2 != 0)
            {
                _isGettingOutOfPenaltyBox = true;
                GameWriter.WritePlayerLeavingPenaltyBox(CurrentPlayer.Name);
            }
            else if (CurrentPlayer.IsInPenaltyBox)
            {
                GameWriter.WritePlayerNotLeavingPenaltyBox(CurrentPlayer.Name);
                _isGettingOutOfPenaltyBox = false;
                return;
            }

            _board.MovePlayer(CurrentPlayer, rolled);

            AskQuestion();
        }

        public void AskQuestion()
        {
            var currentCategory = _board.GetCategoryForPlayer(CurrentPlayer);
            var question = _questions[currentCategory].GetNext();

            GameWriter.WritePlayerNewLocation(CurrentPlayer.Name, CurrentPlayer.Place);
            GameWriter.WriteCategory(currentCategory);
            GameWriter.WriteQuestion(question);
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

            bool winner = CurrentPlayer.Coins == 6;
            MoveToNextPlayer();

            return !winner;
        }

        public bool GiveCurrentPlayerWrongAnswer()
        {
            GameWriter.WriteAnswerWasIncorrect(CurrentPlayer.Name);
            CurrentPlayer.IsInPenaltyBox = true;

            MoveToNextPlayer();
            return true;
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