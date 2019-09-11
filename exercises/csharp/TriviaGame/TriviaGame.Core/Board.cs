using System.Collections.Generic;
using System.Linq;

namespace TriviaGame.Core
{
  public class Board
  {
    public static readonly int MAX_QUESTIONS_PER_CATEGORY = 50;
    private readonly List<Question> _questions = new List<Question>();
    private readonly string[] _categories = { "Pop", "Science", "Sports", "Rock" };

    public Board()
    {
      _questions = _categories.SelectMany(category => Enumerable
            .Repeat($"{category} Question ", MAX_QUESTIONS_PER_CATEGORY)
            .Select((question, index) => new Question
            {
              Title = question + index,
              Type = category
            }))
            .ToList();
    }

    public string NextQuestion(Player player)
    {
      var nextQuestion = _questions.Where(question => question.Type == player.Position?.Category).First();
      _questions.Remove(nextQuestion);

      return nextQuestion.Title;
    }
  }
}