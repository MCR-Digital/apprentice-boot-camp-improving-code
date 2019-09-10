using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace TriviaGame.Core
{
  public class GameRunner
  {

    private static bool notAWinner;

    public static void Main(String[] args)
    {
      Game aGame = new Game();

      aGame.add("Chet");
      aGame.add("Pat");
      aGame.add("Sue");

      Random rand = new Random(Int32.Parse(args[0]));

      do
      {

        aGame.roll(rand.Next(5) + 1);

        if (rand.Next(9) == 7)
        {
          notAWinner = aGame.wrongAnswer();
        }
        else
        {
          notAWinner = aGame.wasCorrectlyAnswered();
        }



      } while (notAWinner);

    }
  }
}
