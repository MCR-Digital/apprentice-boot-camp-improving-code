using System;
using System.Collections.Generic;
using System.Text;

namespace TriviaGame
{
    public class Board
    {
        bool isGettingOutOfPenaltyBox;

        private Player _player;
        private Category _category;

        public Board(Player player, Category category)
        {
            _player = player;
            _category = category;
        }

        public void IsGettingOutOfPenaltyBox(int roll)
        {
            if (roll % 2 != 0)
            {
                isGettingOutOfPenaltyBox = true;

                Console.WriteLine(_player.players[StaticVariables.CurrentPlayer] + " is getting out of the penalty box");
                UpdatePlayerPosition(roll);

                Console.WriteLine($"{_player.players[StaticVariables.CurrentPlayer]}'s new location is {_player.playerPlaces[StaticVariables.CurrentPlayer]}");
                Console.WriteLine($"The category is {_category.CurrentCategory()}");
                _category.AskQuestion();
            }
            else
            {
                Console.WriteLine($"{_player.players[StaticVariables.CurrentPlayer]} is not getting out of the penalty box");
                isGettingOutOfPenaltyBox = false;
            }
        }

        public void UpdatePlayerPosition(int roll)
        {
            _player.playerPlaces[StaticVariables.CurrentPlayer] = _player.playerPlaces[StaticVariables.CurrentPlayer] + roll;
            if (_player.playerPlaces[StaticVariables.CurrentPlayer] > 11) _player.playerPlaces[StaticVariables.CurrentPlayer] = _player.playerPlaces[StaticVariables.CurrentPlayer] - 12;

            Console.WriteLine($"{_player.players[StaticVariables.CurrentPlayer]}'s new location is {_player.playerPlaces[StaticVariables.CurrentPlayer]}");
            Console.WriteLine($"The category is {_category.CurrentCategory()}");
        }
    }
}
