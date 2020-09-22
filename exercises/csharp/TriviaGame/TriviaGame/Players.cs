using System;
using System.Collections.Generic;
using System.Text;

namespace TriviaGame
{
    public class Players
    {
        public List<Player> playerList { get; set; }
        public Player currentPlayer { get; set; }
        int currentPlayerIndex { get; set; }
        public int Count { get; set; }

        public Players()
        {
            playerList = new List<Player>();
            currentPlayerIndex = 0;
        }

        public void AddPlayer(string playerName)
        {
            playerList.Add(new Player(playerName));
            Count = playerList.Count;
            Console.WriteLine(playerName + " was added");
            Console.WriteLine("They are player number " + Count);
        }

        public Player GetCurrentPlayer()
        {
            currentPlayer = playerList[currentPlayerIndex];
            return currentPlayer;
        }

        public void MoveCurrentPlayer(int numberOfSpaces)
        {
            var gameBoardSize = 12;

            currentPlayer.Place = currentPlayer.Place + numberOfSpaces;
            if (currentPlayer.Place > gameBoardSize - 1) currentPlayer.Place = currentPlayer.Place - gameBoardSize;
        }

        public void NextPlayer()
        {
            currentPlayerIndex++;
            if (currentPlayerIndex == Count) currentPlayerIndex = 0;
        }
    }
}
