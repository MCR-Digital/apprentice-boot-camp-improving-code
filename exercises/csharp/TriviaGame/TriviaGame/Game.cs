using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace TriviaGame
{
    public class Game
    {
		private const int MaxPlayers = 6;
        private List<string> players = new List<string>();

        private readonly int[] PlayerPositionOnBoard = new int[MaxPlayers];
        private readonly int[] PlayerPurses = new int[MaxPlayers];
        private readonly bool[] PlayerPenaltyBoxStatus = new bool[MaxPlayers];

        private LinkedList<string> popQuestions = new LinkedList<string>();
        private LinkedList<string> scienceQuestions = new LinkedList<string>();
        private LinkedList<string> sportsQuestions = new LinkedList<string>();
        private LinkedList<string> rockQuestions = new LinkedList<string>();

        private int currentPlayer = 0;
		private int numberOfPositionsOnBoard = 12;
        private bool isGettingOutOfPenaltyBox;

        public Game()
        {
            for (int i = 0; i < numberOfPositionsOnBoard - 1; i++)
            {
                popQuestions.AddLast("Pop Question " + i);
                scienceQuestions.AddLast(("Science Question " + i));
                sportsQuestions.AddLast(("Sports Question " + i));
                rockQuestions.AddLast("Rock Question " + i);
            }
        }
		
        public bool DoesGameHaveMinimumPlayersRequired()
        {
            return (NumberOfPlayers() >= 2);
        }

        public bool GeneratePlayerList(string[] playerNames)
        {
			foreach(var playerName in playerNames)
			{
				players.Add(playerName);
				PlayerPositionOnBoard[NumberOfPlayers()] = 0;
				PlayerPurses[NumberOfPlayers()] = 0;
				PlayerPenaltyBoxStatus[NumberOfPlayers()] = false;

				Console.WriteLine(playerName + " was added");
				Console.WriteLine("They are player number " + players.Count);
			}

			return DoesGameHaveMinimumPlayersRequired();
        }
		
        public int NumberOfPlayers()
        {
            return players.Count;
        }

		private void CalculateNewPositionOnBoard(int spacesToMove) 
		{
			PlayerPositionOnBoard[currentPlayer] = PlayerPositionOnBoard[currentPlayer] + spacesToMove;
			if (PlayerPositionOnBoard[currentPlayer] > numberOfPositionsOnBoard - 1)
			{
				PlayerPositionOnBoard[currentPlayer] = PlayerPositionOnBoard[currentPlayer] - numberOfPositionsOnBoard;
			}


			Console.WriteLine(players[currentPlayer]
					+ "'s new location is "
					+ PlayerPositionOnBoard[currentPlayer]);
		}

		private bool CheckIfPlayerGetsOutOfPenalty(int rollValue)
		{
			return rollValue % 2 != 0;
		}

        public void MakeMoveBasedOnRoll(int roll)
        {
            Console.WriteLine(players[currentPlayer] + " is the current player");
            Console.WriteLine("They have rolled a " + roll);

            if (PlayerPenaltyBoxStatus[currentPlayer])
            {
                if (CheckIfPlayerGetsOutOfPenalty(roll))
                {
                    isGettingOutOfPenaltyBox = true;

                    Console.WriteLine(players[currentPlayer] + " is getting out of the penalty box");

					CalculateNewPositionOnBoard(roll);

					Console.WriteLine("The category is " + ReturnCurrentCategory());
                    askQuestion();
                }
                else
                {
                    Console.WriteLine(players[currentPlayer] + " is not getting out of the penalty box");
                    isGettingOutOfPenaltyBox = false;
                }

            }
            else
            {
				CalculateNewPositionOnBoard(roll);

				Console.WriteLine("The category is " + ReturnCurrentCategory());
                askQuestion();
            }

        }

        private void askQuestion()
        {
            if (ReturnCurrentCategory() == "Pop")
            {
                Console.WriteLine(popQuestions.First());
                popQuestions.RemoveFirst();
            }
            if (ReturnCurrentCategory() == "Science")
            {
                Console.WriteLine(scienceQuestions.First());
                scienceQuestions.RemoveFirst();
            }
            if (ReturnCurrentCategory() == "Sports")
            {
                Console.WriteLine(sportsQuestions.First());
                sportsQuestions.RemoveFirst();
            }
            if (ReturnCurrentCategory() == "Rock")
            {
                Console.WriteLine(rockQuestions.First());
                rockQuestions.RemoveFirst();
            }
        }

        private string ReturnCurrentCategory()
        {
            if (PlayerPositionOnBoard[currentPlayer] == 0) return "Pop";
            if (PlayerPositionOnBoard[currentPlayer] == 4) return "Pop";
            if (PlayerPositionOnBoard[currentPlayer] == 8) return "Pop";
            if (PlayerPositionOnBoard[currentPlayer] == 1) return "Science";
            if (PlayerPositionOnBoard[currentPlayer] == 5) return "Science";
            if (PlayerPositionOnBoard[currentPlayer] == 9) return "Science";
            if (PlayerPositionOnBoard[currentPlayer] == 2) return "Sports";
            if (PlayerPositionOnBoard[currentPlayer] == 6) return "Sports";
            if (PlayerPositionOnBoard[currentPlayer] == 10) return "Sports";
            return "Rock";
        }

        public bool wasCorrectlyAnswered()
        {
            if (PlayerPenaltyBoxStatus[currentPlayer])
            {
                if (isGettingOutOfPenaltyBox)
                {
                    Console.WriteLine("Answer was correct!!!!");
                    PlayerPurses[currentPlayer]++;
                    Console.WriteLine(players[currentPlayer]
                            + " now has "
                            + PlayerPurses[currentPlayer]
                            + " Gold Coins.");

                    bool winner = didPlayerWin();
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

                Console.WriteLine("Answer was correct!!!!");
                PlayerPurses[currentPlayer]++;
                Console.WriteLine(players[currentPlayer]
                        + " now has "
                        + PlayerPurses[currentPlayer]
                        + " Gold Coins.");

                bool winner = didPlayerWin();
                currentPlayer++;
                if (currentPlayer == players.Count) currentPlayer = 0;

                return winner;
            }
        }

        public bool wrongAnswer()
        {
            Console.WriteLine("Question was incorrectly answered");
            Console.WriteLine(players[currentPlayer] + " was sent to the penalty box");
            PlayerPenaltyBoxStatus[currentPlayer] = true;

            currentPlayer++;
            if (currentPlayer == players.Count) currentPlayer = 0;
            return true;
        }

        private bool didPlayerWin()
        {
            return !(PlayerPurses[currentPlayer] == MaxPlayers);
        }
    }

}