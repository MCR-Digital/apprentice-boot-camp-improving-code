using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace TriviaGame
{
    public class Game
    {
        private const int MAX_PLAYER_COUNT = 6;
        private const int MIN_PLAYER_COUNT = 2;

        private const int MAX_QUESTIONS = 50;

        List<Player> players = new List<Player>();

        int[] playerPlaces = new int[MAX_PLAYER_COUNT];
        int[] playerPurses = new int[MAX_PLAYER_COUNT];

        bool[] inPenaltyBox = new bool[MAX_PLAYER_COUNT];

        LinkedList<string> popQuestions = new LinkedList<string>();
        LinkedList<string> scienceQuestions = new LinkedList<string>();
        LinkedList<string> sportsQuestions = new LinkedList<string>();
        LinkedList<string> rockQuestions = new LinkedList<string>();

        int currentPlayerIndex = 0;
        bool isGettingOutOfPenaltyBox;

        public Game()
        {
            for (int i = 0; i < MAX_QUESTIONS; i++)
            {
                popQuestions.AddLast("Pop Question " + i);
                scienceQuestions.AddLast(("Science Question " + i));
                sportsQuestions.AddLast(("Sports Question " + i));
                rockQuestions.AddLast(CreateRockQuestion(i));
            }
        }

        public String CreateRockQuestion(int index)
        {
            return "Rock Question " + index;
        }

        public bool IsPlayable()
        {
            return (PlayerCount >= MIN_PLAYER_COUNT);
        }

        public bool AddPlayer(Player player)
        {


            players.Add(player);
            playerPlaces[PlayerCount] = 0;
            playerPurses[PlayerCount] = 0;
            inPenaltyBox[PlayerCount] = false;

            Console.WriteLine(player.Name + " was added");
            Console.WriteLine("They are player number " + players.Count);
            return true;
        }

        public int PlayerCount
        {
            get
            {
                return players.Count;
            }
        }

        public void RollDice(int rollNumber)
        {
            Console.WriteLine(players[currentPlayerIndex].Name + " is the current player");
            Console.WriteLine("They have rolled a " + rollNumber);

            if (inPenaltyBox[currentPlayerIndex])
            {
                if (rollNumber % 2 != 0)
                {
                    isGettingOutOfPenaltyBox = true;

                    Console.WriteLine(players[currentPlayerIndex].Name + " is getting out of the penalty box");
                    MoveCurrentPlayer(rollNumber);

                    Console.WriteLine(players[currentPlayerIndex].Name
                            + "'s new location is "
                            + playerPlaces[currentPlayerIndex]);
                    Console.WriteLine("The category is " + GetCurrentCategory());
                    PrintQuestionAndRemoveFromList();
                }
                else
                {
                    Console.WriteLine(players[currentPlayerIndex].Name + " is not getting out of the penalty box");
                    isGettingOutOfPenaltyBox = false;
                }

            }
            else
            {
                MoveCurrentPlayer(rollNumber);

                Console.WriteLine(players[currentPlayerIndex].Name
                        + "'s new location is "
                        + playerPlaces[currentPlayerIndex]);
                Console.WriteLine("The category is " + GetCurrentCategory());
                PrintQuestionAndRemoveFromList();
            }

        }

        private void MoveCurrentPlayer(int places)
        {
            playerPlaces[currentPlayerIndex] = playerPlaces[currentPlayerIndex] + places;
            if (playerPlaces[currentPlayerIndex] > 11) playerPlaces[currentPlayerIndex] = playerPlaces[currentPlayerIndex] - 12;
        }

        private void PrintQuestionAndRemoveFromList()
        {
            if (GetCurrentCategory() == "Pop")
            {
                Console.WriteLine(popQuestions.First());
                popQuestions.RemoveFirst();
            }
            if (GetCurrentCategory() == "Science")
            {
                Console.WriteLine(scienceQuestions.First());
                scienceQuestions.RemoveFirst();
            }
            if (GetCurrentCategory() == "Sports")
            {
                Console.WriteLine(sportsQuestions.First());
                sportsQuestions.RemoveFirst();
            }
            if (GetCurrentCategory() == "Rock")
            {
                Console.WriteLine(rockQuestions.First());
                rockQuestions.RemoveFirst();
            }


        }


        private String GetCurrentCategory()
        {
            if (playerPlaces[currentPlayerIndex] == 0) return "Pop";
            if (playerPlaces[currentPlayerIndex] == 4) return "Pop";
            if (playerPlaces[currentPlayerIndex] == 8) return "Pop";
            if (playerPlaces[currentPlayerIndex] == 1) return "Science";
            if (playerPlaces[currentPlayerIndex] == 5) return "Science";
            if (playerPlaces[currentPlayerIndex] == 9) return "Science";
            if (playerPlaces[currentPlayerIndex] == 2) return "Sports";
            if (playerPlaces[currentPlayerIndex] == 6) return "Sports";
            if (playerPlaces[currentPlayerIndex] == 10) return "Sports";
            return "Rock";
        }

        public bool wasCorrectlyAnswered()
        {
            if (inPenaltyBox[currentPlayerIndex])
            {
                if (isGettingOutOfPenaltyBox)
                {
                    Console.WriteLine("Answer was correct!!!!");
                    playerPurses[currentPlayerIndex]++;
                    Console.WriteLine(players[currentPlayerIndex].Name
                            + " now has "
                            + playerPurses[currentPlayerIndex]
                            + " Gold Coins.");

                    bool winner = GetCurrentPlayerWinStatus();
                    NextPlayer();

                    return winner;
                }
                else
                {
                    NextPlayer();
                    return true;
                }



            }
            else
            {

                Console.WriteLine("Answer was corrent!!!!");
                playerPurses[currentPlayerIndex]++;
                Console.WriteLine(players[currentPlayerIndex].Name
                        + " now has "
                        + playerPurses[currentPlayerIndex]
                        + " Gold Coins.");

                bool winner = GetCurrentPlayerWinStatus();
                NextPlayer();

                return winner;
            }
        }

        public bool GiveCurrentPlayerWrongAnswer()
        {
            Console.WriteLine("Question was incorrectly answered");
            Console.WriteLine(players[currentPlayerIndex].Name + " was sent to the penalty box");
            inPenaltyBox[currentPlayerIndex] = true;

            NextPlayer();
            return true;
        }


        private bool GetCurrentPlayerWinStatus()
        {
            return !(playerPurses[currentPlayerIndex] == 6);
        }

        private void NextPlayer()
        {
            currentPlayerIndex++;
            if (currentPlayerIndex == PlayerCount) currentPlayerIndex = 0;
        }
    }
}