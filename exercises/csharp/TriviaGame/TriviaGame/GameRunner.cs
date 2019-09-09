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
        public static void Main(string[] args)
        {

            string[] fruitAndVeg = new[] { "apple", "banana", "carrot", "date", "eggplant", "french beans" };
            Array.Sort(fruitAndVeg);

            Console.WriteLine("First display of produce names to the console:");
            Array.ForEach(fruitAndVeg, Console.WriteLine);

        }
    }
}
