using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace TriviaGame
{
    public class Game
    {
        private const int MIN_PLAYER_COUNT = 2;
        private const int MAX_QUESTIONS = 50;

        List<Player> players = new List<Player>();

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

        public string CreateRockQuestion(int index)
        {
            return "Rock Question " + index;
        }

        public bool IsPlayable()
        {
            return (PlayerCount >= MIN_PLAYER_COUNT);
        }

        public bool AddPlayer(Player player)
        {
            player.Place = 0;
            player.Coins = 0;
            player.IsInPenaltyBox = false;
            players.Add(player);

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

        public Player CurrentPlayer
        {
            get
            {
                return players[currentPlayerIndex];
            }
        }

        public void RollDice(int rollNumber)
        {
            Console.WriteLine(CurrentPlayer.Name + " is the current player");
            Console.WriteLine("They have rolled a " + rollNumber);

            if (CurrentPlayer.IsInPenaltyBox)
            {
                if (rollNumber % 2 != 0)
                {
                    isGettingOutOfPenaltyBox = true;

                    Console.WriteLine(CurrentPlayer.Name + " is getting out of the penalty box");
                    MoveCurrentPlayer(rollNumber);

                    Console.WriteLine(CurrentPlayer.Name
                            + "'s new location is "
                            + CurrentPlayer.Place);
                    Console.WriteLine("The category is " + GetCurrentCategory());
                    PrintQuestionAndRemoveFromList();
                }
                else
                {
                    Console.WriteLine(CurrentPlayer.Name + " is not getting out of the penalty box");
                    isGettingOutOfPenaltyBox = false;
                }

            }
            else
            {
                MoveCurrentPlayer(rollNumber);

                Console.WriteLine(CurrentPlayer.Name
                        + "'s new location is "
                        + CurrentPlayer.Place);
                Console.WriteLine("The category is " + GetCurrentCategory());
                PrintQuestionAndRemoveFromList();
            }

        }

        private void MoveCurrentPlayer(int places)
        {
            CurrentPlayer.Place += places;
            if (CurrentPlayer.Place > 11) CurrentPlayer.Place -= 12;
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


        private string GetCurrentCategory()
        {
            if (CurrentPlayer.Place == 0) return "Pop";
            if (CurrentPlayer.Place == 4) return "Pop";
            if (CurrentPlayer.Place == 8) return "Pop";
            if (CurrentPlayer.Place == 1) return "Science";
            if (CurrentPlayer.Place == 5) return "Science";
            if (CurrentPlayer.Place == 9) return "Science";
            if (CurrentPlayer.Place == 2) return "Sports";
            if (CurrentPlayer.Place == 6) return "Sports";
            if (CurrentPlayer.Place == 10) return "Sports";
            return "Rock";
        }

        public bool wasCorrectlyAnswered()
        {
            if (CurrentPlayer.IsInPenaltyBox)
            {
                if (isGettingOutOfPenaltyBox)
                {
                    Console.WriteLine("Answer was correct!!!!");
                    CurrentPlayer.Coins++;
                    Console.WriteLine(CurrentPlayer.Name
                            + " now has "
                            + CurrentPlayer.Coins
                            + " Gold Coins.");

                    bool winner = GetCurrentPlayerWinStatus();
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

                Console.WriteLine("Answer was corrent!!!!");
                CurrentPlayer.Coins++;
                Console.WriteLine(CurrentPlayer.Name
                        + " now has "
                        + CurrentPlayer.Coins
                        + " Gold Coins.");

                bool winner = GetCurrentPlayerWinStatus();
                MoveToNextPlayer();

                return winner;
            }
        }

        public bool GiveCurrentPlayerWrongAnswer()
        {
            Console.WriteLine("Question was incorrectly answered");
            Console.WriteLine(CurrentPlayer.Name + " was sent to the penalty box");
            CurrentPlayer.IsInPenaltyBox = true;

            MoveToNextPlayer();
            return true;
        }


        private bool GetCurrentPlayerWinStatus()
        {
            return !(CurrentPlayer.Coins == 6);
        }

        private void MoveToNextPlayer()
        {
            currentPlayerIndex++;
            if (currentPlayerIndex == PlayerCount) currentPlayerIndex = 0;
        }
    }
}