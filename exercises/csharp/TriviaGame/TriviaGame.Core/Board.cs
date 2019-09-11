using System.Collections.Generic;
using System.Linq;

namespace TriviaGame.Core
{
  public class Board
  {
    public static readonly int MAX_QUESTIONS_PER_CATEGORY = 50;
    private readonly Dictionary<string, List<string>> _questions = new Dictionary<string, List<string>>();
    private readonly string[] _categories = { "Pop", "Science", "Sports", "Rock" };
    private string _category(int value) => _categories[value % 4];

    public Board()
    {
      _questions = _categories
        .ToDictionary(
          category => category,
          category => Enumerable
            .Repeat($"{category} Question ", MAX_QUESTIONS_PER_CATEGORY)
            .Select((question, index) => question + index)
            .ToList());
    }

    public string NextQuestion(int boardPosition)
    {
      return _questions[_category(boardPosition)].First();
    }
  }
}