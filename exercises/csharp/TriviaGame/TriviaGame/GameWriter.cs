using System;

namespace TriviaGame
{
    public static class GameWriter
    {
        public static void WritePlayerAdded(string playerName, int playerCount)
        {
            Console.WriteLine(playerName + " was added");
            Console.WriteLine("They are player number " + playerCount);
        }

        public static void WritePlayerNewLocation(string playerName, int newLocation)
        {
            Console.WriteLine(playerName + "'s new location is " + newLocation);
        }

        public static void WriteCategory(Category category)
        {
            Console.WriteLine("The category is " + category);
        }

        public static void WriteNewCoinAmount(string playerName, int coins)
        {
            Console.WriteLine(playerName + " now has " + coins + " Gold Coins.");
        }

        public static void WriteCurrentPlayerRoll(string playerName, int rollNumber)
        {
            Console.WriteLine(playerName + " is the current player");
            Console.WriteLine("They have rolled a " + rollNumber);
        }

        public static void WriteAnswerWasCorrect()
        {
            Console.WriteLine("Answer was correct!!!!");
        }

        public static void WriteAnswerWasIncorrect(string playerName)
        {
            Console.WriteLine("Question was incorrectly answered");
            Console.WriteLine(playerName + " was sent to the penalty box");
        }

        public static void WritePlayerLeavingPenaltyBox(string playerName)
        {
            Console.WriteLine(playerName + " is getting out of the penalty box");
        }

        public static void WritePlayerNotLeavingPenaltyBox(string playerName)
        {
            Console.WriteLine(playerName + " is not getting out of the penalty box");
        }

        public static void WriteQuestion(string question)
        {
            Console.WriteLine(question);
        }
    }
}
