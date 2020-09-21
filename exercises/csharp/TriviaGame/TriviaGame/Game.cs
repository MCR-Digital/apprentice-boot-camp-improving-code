using System;
using System.Collections.Generic;
using System.Linq;

namespace TriviaGame
{
    public class Game
    {
        private const int WinningScore = 6;
        private const int MaxPlayers = 6;
        private const int MinPlayers = 2;
        private const int EndOfBoard = 11;
        private const int LengthOfBoard = 12;
        private const int FirstPlayer = 0;
        private readonly List<string> _gamePlayers = new List<string>();

        private readonly int[] _playerPositions = new int[MaxPlayers];
        private readonly int[] _playerPurses = new int[MaxPlayers];

        private readonly bool[] _inPenaltyBox = new bool[MaxPlayers];

        private readonly LinkedList<string> _popQuestions = new LinkedList<string>();
        private readonly LinkedList<string> _scienceQuestions = new LinkedList<string>();
        private readonly LinkedList<string> _sportsQuestions = new LinkedList<string>();
        private readonly LinkedList<string> _rockQuestions = new LinkedList<string>();

        private int _currentPlayer;
        private bool _playerCanAnswerQuestion;

        public Game()
        {
            for (int i = 0; i < 50; i++)
            {
                AddDefaultQuestions(i);
            }
        }

        private void AddDefaultQuestions(int i)
        {
            _popQuestions.AddLast($"Pop Question {i}");
            _scienceQuestions.AddLast($"Science Question {i}");
            _sportsQuestions.AddLast($"Sports Question {i}");
            _rockQuestions.AddLast($"Rock Question {i}");
        }

        public bool IsPlayable()
        {
            return (_gamePlayers.Count >= MinPlayers);
        }

        public bool Add(string playerName)
        {
            _gamePlayers.Add(playerName);
            _playerPositions[_gamePlayers.Count] = 0;
            _playerPurses[_gamePlayers.Count] = 0;
            _inPenaltyBox[_gamePlayers.Count] = false;

            Console.WriteLine($"{playerName} was added");
            Console.WriteLine($"They are player number {_gamePlayers.Count}");
            return true;
        }

        public void Roll(int roll)
        {
            var playerName = _gamePlayers[_currentPlayer];
            Console.WriteLine($"{playerName} is the current player");
            Console.WriteLine($"They have rolled a {roll}");

            if (_inPenaltyBox[_currentPlayer])
            {
                if (CanLeavePenaltyBox(roll))
                {
                    _playerCanAnswerQuestion = true;

                    PenaltyBoxMessage(_playerCanAnswerQuestion);
                    _playerPositions[_currentPlayer] = _playerPositions[_currentPlayer] + roll;
                    if (_playerPositions[_currentPlayer] > EndOfBoard)
                        _playerPositions[_currentPlayer] = _playerPositions[_currentPlayer] - LengthOfBoard;

                    Console.WriteLine($"{playerName}'s new location is {_playerPositions[_currentPlayer]}");
                    Console.WriteLine($"The category is {CurrentCategory()}");
                    AskQuestion();
                }
                else
                {
                    PenaltyBoxMessage();
                    _playerCanAnswerQuestion = false;
                }
            }
            else
            {
                _playerPositions[_currentPlayer] = _playerPositions[_currentPlayer] + roll;
                if (_playerPositions[_currentPlayer] > EndOfBoard) _playerPositions[_currentPlayer] = _playerPositions[_currentPlayer] - LengthOfBoard;

                Console.WriteLine($"{playerName}'s new location is {_playerPositions[_currentPlayer]}");
                Console.WriteLine($"The category is {CurrentCategory()}");
                AskQuestion();
            }
        }

        private void PenaltyBoxMessage(bool gettingOut = false)
        {
            Console.WriteLine(
                $"{_gamePlayers[_currentPlayer]} {(gettingOut ? "is" : "is not")} getting out of the penalty box");
        }

        private static bool CanLeavePenaltyBox(int roll)
        {
            return roll % 2 != 0;
        }

        private void AskQuestion()
        {
            if (CurrentCategory() == Category.Pop)
            {
                Console.WriteLine(_popQuestions.First());
                _popQuestions.RemoveFirst();
            }

            if (CurrentCategory() == Category.Science)
            {
                Console.WriteLine(_scienceQuestions.First());
                _scienceQuestions.RemoveFirst();
            }

            if (CurrentCategory() == Category.Sports)
            {
                Console.WriteLine(_sportsQuestions.First());
                _sportsQuestions.RemoveFirst();
            }

            if (CurrentCategory() == Category.Rock)
            {
                Console.WriteLine(_rockQuestions.First());
                _rockQuestions.RemoveFirst();
            }
        }


        private string CurrentCategory()
        {
            var playerPosition = _playerPositions[_currentPlayer];
            if (playerPosition == 0) return Category.Pop;
            if (playerPosition == 4) return Category.Pop;
            if (playerPosition == 8) return Category.Pop;
            if (playerPosition == 1) return Category.Science;
            if (playerPosition == 5) return Category.Science;
            if (playerPosition == 9) return Category.Science;
            if (playerPosition == 2) return Category.Sports;
            if (playerPosition == 6) return Category.Sports;
            if (playerPosition == 10) return Category.Sports;
            return Category.Rock;
        }

        public bool WasCorrectlyAnswered()
        {
            if (_inPenaltyBox[_currentPlayer])
            {
                if (_playerCanAnswerQuestion)
                {
                    Console.WriteLine("Answer was correct!!!!");
                    _playerPurses[_currentPlayer]++;
                    Console.WriteLine($"{_gamePlayers[_currentPlayer]} now has {_playerPurses[_currentPlayer]} Gold Coins.");

                    bool winner = DidPlayerWin();
                    _currentPlayer++;
                    if (_currentPlayer == _gamePlayers.Count) _currentPlayer = FirstPlayer;

                    return winner;
                }
                else
                {
                    _currentPlayer++;
                    if (_currentPlayer == _gamePlayers.Count) _currentPlayer = FirstPlayer;
                    return true;
                }
            }
            else
            {
                Console.WriteLine("Answer was corrent!!!!");
                _playerPurses[_currentPlayer]++;
                Console.WriteLine($"{_gamePlayers[_currentPlayer]} now has {_playerPurses[_currentPlayer]} Gold Coins.");

                bool winner = DidPlayerWin();
                _currentPlayer++;
                if (_currentPlayer == _gamePlayers.Count) _currentPlayer = FirstPlayer;

                return winner;
            }
        }

        public bool WrongAnswer()
        {
            Console.WriteLine("Question was incorrectly answered");
            Console.WriteLine($"{_gamePlayers[_currentPlayer]} was sent to the penalty box");
            _inPenaltyBox[_currentPlayer] = true;

            _currentPlayer++;
            if (_currentPlayer == _gamePlayers.Count) _currentPlayer = FirstPlayer;
            return true;
        }


        private bool DidPlayerWin()
        {
            return _playerPurses[_currentPlayer] != WinningScore;
        }
    }
}