using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace TriviaGame
{
    public class Game
    {
        List<string> players = new List<string>();

        int[] places = new int[6];
        int[] purses = new int[6];

        bool[] inPenaltyBox = new bool[6];

        LinkedList<string> popQuestions = new LinkedList<string>();
        LinkedList<string> scienceQuestions = new LinkedList<string>();
        LinkedList<string> sportsQuestions = new LinkedList<string>();
        LinkedList<string> rockQuestions = new LinkedList<string>();

        int currentPlayerIndex = 0;
        bool isGettingOutOfPenaltyBox;

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

        public String CreateRockQuestion(int index)
        {
            return "Rock Question " + index;
        }

        public bool IsPlayable()
        {
            return (GetPlayerCount() >= 2);
        }

        public bool AddPlayer(String playerName)
        {


            players.Add(playerName);
            places[GetPlayerCount()] = 0;
            purses[GetPlayerCount()] = 0;
            inPenaltyBox[GetPlayerCount()] = false;

            Console.WriteLine(playerName + " was added");
            Console.WriteLine("They are player number " + players.Count);
            return true;
        }

        public int GetPlayerCount()
        {
            return players.Count;
        }

        public void RollDice(int rollNumber)
        {
            Console.WriteLine(players[currentPlayerIndex] + " is the current player");
            Console.WriteLine("They have rolled a " + rollNumber);

            if (inPenaltyBox[currentPlayerIndex])
            {
                if (rollNumber % 2 != 0)
                {
                    isGettingOutOfPenaltyBox = true;

                    Console.WriteLine(players[currentPlayerIndex] + " is getting out of the penalty box");
                    places[currentPlayerIndex] = places[currentPlayerIndex] + rollNumber;
                    if (places[currentPlayerIndex] > 11) places[currentPlayerIndex] = places[currentPlayerIndex] - 12;

                    Console.WriteLine(players[currentPlayerIndex]
                            + "'s new location is "
                            + places[currentPlayerIndex]);
                    Console.WriteLine("The category is " + GetCurrentCategory());
                    askQuestion();
                }
                else
                {
                    Console.WriteLine(players[currentPlayerIndex] + " is not getting out of the penalty box");
                    isGettingOutOfPenaltyBox = false;
                }

            }
            else
            {

                places[currentPlayerIndex] = places[currentPlayerIndex] + rollNumber;
                if (places[currentPlayerIndex] > 11) places[currentPlayerIndex] = places[currentPlayerIndex] - 12;

                Console.WriteLine(players[currentPlayerIndex]
                        + "'s new location is "
                        + places[currentPlayerIndex]);
                Console.WriteLine("The category is " + GetCurrentCategory());
                askQuestion();
            }

        }

        private void askQuestion()
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
            if (places[currentPlayerIndex] == 0) return "Pop";
            if (places[currentPlayerIndex] == 4) return "Pop";
            if (places[currentPlayerIndex] == 8) return "Pop";
            if (places[currentPlayerIndex] == 1) return "Science";
            if (places[currentPlayerIndex] == 5) return "Science";
            if (places[currentPlayerIndex] == 9) return "Science";
            if (places[currentPlayerIndex] == 2) return "Sports";
            if (places[currentPlayerIndex] == 6) return "Sports";
            if (places[currentPlayerIndex] == 10) return "Sports";
            return "Rock";
        }

        public bool wasCorrectlyAnswered()
        {
            if (inPenaltyBox[currentPlayerIndex])
            {
                if (isGettingOutOfPenaltyBox)
                {
                    Console.WriteLine("Answer was correct!!!!");
                    purses[currentPlayerIndex]++;
                    Console.WriteLine(players[currentPlayerIndex]
                            + " now has "
                            + purses[currentPlayerIndex]
                            + " Gold Coins.");

                    bool winner = GetCurrentPlayerWinStatus();
                    currentPlayerIndex++;
                    if (currentPlayerIndex == players.Count) currentPlayerIndex = 0;

                    return winner;
                }
                else
                {
                    currentPlayerIndex++;
                    if (currentPlayerIndex == players.Count) currentPlayerIndex = 0;
                    return true;
                }



            }
            else
            {

                Console.WriteLine("Answer was corrent!!!!");
                purses[currentPlayerIndex]++;
                Console.WriteLine(players[currentPlayerIndex]
                        + " now has "
                        + purses[currentPlayerIndex]
                        + " Gold Coins.");

                bool winner = GetCurrentPlayerWinStatus();
                currentPlayerIndex++;
                if (currentPlayerIndex == players.Count) currentPlayerIndex = 0;

                return winner;
            }
        }

        public bool GiveCurrentPlayerWrongAnswer()
        {
            Console.WriteLine("Question was incorrectly answered");
            Console.WriteLine(players[currentPlayerIndex] + " was sent to the penalty box");
            inPenaltyBox[currentPlayerIndex] = true;

            currentPlayerIndex++;
            if (currentPlayerIndex == players.Count) currentPlayerIndex = 0;
            return true;
        }


        private bool GetCurrentPlayerWinStatus()
        {
            return !(purses[currentPlayerIndex] == 6);
        }
    }

}