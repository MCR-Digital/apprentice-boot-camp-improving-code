using System;
using System.Collections.Generic;

namespace TriviaGame
{
    public class Player
    {
        public List<string> totalPlayers;
        public int[] playerBoardPositionState = new int[6];
        public int[] playerPurseTotalState = new int[6];

        public bool[] playerPenaltyBoxState = new bool[6];

        public bool AddPlayer(string playerName)
        {
            totalPlayers.Add(playerName);
            playerBoardPositionState[GetTotalPlayersCount()] = 0;
            playerPurseTotalState[GetTotalPlayersCount()] = 0;
            playerPenaltyBoxState[GetTotalPlayersCount()] = false;
            PrintNewPlayerDetails(playerName);
            return true;
        }

        private void PrintNewPlayerDetails(string playerName)
        {
            Console.WriteLine(playerName + " was added");
            Console.WriteLine("They are player number " + totalPlayers.Count);
        }

        public int GetTotalPlayersCount()
        {
            return totalPlayers.Count;
        }

    }
}