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

        List<string> playerNames = new List<string>();

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

        public bool AddPlayer(String playerName)
        {


            playerNames.Add(playerName);
            playerPlaces[PlayerCount] = 0;
            playerPurses[PlayerCount] = 0;
            inPenaltyBox[PlayerCount] = false;

            Console.WriteLine(playerName + " was added");
            Console.WriteLine("They are player number " + playerNames.Count);
            return true;
        }

        public int PlayerCount
        {
            get
            {
                return playerNames.Count;
            }
        }

        public void RollDice(int rollNumber)
        {
            Console.WriteLine(playerNames[currentPlayerIndex] + " is the current player");
            Console.WriteLine("They have rolled a " + rollNumber);

            if (inPenaltyBox[currentPlayerIndex])
            {
                if (rollNumber % 2 != 0)
                {
                    isGettingOutOfPenaltyBox = true;

                    Console.WriteLine(playerNames[currentPlayerIndex] + " is getting out of the penalty box");
                    playerPlaces[currentPlayerIndex] = playerPlaces[currentPlayerIndex] + rollNumber;
                    if (playerPlaces[currentPlayerIndex] > 11) playerPlaces[currentPlayerIndex] = playerPlaces[currentPlayerIndex] - 12;

                    Console.WriteLine(playerNames[currentPlayerIndex]
                            + "'s new location is "
                            + playerPlaces[currentPlayerIndex]);
                    Console.WriteLine("The category is " + GetCurrentCategory());
                    PrintQuestionAndRemoveFromList();
                }
                else
                {
                    Console.WriteLine(playerNames[currentPlayerIndex] + " is not getting out of the penalty box");
                    isGettingOutOfPenaltyBox = false;
                }

            }
            else
            {

                playerPlaces[currentPlayerIndex] = playerPlaces[currentPlayerIndex] + rollNumber;
                if (playerPlaces[currentPlayerIndex] > 11) playerPlaces[currentPlayerIndex] = playerPlaces[currentPlayerIndex] - 12;

                Console.WriteLine(playerNames[currentPlayerIndex]
                        + "'s new location is "
                        + playerPlaces[currentPlayerIndex]);
                Console.WriteLine("The category is " + GetCurrentCategory());
                PrintQuestionAndRemoveFromList();
            }

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
                    Console.WriteLine(playerNames[currentPlayerIndex]
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
                Console.WriteLine(playerNames[currentPlayerIndex]
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
            Console.WriteLine(playerNames[currentPlayerIndex] + " was sent to the penalty box");
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