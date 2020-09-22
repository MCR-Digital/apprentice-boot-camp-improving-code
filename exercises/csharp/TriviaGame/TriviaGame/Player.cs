using System;
using System.Collections.Generic;
using System.Text;

namespace TriviaGame
{
    public class Player
    {
       public List<string> players = new List<string>();

       public int[] playerPlaces = new int[6];
       public int[] playerScores = new int[6];
       public bool[] inPenaltyBox = new bool[6];

       public bool Add(string playerName)
        {

            players.Add(playerName);
            playerPlaces[HowManyPlayers()] = 0;
            playerScores[HowManyPlayers()] = 0;
            inPenaltyBox[HowManyPlayers()] = false;

            Console.WriteLine($"{playerName} was added");
            Console.WriteLine($"They are player number {players.Count}");
            return true;
        }

        public int HowManyPlayers()
        {
            return players.Count;
        }
    }
}
