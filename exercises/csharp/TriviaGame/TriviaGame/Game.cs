using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace TriviaGame
{
    public class Game
    {
        List<string> players = new List<string>();

        int[] playerPlaces = new int[6];
        int[] playerPurses = new int[6];

        bool[] playerPenaltyBoxStatuses = new bool[6];

        LinkedList<string> popQuestions = new LinkedList<string>();
        LinkedList<string> scienceQuestions = new LinkedList<string>();
        LinkedList<string> sportsQuestions = new LinkedList<string>();
        LinkedList<string> rockQuestions = new LinkedList<string>();

        int currentPlayer = 0;
        bool isPlayerGettingOutOfPenaltyBox;

        public Game()
        {
            for (int i = 0; i < 50; i++)
            {
                popQuestions.AddLast("Pop Question " + i);
                scienceQuestions.AddLast(("Science Question " + i));
                sportsQuestions.AddLast(("Sports Question " + i));
                rockQuestions.AddLast(CreateRockQuestion(i));
            }
        }

        public string CreateRockQuestion(int index)
        {
            return "Rock Question " + index;
        }

        public bool IsGamePlayable()
        {
            return (PlayerCount() >= 2);
        }

        public bool AddPlayer(string playerName)
        {
            players.Add(playerName);
            playerPlaces[PlayerCount()] = 0;
            playerPurses[PlayerCount()] = 0;
            playerPenaltyBoxStatuses[PlayerCount()] = false;

            Console.WriteLine(playerName + " was added");
            Console.WriteLine("They are player number " + players.Count);
            return true;
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
                    Console.WriteLine("The category is " + CurrentCategory());
                    AskQuestion();
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
                Console.WriteLine("The category is " + CurrentCategory());
                AskQuestion();
            }

        }

        private void AskQuestion()
        {
            if (CurrentCategory() == "Pop")
            {
                Console.WriteLine(popQuestions.First());
                popQuestions.RemoveFirst();
            }
            if (CurrentCategory() == "Science")
            {
                Console.WriteLine(scienceQuestions.First());
                scienceQuestions.RemoveFirst();
            }
            if (CurrentCategory() == "Sports")
            {
                Console.WriteLine(sportsQuestions.First());
                sportsQuestions.RemoveFirst();
            }
            if (CurrentCategory() == "Rock")
            {
                Console.WriteLine(rockQuestions.First());
                rockQuestions.RemoveFirst();
            }
        }


        private string CurrentCategory()
        {
            if (playerPlaces[currentPlayer] == 0) return "Pop";
            if (playerPlaces[currentPlayer] == 4) return "Pop";
            if (playerPlaces[currentPlayer] == 8) return "Pop";
            if (playerPlaces[currentPlayer] == 1) return "Science";
            if (playerPlaces[currentPlayer] == 5) return "Science";
            if (playerPlaces[currentPlayer] == 9) return "Science";
            if (playerPlaces[currentPlayer] == 2) return "Sports";
            if (playerPlaces[currentPlayer] == 6) return "Sports";
            if (playerPlaces[currentPlayer] == 10) return "Sports";
            return "Rock";
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


        private bool DidPlayerWin()
        {
            return !(playerPurses[currentPlayer] == 6);
        }
    }

}