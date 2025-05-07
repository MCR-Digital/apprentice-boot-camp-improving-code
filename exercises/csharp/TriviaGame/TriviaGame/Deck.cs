using System;
using System.Collections.Generic;
using System.Text;

namespace TriviaGame
{
    public class Deck
    {
        public Category category { get; set; }
        public LinkedList<string> Questions { get; set; } = new LinkedList<string>();

        public Deck(string categoryName)
        {
            category = new Category(categoryName);
        }

        public void AddQuestion(int questionNumber)
        {
            Questions.AddLast($"{category.Name} Question " + questionNumber);

        }
    }
}
