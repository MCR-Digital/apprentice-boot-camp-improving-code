using System.Collections.Generic;

namespace TriviaGame
{
    public class QuestionSet
    {
        private readonly Dictionary<Category, QuestionDeck> _questionDecks;

        public QuestionSet()
        {
            _questionDecks = new Dictionary<Category, QuestionDeck>()
            {
                { Category.Pop, new QuestionDeck(Category.Pop) },
                { Category.Science, new QuestionDeck(Category.Science) },
                { Category.Sports, new QuestionDeck(Category.Sports) },
                { Category.Rock, new QuestionDeck(Category.Rock) },
            };
        }

        public QuestionDeck GetDeck(Category category)
        {
            return _questionDecks[category];
        }
    }
}
