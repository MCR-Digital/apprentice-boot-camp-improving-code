using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace TriviaGame
{
    public class Board
    {
        private const int BOARD_SIZE = 12;
        private readonly Dictionary<Category, QuestionDeck> _questions;

        public Board()
        {
            _questions = new Dictionary<Category, QuestionDeck>()
            {
                { Category.Pop, new QuestionDeck(Category.Pop) },
                { Category.Science, new QuestionDeck(Category.Science) },
                { Category.Sports, new QuestionDeck(Category.Sports) },
                { Category.Rock, new QuestionDeck(Category.Rock) },
            };
        }
        
        public void MovePlayer(Player player, int places)
        {
            player.Place += places;

            if (player.Place >= BOARD_SIZE)
            {
                player.Place -= BOARD_SIZE;
            }
        }

        public Category GetCategoryForPlayer(Player player)
        {
            var position = player.Place;

            switch (position)
            {
                case 0:
                case 4:
                case 8:
                    return Category.Pop;
                case 1:
                case 5:
                case 9:
                    return Category.Science;
                case 2:
                case 6:
                case 10:
                    return Category.Sports;
                default:
                    return Category.Rock;
            }
        }

        public Question GetQuestionForPlayer(Player player)
        {
            var category = GetCategoryForPlayer(player);

            return _questions[category].GetNext();
        }
    }
}
