using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace TriviaGame.Core
{
  public static class GameRunner
  {

    private static bool notAWinner;

    public static void Main(String[] args)
    {
      Game newGame = new Game();

      newGame.AddPlayer("Chet");
      newGame.AddPlayer("Pat");
      newGame.AddPlayer("Sue");

      Random rand = new Random(Int32.Parse(args[0]));

      do
      {
        newGame.OnDieRoll(rand.Next(5) + 1);
        notAWinner = newGame.OnAnswer(rand.Next(9) != 7);
      } while (notAWinner);

    }
  }
}
