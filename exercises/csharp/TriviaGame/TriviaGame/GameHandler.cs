using System;

namespace TriviaGame
{
    public static class GameHandler
    {

        private const int TotalCategories = 4;
        private const int TotalSpaces = 12;

        public static int RollDice(Player player, Random random)
        {
            var roll = random.Next(5) + 1;
            
            if (player.IsInPenaltyBox)
            {
                player.SkipTurn = roll % 2 == 0;
            }

            return roll;
        }

        public static int MovePlayer(Player player, int roll)
        {
            var newSpace = (player.CurrentSpace + roll) % TotalSpaces;
            return player.CurrentSpace = player.SkipTurn ? player.CurrentSpace : newSpace;
        }

        public static Category GetCategoryAtSpace(int space)
        {
            var id = space % TotalCategories;

            switch (id)
            {
                case 0:
                    return Category.Pop;
                case 1:
                    return Category.Science;
                case 2:
                    return Category.Sports;
                default:
                    return Category.Rock;
            }
        }

        public static string DrawQuestion(bool skipTurn, Deck deck, Category cat)
        {
            return skipTurn ? "" : deck.questionCategories[cat].Dequeue();
        }
    }
}
