using System;

namespace TriviaGame
{
    public static class GameWriter
    {
        public static void WritePlayerAdded(Player player, int playerCount)
        {
            Console.WriteLine($"{player.Name} was added");
            Console.WriteLine($"They are player number {playerCount}");
        }

        public static void WritePlayerNewLocation(Player player)
        {
            Console.WriteLine($"{player.Name}'s new location is {player.Place}");
        }

        public static void WriteCategory(Category category)
        {
            Console.WriteLine($"The category is {category}");
        }

        public static void WriteNewCoinAmount(Player player)
        {
            Console.WriteLine($"{player.Name} now has {player.Coins} Gold Coins.");
        }

        public static void WriteCurrentPlayerRoll(Player player, int rollNumber)
        {
            Console.WriteLine($"{player.Name} is the current player");
            Console.WriteLine($"They have rolled a {rollNumber}");
        }

        public static void WriteAnswerWasCorrect()
        {
            Console.WriteLine("Answer was correct!!!!");
        }

        public static void WriteAnswerWasIncorrect(Player player)
        {
            Console.WriteLine("Question was incorrectly answered");
            Console.WriteLine($"{player.Name} was sent to the penalty box");
        }

        public static void WritePlayerLeavingPenaltyBox(Player player)
        {
            Console.WriteLine($"{player.Name} is getting out of the penalty box");
        }

        public static void WritePlayerNotLeavingPenaltyBox(Player player)
        {
            Console.WriteLine($"{player.Name} is not getting out of the penalty box");
        }

        public static void WriteQuestion(Question question)
        {
            Console.WriteLine($"{question.Category} Question {question.Number}");
        }
    }
}
