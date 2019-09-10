using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace TriviaGame
{
    public class Question
    {
        public int Number { get; set; }

        public Category Category { get; set; }

        public override string ToString()
        {
            return $"{Category.ToString()} Question {Number}";
        }
    }
}
