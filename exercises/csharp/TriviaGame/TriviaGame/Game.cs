using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace TriviaGame
{
    public class Game
    {
        readonly List<string> players = new List<string>();

        readonly int[] places = new int[6];
        readonly int[] coins = new int[6];

        readonly bool[] inPenaltyBox = new bool[6];

        readonly LinkedList<string> popQuestions = new LinkedList<string>();
        readonly LinkedList<string> scienceQuestions = new LinkedList<string>();
        readonly LinkedList<string> sportsQuestions = new LinkedList<string>();
        readonly LinkedList<string> rockQuestions = new LinkedList<string>();

        int currentPlayer = 0;
        bool isGettingOutOfPenaltyBox;

        public Game()
        {
            for (int index = 0; index < 50; index++)
            {
                popQuestions.AddLast($"Pop Question {index}");
                scienceQuestions.AddLast(($"Science Question {index}"));
                sportsQuestions.AddLast(($"Sports Question {index}"));
                rockQuestions.AddLast(CreateRockQuestion(index));
            }
        }

        public string CreateRockQuestion(int index)
        {
            return $"Rock Question {index}";
        }

        public bool IsPlayable() => (HowManyPlayers() >= 2);

        public bool Add(string playerName)
        {
            players.Add(playerName);
            places[HowManyPlayers()] = 0;
            coins[HowManyPlayers()] = 0;
            inPenaltyBox[HowManyPlayers()] = false;

            Console.WriteLine($"{playerName} was added");
            Console.WriteLine($"They are player number {players.Count}");
            return true;
        }

        public int HowManyPlayers() => players.Count;

        public void RollDice(int roll)
        {
            Console.WriteLine($"{players[currentPlayer]} is the current player");
            Console.WriteLine($"They have rolled a {roll}");
                                 
            if (inPenaltyBox[currentPlayer])
            {
                if (roll % 2 != 0)
                {
                    isGettingOutOfPenaltyBox = true;

                    Console.WriteLine($"{players[currentPlayer]} is getting out of the penalty box");
                    places[currentPlayer] = places[currentPlayer] + roll;

                    if (places[currentPlayer] > 11)
                    {
                        places[currentPlayer] = places[currentPlayer] - 12;
                    }

                    Console.WriteLine($"{players[currentPlayer]}'s new location is {places[currentPlayer]}");
                    Console.WriteLine($"The category is {Category()}");
                    AskQuestion();
                }
                else
                {
                    Console.WriteLine($"{players[currentPlayer]} is not getting out of the penalty box");
                    isGettingOutOfPenaltyBox = false;
                }
            }
            else
            {
                places[currentPlayer] = places[currentPlayer] + roll;
                if (places[currentPlayer] > 11) places[currentPlayer] = places[currentPlayer] - 12;

                Console.WriteLine($"{players[currentPlayer]}'s new location is {places[currentPlayer]}");
                Console.WriteLine($"The category is {Category()}");
                AskQuestion();
            }

        }

        private void AskQuestion()
        {
            if (Category() == "Pop")
            {
                Console.WriteLine(popQuestions.First());
                popQuestions.RemoveFirst();
            }
            if (Category() == "Science")
            {
                Console.WriteLine(scienceQuestions.First());
                scienceQuestions.RemoveFirst();
            }
            if (Category() == "Sports")
            {
                Console.WriteLine(sportsQuestions.First());
                sportsQuestions.RemoveFirst();
            }
            if (Category() == "Rock")
            {
                Console.WriteLine(rockQuestions.First());
                rockQuestions.RemoveFirst();
            }
        }


        private string Category()
        {
            if (places[currentPlayer] == 0 || places[currentPlayer] == 4 || places[currentPlayer] == 8) return "Pop";
            if (places[currentPlayer] == 1 || places[currentPlayer] == 5 || places[currentPlayer] == 9) return "Science";
            if (places[currentPlayer] == 2 || places[currentPlayer] == 6 || places[currentPlayer] == 10) return "Sports";
            else return "Rock";
        }

        public bool CorrectAnswer()
        {
            if (inPenaltyBox[currentPlayer])
            {
                if (isGettingOutOfPenaltyBox)
                {
                    Console.WriteLine("Answer was correct!!!!");
                    coins[currentPlayer]++;
                    Console.WriteLine($"{players[currentPlayer]} now has {coins[currentPlayer]} Gold Coins.");

                    bool winner = DidPlayerWin();
                    currentPlayer++;
                    if (currentPlayer == players.Count) currentPlayer = 0;

                    return winner;
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
                coins[currentPlayer]++;
                Console.WriteLine($"{players[currentPlayer]} now has {coins[currentPlayer]} Gold Coins.");

                bool winner = DidPlayerWin();
                currentPlayer++;
                if (currentPlayer == players.Count) currentPlayer = 0;

                return winner;
            }
        }

        public bool WrongAnswer()
        {
            Console.WriteLine("Question was incorrectly answered");
            Console.WriteLine($"{players[currentPlayer]} was sent to the penalty box");
            inPenaltyBox[currentPlayer] = true;

            currentPlayer++;
            if (currentPlayer == players.Count) currentPlayer = 0;
            return true;
        }

        private bool DidPlayerWin() => (coins[currentPlayer] != 6);
    }
}