using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace TriviaGame
{
    public class Game
    {

        int currentPlayer = 0;
        bool isPlayerGettingOutOfPenaltyBox;
        List<Player> players = new List<Player>();
        Questions questions = new Questions();

        public Game()
        {
        }

        public void AddPlayer(string playerName)
        {
            players.Add(new Player(playerName));

            Console.WriteLine(playerName + " was added");
            Console.WriteLine("They are player number " + GetPlayerCount());
        }

        {
        }

        public int PlayerCount()
        {
            return players.Count;
        }

        public void RollDice(int diceResult)
        {
            Console.WriteLine(players[currentPlayer] + " is the current player");
            Console.WriteLine("They have rolled a " + diceResult);

            if (playerPenaltyBoxStatuses[currentPlayer])
            {
                if (diceResult % 2 != 0)
                {
                    isPlayerGettingOutOfPenaltyBox = true;

                    Console.WriteLine(players[currentPlayer] + " is getting out of the penalty box");
                    playerPlaces[currentPlayer] = playerPlaces[currentPlayer] + diceResult;
                    if (playerPlaces[currentPlayer] > 11) playerPlaces[currentPlayer] = playerPlaces[currentPlayer] - 12;

                    Console.WriteLine(players[currentPlayer]
                            + "'s new location is "
                            + playerPlaces[currentPlayer]);
                    questions.AskQuestion(currentPlayer.Place);
                }
                else
                {
                    Console.WriteLine(players[currentPlayer] + " is not getting out of the penalty box");
                    isPlayerGettingOutOfPenaltyBox = false;
                }

            }
            else
            {

                playerPlaces[currentPlayer] = playerPlaces[currentPlayer] + diceResult;
                if (playerPlaces[currentPlayer] > 11) playerPlaces[currentPlayer] = playerPlaces[currentPlayer] - 12;

                Console.WriteLine(players[currentPlayer]
                        + "'s new location is "
                        + playerPlaces[currentPlayer]);
                questions.AskQuestion(currentPlayer.Place);
            }

        }

        {


        {
        }

        public bool CorrectAnswer()
        {
            if (playerPenaltyBoxStatuses[currentPlayer])
            {
                if (isPlayerGettingOutOfPenaltyBox)
                {
                    Console.WriteLine("Answer was correct!!!!");
                    playerPurses[currentPlayer]++;
                    Console.WriteLine(players[currentPlayer]
                            + " now has "
                            + playerPurses[currentPlayer]
                            + " Gold Coins.");

                    bool isPlayerWinner = DidPlayerWin();
                    currentPlayer++;
                    if (currentPlayer == players.Count) currentPlayer = 0;

                    return isPlayerWinner;
                }
                else
                {
                    currentPlayer++;
                    if (currentPlayer == players.Count) currentPlayer = 0;
                    return true;
                }



            }
            else
            {

                Console.WriteLine("Answer was corrent!!!!");
                playerPurses[currentPlayer]++;
                Console.WriteLine(players[currentPlayer]
                        + " now has "
                        + playerPurses[currentPlayer]
                        + " Gold Coins.");

                bool winner = DidPlayerWin();
                currentPlayer++;
                if (currentPlayer == players.Count) currentPlayer = 0;

                return winner;
            }
        }

        public bool WrongAnswer()
        {
            Console.WriteLine("Question was incorrectly answered");
            Console.WriteLine(players[currentPlayer] + " was sent to the penalty box");
            playerPenaltyBoxStatuses[currentPlayer] = true;

            currentPlayer++;
            if (currentPlayer == players.Count) currentPlayer = 0;
            return true;
        }

        {
        }
    }

}