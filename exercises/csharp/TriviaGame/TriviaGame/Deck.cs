using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace TriviaGame
{
    public class Deck
    {
        public readonly Dictionary<Category, Queue<string>> questionCategories = new Dictionary<Category, Queue<string>>
        {
            { Category.Pop, new Queue<string>() },
            { Category.Science, new Queue<string>() },
            { Category.Sports, new Queue<string>() },
            { Category.Rock, new Queue<string>() }
        };
        
        public Deck(int TotalQuestions)
        {
            for (var i = 0; i < TotalQuestions; i++)
            {
                questionCategories[Category.Pop].Enqueue("Pop Question " + i);
                questionCategories[Category.Science].Enqueue(("Science Question " + i));
                questionCategories[Category.Sports].Enqueue(("Sports Question " + i));
                questionCategories[Category.Rock].Enqueue("Rock Question " + i);
            }
        }
    }
}
