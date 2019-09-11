using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace TriviaGame
{
    public class QuestionDeck
    {
        private int _currentQuestion;

        public QuestionDeck(Category category)
        {
            _currentQuestion = 0;
            Category = category;
        }

        public Category Category { get; }

        public Question GetNext()
        {
            var questionNumber = _currentQuestion;
            _currentQuestion++;

            return new Question
            {
                Number = questionNumber,
                Category = Category
            };
        }
    }
}
