using System;

namespace TriviaGame
{
    public class Board
    {
        private const int BOARD_SIZE = 12;
        private readonly QuestionSet _questionSet;

        public Board()
        {
            _questionSet = new QuestionSet();
        }
        
        public void MovePlayer(Player player, int places)
        {
            player.Place += places;

            if (player.Place >= BOARD_SIZE)
            {
                player.Place -= BOARD_SIZE;
            }
        }

        private static Category GetCategoryForPlayer(Player player)
        {
            int numberOfCategories = Enum.GetValues(typeof(Category)).Length;

            int categoryNumber = player.Place % numberOfCategories;

            return (Category)categoryNumber;
        }

        public Question GetQuestionForPlayer(Player player)
        {
            var category = GetCategoryForPlayer(player);

            return _questionSet.GetDeck(category).GetNext();
        }
    }
}
