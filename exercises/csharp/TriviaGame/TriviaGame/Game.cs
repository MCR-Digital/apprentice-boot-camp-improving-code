using System;
using System.Collections.Generic;
using System.Linq;

namespace TriviaGame
{
    public class Game
    {
        List<string> players = new List<string>();

        int[] playerPositions = new int[6]; //what is 6, max players?
        int[] playerScores = new int[6];

        bool[] inPenaltyBox = new bool[6];

        LinkedList<string> popQuestions = new LinkedList<string>();
        LinkedList<string> scienceQuestions = new LinkedList<string>();
        LinkedList<string> sportsQuestions = new LinkedList<string>();
        LinkedList<string> rockQuestions = new LinkedList<string>();

        private int currentPlayer;
        bool isGettingOutOfPenaltyBox; //redundant variable - are players ever removed from penalty box?
        
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

        public string CreateRockQuestion(int index) //can be made reusable by passing in string?
        {
            return "Rock Question " + index;
        }

        public bool Add(string playerName)
        {
            players.Add(playerName);
            playerPositions[HowManyPlayers()] = 0;
            playerScores[HowManyPlayers()] = 0;
            inPenaltyBox[HowManyPlayers()] = false;

            Console.WriteLine(playerName + " was added");
            Console.WriteLine("They are player number " + players.Count);
            return true;
        }

        public int HowManyPlayers() //what?
        {
            return players.Count;
        }

        public void Roll(int roll) //does roll param make sense/descriptive enough?
        {
            Console.WriteLine(players[currentPlayer] + " is the current player");
            Console.WriteLine("They have rolled a " + roll);
            if (inPenaltyBox[currentPlayer])
            {
                if (roll % 2 != 0) //pull out to make meaningful, ie isOdd?
                {
                    isGettingOutOfPenaltyBox = true;

                    Console.WriteLine(players[currentPlayer] + " is getting out of the penalty box");
                    SetNewPlayerPosition(roll);
                    
                    Console.WriteLine("The category is " + CurrentCategory());
                    AskQuestion();
                }
                else
                {
                    Console.WriteLine(players[currentPlayer] + " is not getting out of the penalty box");
                    isGettingOutOfPenaltyBox = false;
                }
            }
            else
            {
                SetNewPlayerPosition(roll);
                
                Console.WriteLine("The category is " + CurrentCategory());
                AskQuestion();
            }

        }

        private void SetNewPlayerPosition(int roll)
        {
            playerPositions[currentPlayer] = playerPositions[currentPlayer] + roll; //+=
            if (playerPositions[currentPlayer] > 11) playerPositions[currentPlayer] = playerPositions[currentPlayer] - 12; //-=

            Console.WriteLine(players[currentPlayer]
                              + "'s new location is "
                              + playerPositions[currentPlayer]);
        }

        private void AskQuestion()
        {
            if (CurrentCategory() == "Pop") //could you make this into a single method, passing in the list and string?
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
            if (playerPositions[currentPlayer] == 0) return "Pop";
            if (playerPositions[currentPlayer] == 4) return "Pop";
            if (playerPositions[currentPlayer] == 8) return "Pop";
            if (playerPositions[currentPlayer] == 1) return "Science";
            if (playerPositions[currentPlayer] == 5) return "Science";
            if (playerPositions[currentPlayer] == 9) return "Science";
            if (playerPositions[currentPlayer] == 2) return "Sports";
            if (playerPositions[currentPlayer] == 6) return "Sports";
            if (playerPositions[currentPlayer] == 10) return "Sports";
            return "Rock";
        }

        public bool WasCorrectlyAnswered() //correct and incorrect methods are named differently
        {
            if (inPenaltyBox[currentPlayer]) //should just do else if in penalty box - is this repeated logic?
            {
                if (isGettingOutOfPenaltyBox)
                {
                    IncrementScore();

                    bool winner = DidPlayerWin();
                    MoveToNextPlayer();

                    return winner;
                }
                else
                {
                    MoveToNextPlayer();
                    return true;
                }
            }
            else
            {
                IncrementScore();

                bool winner = DidPlayerWin();
                MoveToNextPlayer();

                return winner;
            }
        }

        private void MoveToNextPlayer()
        {
            currentPlayer++;
            if (currentPlayer == players.Count) currentPlayer = 0;
        }

        private void IncrementScore()
        {
            Console.WriteLine("Answer was correct!!!!");
            playerScores[currentPlayer]++;
            Console.WriteLine(players[currentPlayer]
                              + " now has "
                              + playerScores[currentPlayer]
                              + " Gold Coins.");
        }

        public bool WrongAnswer()
        {
            Console.WriteLine("Question was incorrectly answered");
            Console.WriteLine(players[currentPlayer] + " was sent to the penalty box");
            inPenaltyBox[currentPlayer] = true;

            currentPlayer++;
            if (currentPlayer == players.Count) currentPlayer = 0;
            return true;
        }
        
        private bool DidPlayerWin()
        {
            return !(playerScores[currentPlayer] == 6);
        }
    }

}