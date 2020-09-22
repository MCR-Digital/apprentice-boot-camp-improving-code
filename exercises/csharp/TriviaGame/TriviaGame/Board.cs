namespace TriviaGame
{
    public class Board
    {
        public bool IsRollResultOdd(int rollResult)
        {
            if (rollResult % 2 != 0)
            {
                return true;
            }
            else
            {
                return false;
            }
        }
    }

}