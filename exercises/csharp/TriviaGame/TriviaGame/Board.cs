using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace TriviaGame
{
    public class Board
    {
        public Category GetCategoryForPosition(int position)
        {
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
