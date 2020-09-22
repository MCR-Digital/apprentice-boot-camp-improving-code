using System;
using System.Collections.Generic;
using System.Linq;

namespace TriviaGame
{
    public class Game
    {
        private readonly int[] _popSpaces = {0, 4, 8};
        private readonly int[] _scienceSpaces = {1, 5, 9};
        private readonly int[] _sportSpaces = {2, 6, 10};
        private readonly int[] _rockSpaces = {3, 7, 11};
        
        private readonly List<Player> _gamePlayers = new List<Player>();

        private int _currentPlayerIndex;
        private readonly ScienceQuestion _scienceQuestion;
        private readonly RockQuestion _rockQuestion;
        private readonly SportsQuestion _sportsQuestion;
        private readonly PopQuestion _popQuestion;

        private Player _currentPlayer;
        public Game()
        {
            _scienceQuestion = new ScienceQuestion();
            _rockQuestion = new RockQuestion();
            _sportsQuestion = new SportsQuestion();
            _popQuestion = new PopQuestion();

            for (int i = 0; i < Constants.NumberOfQuestions; i++)
            {
                _scienceQuestion.Questions.AddLast($"Science Question {i}");
                _popQuestion.Questions.AddLast($"Pop Question {i}");
                _sportsQuestion.Questions.AddLast($"Sports Question {i}");
                _rockQuestion.Questions.AddLast($"Rock Question {i}");
            }
        }

        public bool IsPlayable()
        {
            return _gamePlayers.Count >= Constants.MinPlayers;
        }

        public bool AddPlayer(string playerName)
        {
            var player = new Player(playerName);
            _gamePlayers.Add(player);
            Console.WriteLine($"They are player number {_gamePlayers.Count}");
            return true;
        }

        public void Roll(int roll)
        {
            _currentPlayer = _gamePlayers[_currentPlayerIndex];
            
            Console.WriteLine($"{_currentPlayer.Name} is the current player");
            Console.WriteLine($"They have rolled a {roll}");

            if (_currentPlayer.InPenaltyBox)
            {
                if (CanLeavePenaltyBox(roll))
                {
                    MovePlayer(roll, _currentPlayer.Name);
                    AskQuestion();
                }
                else
                {
                    WritePenaltyBoxMessage();
                    _currentPlayer.CanAnswerQuestion = false;
                }
            }
            else
            {
                MovePlayer(roll, _currentPlayer.Name);
                AskQuestion();
            }
        }

        private void MovePlayer(int roll, string playerName)
        {
            _currentPlayer.CanAnswerQuestion = true;

            if (_currentPlayer.InPenaltyBox)
            {
                WritePenaltyBoxMessage(_currentPlayer.CanAnswerQuestion);
            }
            _currentPlayer.Position += roll;
            BoardWrapAround();

            Console.WriteLine($"{playerName}'s new location is {_currentPlayer.Position}");
            Console.WriteLine($"The category is {CurrentCategory()}");
        }

        private void BoardWrapAround()
        {
            if (_currentPlayer.Position > Constants.EndOfBoard)
            {
                _currentPlayer.Position -= Constants.LengthOfBoard;
            }
        }

        private void WritePenaltyBoxMessage(bool gettingOut = false)
        {
            Console.WriteLine(
                $"{_currentPlayer.Name} {(gettingOut ? "is" : "is not")} getting out of the penalty box");
        }

        private static bool CanLeavePenaltyBox(int roll)
        {
            return roll % 2 != 0;
        }

        private void AskQuestion()
        {
            if (CurrentCategory() == Category.Pop)
            {
                Console.WriteLine(_popQuestion.Questions.First());
                _popQuestion.Questions.RemoveFirst();
            }

            if (CurrentCategory() == Category.Science)
            {
                Console.WriteLine(_scienceQuestion.Questions.First());
                _scienceQuestion.Questions.RemoveFirst();
            }

            if (CurrentCategory() == Category.Sports)
            {
                Console.WriteLine(_sportsQuestion.Questions.First());
                _sportsQuestion.Questions.RemoveFirst();
            }

            if (CurrentCategory() == Category.Rock)
            {
                Console.WriteLine(_rockQuestion.Questions.First());
                _rockQuestion.Questions.RemoveFirst();
            }
        }


        private string CurrentCategory()
        {
            switch (CurrentPlayerPosition())
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

        private int CurrentPlayerPosition()
        {
            return _currentPlayer.Position;
        }

        public bool WasCorrectlyAnswered()
        {
            bool winner;
            if (_currentPlayer.InPenaltyBox)
            {
                if (_currentPlayer.CanAnswerQuestion)
                {
                    Console.WriteLine("Answer was correct!!!!");
                    _currentPlayer.Coins++;
                    PrintCurrentPlayerCoins();
                    winner = DidPlayerWin();
                    _currentPlayerIndex++;
                    CheckPlayerWrapAround();

                    return winner;
                }
                
                _currentPlayerIndex++;
                CheckPlayerWrapAround();
                return true;
            }
            
            Console.WriteLine("Answer was corrent!!!!");
            _currentPlayer.Coins++;
            PrintCurrentPlayerCoins();
            
            winner = DidPlayerWin();
            _currentPlayerIndex++;
            CheckPlayerWrapAround();

            return winner;
        }

        private void CheckPlayerWrapAround()
        {
            if (_currentPlayerIndex == _gamePlayers.Count)
            {
                _currentPlayerIndex = Constants.FirstPlayer;
            }
        }

        private void PrintCurrentPlayerCoins()
        {
            Console.WriteLine(
                $"{_currentPlayer.Name} now has {_currentPlayer.Coins} Gold Coins.");
        }

        public bool WrongAnswer()
        {
            Console.WriteLine("Question was incorrectly answered");
            Console.WriteLine($"{_currentPlayer.Name} was sent to the penalty box");
            _currentPlayer.InPenaltyBox = true;

            _currentPlayerIndex++;
            CheckPlayerWrapAround();
            return true;
        }


        private bool DidPlayerWin()
        {
            return _currentPlayer.Coins != Constants.WinningScore;
        }
    }
}