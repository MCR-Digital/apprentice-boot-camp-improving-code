using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace TriviaGame
{
    public static class GameRunner
    {
        private static bool notAWinner;

        public static void Main(string[] args)
        {
            var currentGame = new Game();

            currentGame.AddPlayer("Chet");
            currentGame.AddPlayer("Pat");
            currentGame.AddPlayer("Sue");

            var random = new Random(int.Parse(args[0]));

            do
            {
                currentGame.StartANewTurn(random);
                notAWinner = currentGame.AnswerQuestion(random);
            }
            while (notAWinner) ;
        }
    }
}
