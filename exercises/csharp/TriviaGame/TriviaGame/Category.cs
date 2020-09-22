using System.Collections.Generic;

namespace TriviaGame
{
    public class Category
    {
        public string name { get; set; }
        public LinkedList<string> questions { get; set; }

        public Category(string name, LinkedList<string> questions)
        {
            this.name = name;
            this.questions = questions;
        }

        private string CreateQuestionText(int index)
        {
            return $"{name} Question " + index;
        }

        public void AddQuestion(int questionIndex)
        {
            questions.AddLast(CreateQuestionText(questionIndex));
        }
    }
}
