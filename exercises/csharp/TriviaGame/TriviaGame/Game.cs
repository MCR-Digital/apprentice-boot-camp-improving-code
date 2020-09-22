using System;
using System.Collections.Generic;
using System.Linq;

namespace TriviaGame
{
    public class Game
    {
        private const int CardQuestionNumber = 50;
        List<string> players = new List<string>();

        int[] places = new int[6];
        int[] playerGoldCoins = new int[6];

        bool[] inPenaltyBox = new bool[6];

        LinkedList<string> popQuestions = new LinkedList<string>();
        LinkedList<string> scienceQuestions = new LinkedList<string>();
        LinkedList<string> sportsQuestions = new LinkedList<string>();
        LinkedList<string> rockQuestions = new LinkedList<string>();

        int _currentPlayer = 0;
        bool _leavingPenaltyBox;

        public Game()
        {
            for (int i = 0; i < CardQuestionNumber; i++)
            {
                popQuestions.AddLast("Pop Question " + i);
                scienceQuestions.AddLast(("Science Question " + i));
                sportsQuestions.AddLast(("Sports Question " + i));
                rockQuestions.AddLast(("Rock Question " + i));
            }
        }

        //public string CreateRockQuestion(int index)
        //{
        //    return "Rock Question " + index;
        //}

        // Unused function - commented out

        //public bool IsPlayable()
        //{
        //    return (HowManyPlayers() >= 2);
        //}

        public bool AddPlayer(string playerName)
        {


            players.Add(playerName);
            places[TotalNumberOfPlayers()] = 0;
            playerGoldCoins[TotalNumberOfPlayers()] = 0;
            inPenaltyBox[TotalNumberOfPlayers()] = false;

            Console.WriteLine(playerName + " was added");
            Console.WriteLine("They are player number " + players.Count);
            return true;
        }

        public int TotalNumberOfPlayers()
        {
            return players.Count;
        }

        public void NextPlayerRolls(int roll)
        {
            Console.WriteLine(players[_currentPlayer] + " is the current player");
            Console.WriteLine("They have rolled a " + roll);

            void LeftPenaltyBox()
            {
                _leavingPenaltyBox = true;

                Console.WriteLine(players[_currentPlayer] + " is getting out of the penalty box");
                places[_currentPlayer] = places[_currentPlayer] + roll;
                if (places[_currentPlayer] > 11) places[_currentPlayer] = places[_currentPlayer] - 12;
            }

            void StaysInPenaltyBox()
            {
                Console.WriteLine(players[_currentPlayer] + " is not getting out of the penalty box");
                _leavingPenaltyBox = false;
            }

            if (inPenaltyBox[_currentPlayer])
            {
                if (roll % 2 != 0)
                {
                    LeftPenaltyBox();
                    NewPlayerLocation();
                    StateCurrentCategory();
                    AskQuestion();
                }
                else
                {
                    StaysInPenaltyBox();
                }

            }
            else
            {

                places[_currentPlayer] = places[_currentPlayer] + roll;
                if (places[_currentPlayer] > 11) places[_currentPlayer] = places[_currentPlayer] - 12;

                NewPlayerLocation();
                StateCurrentCategory();
                AskQuestion();
            }

        }

        private void StateCurrentCategory()
        {
            Console.WriteLine("The category is " + CurrentCategory());
        }

        private void NewPlayerLocation()
        {
            Console.WriteLine(players[_currentPlayer]
                              + "'s new location is "
                              + places[_currentPlayer]);
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
            return places[_currentPlayer] switch
            {
                0 => "Pop",
                4 => "Pop",
                8 => "Pop",
                1 => "Science",
                5 => "Science",
                9 => "Science",
                2 => "Sports",
                6 => "Sports",
                10 => "Sports",
                _ => "Rock"
            };
        }

        public bool CorrectAnswer()
        {
            if (inPenaltyBox[_currentPlayer])
            {
                if (_leavingPenaltyBox)
                {
                    Console.WriteLine("Answer was correct!!!!");
                    playerGoldCoins[_currentPlayer]++;
                    Console.WriteLine(players[_currentPlayer]
                            + " now has "
                            + playerGoldCoins[_currentPlayer]
                            + " Gold Coins.");

                    bool winner = PlayerWon();
                    _currentPlayer++;
                    if (_currentPlayer == players.Count) _currentPlayer = 0;

                    return winner;
                }
                else
                {
                    _currentPlayer++;
                    if (_currentPlayer == players.Count) _currentPlayer = 0;
                    return true;
                }



            }
            else
            {

                Console.WriteLine("Answer was corrent!!!!");
                playerGoldCoins[_currentPlayer]++;
                Console.WriteLine(players[_currentPlayer]
                        + " now has "
                        + playerGoldCoins[_currentPlayer]
                        + " Gold Coins.");

                bool winner = PlayerWon();
                _currentPlayer++;
                if (_currentPlayer == players.Count) _currentPlayer = 0;

                return winner;
            }
        }

        public bool IncorrectAnswer()
        {
            SendToPenaltyBox();

            _currentPlayer++;
            if (_currentPlayer == players.Count) _currentPlayer = 0;
            return true;
        }

        private void SendToPenaltyBox()
        {
            Console.WriteLine("Question was incorrectly answered");
            Console.WriteLine(players[_currentPlayer] + " was sent to the penalty box");
            inPenaltyBox[_currentPlayer] = true;
        }


        private bool PlayerWon()
        {
            return playerGoldCoins[_currentPlayer] != 6;
        }
    }

}