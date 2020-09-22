using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace TriviaGame
{
    public class Trivia_Game
    {
        List<string> listOfAllPlayers = new List<string>();

        int[] playersPosition = new int[6];
        int[] playersPurses = new int[6];
        int[] popArray = { 0, 4, 8 };
        int[] scienceArray = { 1, 5, 9 };
        int[] SportsArray = { 2, 6, 10 };

        bool[] inPenaltyBox = new bool[6];

        LinkedList<string> popQuestions = new LinkedList<string>();
        LinkedList<string> scienceQuestions = new LinkedList<string>();
        LinkedList<string> sportsQuestions = new LinkedList<string>();
        LinkedList<string> rockQuestions = new LinkedList<string>();

        int currentPlayer;
        bool isGettingOutOfPenaltyBox;

        public Trivia_Game()
        {
            const int maxQuestionNumber = 50;
            for (int questionNumber = 0; questionNumber < maxQuestionNumber; questionNumber++)
            {
                popQuestions.AddLast("Pop Question " + questionNumber);
                scienceQuestions.AddLast(("Science Question " + questionNumber));
                sportsQuestions.AddLast(("Sports Question " + questionNumber));
                rockQuestions.AddLast(CreateRockQuestion(questionNumber));
            }
        }

        public string CreateRockQuestion(int index)
        {
            return "Rock Question " + index;
        }

        public bool IsValidNumberOfPlayers()
        {
            return (HowManyPlayersInCurrentGame() >= 2);
        }

        public bool AddNewPlayer(string playerName)
        {
            listOfAllPlayers.Add(playerName);
            playersPosition[HowManyPlayersInCurrentGame()] = 0;
            playersPurses[HowManyPlayersInCurrentGame()] = 0;
            inPenaltyBox[HowManyPlayersInCurrentGame()] = false;

            Console.WriteLine(playerName + " was added");
            Console.WriteLine("They are player number " + listOfAllPlayers.Count);
            return true;
        }

        public int HowManyPlayersInCurrentGame()
        {
            return listOfAllPlayers.Count;
        }

        public void RollDice(int DiceRollResult)
        {
            Console.WriteLine(listOfAllPlayers[currentPlayer] + " is the current player");
            Console.WriteLine("They have rolled a " + DiceRollResult);

            if (inPenaltyBox[currentPlayer])
            {
                if (DiceRollResult % 2 != 0)
                {
                    isGettingOutOfPenaltyBox = true;

                    Console.WriteLine(listOfAllPlayers[currentPlayer] + " is getting out of the penalty box");
                    MovePlayerToNewPosition(DiceRollResult);
                }
                else
                {
                    Console.WriteLine(listOfAllPlayers[currentPlayer] + " is not getting out of the penalty box");
                    isGettingOutOfPenaltyBox = false;
                }

            }
            else
            {
                MovePlayerToNewPosition(DiceRollResult);
            }

        }

        private void MovePlayerToNewPosition(int DiceRollResult)
        {
            playersPosition[currentPlayer] = playersPosition[currentPlayer] + DiceRollResult;
            if (playersPosition[currentPlayer] > 11) playersPosition[currentPlayer] = playersPosition[currentPlayer] - 12;

            Console.WriteLine(listOfAllPlayers[currentPlayer]
                    + "'s new location is "
                    + playersPosition[currentPlayer]);
            Console.WriteLine("The category is " + CurrentCategory());
            AskQuestion();
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
            if (popArray.Contains(playersPosition[currentPlayer])) return "Pop";
            if (scienceArray.Contains(playersPosition[currentPlayer])) return "Science";
            if (SportsArray.Contains(playersPosition[currentPlayer])) return "Sports";
            return "Rock";
        }

        public bool WasCorrectlyAnswered()
        {
            if (inPenaltyBox[currentPlayer])
            {
                if (isGettingOutOfPenaltyBox)
                {
                    Console.WriteLine("Answer was correct!!!!");
                    playersPurses[currentPlayer]++;
                    Console.WriteLine(listOfAllPlayers[currentPlayer]
                            + " now has "
                            + playersPurses[currentPlayer]
                            + " Gold Coins.");

                    bool winner = HasPlayerWon();
                    currentPlayer++;
                    if (currentPlayer == listOfAllPlayers.Count) currentPlayer = 0;

                    return winner;
                }
                else
                {
                    currentPlayer++;
                    if (currentPlayer == listOfAllPlayers.Count) currentPlayer = 0;
                    return true;
                }
            }
            else
            {
                Console.WriteLine("Answer was corrent!!!!");
                playersPurses[currentPlayer]++;
                Console.WriteLine(listOfAllPlayers[currentPlayer]
                        + " now has "
                        + playersPurses[currentPlayer]
                        + " Gold Coins.");

                bool winner = HasPlayerWon();
                currentPlayer++;
                if (currentPlayer == listOfAllPlayers.Count) currentPlayer = 0;

                return winner;
            }
        }

        public bool IsWrongAnswer()
        {
            Console.WriteLine("Question was incorrectly answered");
            Console.WriteLine(listOfAllPlayers[currentPlayer] + " was sent to the penalty box");
            inPenaltyBox[currentPlayer] = true;

            currentPlayer++;
            if (currentPlayer == listOfAllPlayers.Count) currentPlayer = 0;
            return true;
        }

        private bool HasPlayerWon()
        {
            const int WinningTotal = 6;
            return !(playersPurses[currentPlayer] == WinningTotal);
        }
    }

}