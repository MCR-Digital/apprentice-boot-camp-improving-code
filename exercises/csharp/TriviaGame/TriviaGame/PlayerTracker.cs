using System.Collections.Generic;

namespace TriviaGame
{
    public class PlayerTracker
    {
        private int _currentPlayerIndex = 0;

        public PlayerTracker()
        {
            Players = new List<Player>();
        }

        public List<Player> Players { get; private set; }

        public Player CurrentPlayer
        {
            get
            {
                return Players[_currentPlayerIndex];
            }
        }

        public void MoveToNextPlayer()
        {
            _currentPlayerIndex++;

            if (_currentPlayerIndex == Players.Count)
            {
                _currentPlayerIndex = 0;
            }
        }

        public void Add(Player player)
        {
            Players.Add(player);
        }
    }
}
