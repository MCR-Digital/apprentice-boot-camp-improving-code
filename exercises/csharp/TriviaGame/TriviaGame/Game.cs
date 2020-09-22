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
        private const int NumberOfQuestions = 50;
        private readonly int[] _popSpaces = {0, 4, 8};
        private readonly int[] _scienceSpaces = {1, 5, 9};
        private readonly int[] _sportSpaces = {2, 6, 10};
        private readonly int[] _rockSpaces = {3, 7, 11};
        
        private readonly List<Player> _gamePlayers = new List<Player>();
        private int numberOfPlayers = 0;
        private readonly int[] _playerPositions = new int[MaxPlayers];
        private readonly int[] _playerPurses = new int[MaxPlayers];
        private readonly bool[] _inPenaltyBox = new bool[MaxPlayers];

        private int _currentPlayerIndex;
        private bool _playerCanAnswerQuestion;
        private string _currentPlayerName;
        private readonly ScienceQuestion _scienceQuestion;
        private RockQuestion _rockQuestion;
        private SportsQuestion _sportsQuestion;
        private PopQuestion _popQuestion;


        public Game()
        {
            _scienceQuestion = new ScienceQuestion();
            _rockQuestion = new RockQuestion();
            _sportsQuestion = new SportsQuestion();
            _popQuestion = new PopQuestion();

            for (int i = 0; i < NumberOfQuestions; i++)
            {
                _scienceQuestion.Questions.AddLast($"Science Question {i}");
                _popQuestion.Questions.AddLast($"Pop Question {i}");
                _sportsQuestion.Questions.AddLast($"Sports Question {i}");
                _rockQuestion.Questions.AddLast($"Rock Question {i}");
            }
        }

        public bool IsPlayable()
        {
            return _gamePlayers.Count >= MinPlayers;
        }

        public bool AddPlayer(string playerName)
        {
            var newPLayer = new Player(playerName);
            _gamePlayers.Add(newPLayer);
            _playerPositions[_gamePlayers.Count] = 0;
            _playerPurses[_gamePlayers.Count] = 0;
            _inPenaltyBox[_gamePlayers.Count] = false;

            Console.WriteLine($"{playerName} was added");
            Console.WriteLine($"They are player number {_gamePlayers.Count}");
            return true;
        }

        public void Roll(int roll)
        {
            _currentPlayerName = _gamePlayers[_currentPlayerIndex].Name;
            Console.WriteLine($"{_currentPlayerName} is the current player");
            Console.WriteLine($"They have rolled a {roll}");

            if (_inPenaltyBox[_currentPlayerIndex])
            {
                if (CanLeavePenaltyBox(roll))
                {
                    MovePlayer(roll, _currentPlayerName, true);
                    AskQuestion();
                }
                else
                {
                    WritePenaltyBoxMessage();
                    _playerCanAnswerQuestion = false;
                }
            }
            else
            {
                MovePlayer(roll, _currentPlayerName);
                AskQuestion();
            }
        }

        private void MovePlayer(int roll, string playerName, bool showPenaltyBoxMessage = false)
        {
            _playerCanAnswerQuestion = true;

            if (showPenaltyBoxMessage)
            {
                WritePenaltyBoxMessage(_playerCanAnswerQuestion);
            }
            _playerPositions[_currentPlayerIndex] = _playerPositions[_currentPlayerIndex] + roll;
            if (_playerPositions[_currentPlayerIndex] > EndOfBoard)
            {
                _playerPositions[_currentPlayerIndex] = _playerPositions[_currentPlayerIndex] - LengthOfBoard;
            }

            Console.WriteLine($"{playerName}'s new location is {_playerPositions[_currentPlayerIndex]}");
            Console.WriteLine($"The category is {CurrentCategory()}");
        }

        private void WritePenaltyBoxMessage(bool gettingOut = false)
        {
            Console.WriteLine(
                $"{_currentPlayerName} {(gettingOut ? "is" : "is not")} getting out of the penalty box");
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
            return _playerPositions[_currentPlayerIndex];
        }

        public bool WasCorrectlyAnswered()
        {
            bool winner;
            if (_inPenaltyBox[_currentPlayerIndex])
            {
                if (_playerCanAnswerQuestion)
                {
                    Console.WriteLine("Answer was correct!!!!");
                    _playerPurses[_currentPlayerIndex]++;
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
            _playerPurses[_currentPlayerIndex]++;
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
                _currentPlayerIndex = FirstPlayer;
            }
        }

        private void PrintCurrentPlayerCoins()
        {
            Console.WriteLine(
                $"{_currentPlayerName} now has {_playerPurses[_currentPlayerIndex]} Gold Coins.");
        }

        public bool WrongAnswer()
        {
            Console.WriteLine("Question was incorrectly answered");
            Console.WriteLine($"{_currentPlayerName} was sent to the penalty box");
            _inPenaltyBox[_currentPlayerIndex] = true;

            _currentPlayerIndex++;
            CheckPlayerWrapAround();
            return true;
        }


        private bool DidPlayerWin()
        {
            return _playerPurses[_currentPlayerIndex] != WinningScore;
        }
    }
}