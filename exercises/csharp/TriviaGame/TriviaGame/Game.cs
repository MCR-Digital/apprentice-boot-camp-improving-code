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

        public void MakeMoveBasedOnRoll(int rollValue)
        {
            Console.WriteLine(players[currentPlayer] + " is the current player");
            Console.WriteLine("They have rolled a " + rollValue);

            if (PlayerPenaltyBoxStatus[currentPlayer])
            {
                if (CheckIfPlayerGetsOutOfPenalty(rollValue))
                {
                    isGettingOutOfPenaltyBox = true;
                    Console.WriteLine(players[currentPlayer] + " is getting out of the penalty box");

					CalculateNewPositionOnBoard(rollValue);

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
				CalculateNewPositionOnBoard(rollValue);				
                AskQuestion();
            }

        }

        private void AskQuestion()
        {
			var currentCategory = ReturnCurrentCategory();

			Console.WriteLine("The category is " + currentCategory);
			if (currentCategory == "Pop")
            {
                Console.WriteLine(popQuestions.First());
                popQuestions.RemoveFirst();
            }
            if (currentCategory == "Science")
            {
                Console.WriteLine(scienceQuestions.First());
                scienceQuestions.RemoveFirst();
            }
            if (currentCategory == "Sports")
            {
                Console.WriteLine(sportsQuestions.First());
                sportsQuestions.RemoveFirst();
            }
            if (currentCategory == "Rock")
            {
                Console.WriteLine(rockQuestions.First());
                rockQuestions.RemoveFirst();
            }
        }

        public bool WasCorrectlyAnswered()
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

                Console.WriteLine("Answer was correct!!!!");
                PlayerPurses[currentPlayer]++;
                Console.WriteLine(players[currentPlayer]
                        + " now has "
                        + PlayerPurses[currentPlayer]
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
            PlayerPenaltyBoxStatus[currentPlayer] = true;

            currentPlayer++;
            if (currentPlayer == players.Count) currentPlayer = 0;
            return true;
        }

		#region "private helper methods"
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

		private bool DidPlayerWin()
		{
			return !(PlayerPurses[currentPlayer] == MaxPlayers);
		}

		private string ReturnCurrentCategory()
		{
			var categories = new Dictionary<int, string>
			{
				{ 0, "Pop" },
				{ 1, "Science" },
				{ 2, "Sports" },
				{ 3, "Rock" },
				{ 4, "Pop" },
				{ 5, "Science" },
				{ 6, "Sports" },
				{ 7, "Rock" },
				{ 8, "Pop" },
				{ 9, "Science" },
				{ 10, "Sports" },
				{ 11, "Rock" },
			};

			return categories[PlayerPositionOnBoard[currentPlayer]];
		}
		#endregion
	}
}