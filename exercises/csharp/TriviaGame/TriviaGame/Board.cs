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
    }
}
