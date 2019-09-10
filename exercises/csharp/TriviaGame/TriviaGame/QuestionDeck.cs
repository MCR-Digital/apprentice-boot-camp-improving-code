using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace TriviaGame
{
    public class QuestionDeck
    {
        private readonly int _questionCount;
        private int _currentQuestion;

        public QuestionDeck(Category category, int questionCount)
        {
            _questionCount = questionCount;
            _currentQuestion = 0;
            Category = category;
        }

        public Category Category { get; }

        public string GetNext()
        {
            var questionNumber = _currentQuestion;
            _currentQuestion++;
            return $"{Category.ToString()} Question {questionNumber}";
        }
    }
}
