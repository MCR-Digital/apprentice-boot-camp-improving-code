using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace TriviaGame
{
   public class GameRunner
    {

        private static bool doesGameHaveWinner;

        public static void Main(String[] args)
        {
            Game game = new Game();
            Player player = new Player();

            player.AddPlayer("Chet");
            player.AddPlayer("Pat");
            player.AddPlayer("Sue");

            game._player = player;

            Random rand = new Random(Int32.Parse(args[0]));

            do
            {
                // turn the below to a method that generates a random dice number
                game.RollDice(rand.Next(5) + 1);

                if (rand.Next(9) == 7)
                {
                    doesGameHaveWinner = game.WasIncorrectlyAnswered();
                }
                else
                {
                    doesGameHaveWinner = game.WasCorrectlyAnswered();
                }



            } while (doesGameHaveWinner);

        }
    }
}
