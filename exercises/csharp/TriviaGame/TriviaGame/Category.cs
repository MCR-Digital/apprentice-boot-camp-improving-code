using System.Collections.Generic;

namespace TriviaGame
{
    public class Category
    {
        public string name { get; set; }
        public LinkedList<Question> questions { get; set; }

        public Category(string name, LinkedList<Question> questions)
        {
            this.name = name;
            this.questions = questions;
        }

        public void AddQuestion(int questionIndex)
        {
            questions.AddLast(new Question(name, questionIndex));
        }
    }
}
