using System;
using System.Collections.Generic;

namespace TriviaGame
{
    public class Player
    {
        private int[] playersPosition = new int[6];
        private int[] playersPurses = new int[6];
        private int currentPlayer;
        bool[] inPenaltyBox = new bool[6];

        public List<string> listOfAllPlayers = new List<string>();

        public Player()
        {

        }

        public Player(int[] playersPosition, int[] playersPurses, int currentPlayer, List<string> listOfAllPlayers)
        {
            this.playersPosition = playersPosition;
            this.playersPurses = playersPurses;
            this.currentPlayer = currentPlayer;
            this.listOfAllPlayers = listOfAllPlayers;
        }

        public bool AddNewPlayer(string playerName)
        {
            listOfAllPlayers.Add(playerName);
            playersPosition[HowManyPlayersInCurrentGame()] = 0;
            playersPurses[HowManyPlayersInCurrentGame()] = 0;
            inPenaltyBox[HowManyPlayersInCurrentGame()] = false;

            Console.WriteLine(playerName + " was added");
            Console.WriteLine("They are player number " + listOfAllPlayers.Count);
            return true;
        }

        public int HowManyPlayersInCurrentGame()
        {
            return listOfAllPlayers.Count;
        }
    }
}
