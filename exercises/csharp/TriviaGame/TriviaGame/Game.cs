using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace TriviaGame
{
    public class Game
    {
        bool isGettingOutOfPenaltyBox;

        private Player _player;
        private Board _board;
        private Category _category;

        public Game(Player player, Board board, Category category)
        {
            _player = player;
            _board = board;
            _category = category;

        }

        public bool Add(string playerName)
        {
            return _player.Add(playerName);
        }


        public void Roll(int roll)
        {
            Console.WriteLine($"{_player.players[StaticVariables.CurrentPlayer]} is the current player");
            Console.WriteLine($"They have rolled a {roll}");

            if (_player.inPenaltyBox[StaticVariables.CurrentPlayer])
            {

                _board.IsGettingOutOfPenaltyBox(roll);
            }
            else
            {

                _board.UpdatePlayerPosition(roll);

                _category.AskQuestion();
            }

        }

        public bool WasCorrectlyAnswered()
        {
            if (_player.inPenaltyBox[StaticVariables.CurrentPlayer])
            {
                if (isGettingOutOfPenaltyBox)
                {
                   return PlayerAnsweredCorrect();

                }

                StaticVariables.CurrentPlayer++;
                if (StaticVariables.CurrentPlayer == _player.players.Count) StaticVariables.CurrentPlayer = 0;
                return true;

            }

            return PlayerAnsweredCorrect();
        }

        private bool PlayerAnsweredCorrect()
        {
            Console.WriteLine("Answer was correct!!!!");
            _player.playerScores[StaticVariables.CurrentPlayer]++;
            Console.WriteLine($"{_player.players[StaticVariables.CurrentPlayer]} now has {_player.playerScores[StaticVariables.CurrentPlayer]} Gold Coins.");

            bool winner = DidPlayerWin();
            StaticVariables.CurrentPlayer++;
            if (StaticVariables.CurrentPlayer == _player.players.Count) StaticVariables.CurrentPlayer = 0;

            return winner;
        }

        public bool WrongAnswer()
        {
            Console.WriteLine("Question was incorrectly answered");
            Console.WriteLine($"{_player.players[StaticVariables.CurrentPlayer]} was sent to the penalty box");
            _player.inPenaltyBox[StaticVariables.CurrentPlayer] = true;

            StaticVariables.CurrentPlayer++;
            if (StaticVariables.CurrentPlayer == _player.players.Count) StaticVariables.CurrentPlayer = 0;
            return true;
        }

        private bool DidPlayerWin()
        {
            return _player.playerScores[StaticVariables.CurrentPlayer] != 6;
        }
    }

}