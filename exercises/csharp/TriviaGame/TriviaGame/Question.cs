namespace TriviaGame
{
    public class Question
    {
        public string category { get; set; }
        public int index { get; set; }
        public string text { get; set; }

        public Question(string category, int index)
        {
            this.category = category;
            this.index = index;
            text = CreateQuestionText();
        }

        private string CreateQuestionText()
        {
            return $"{category} Question " + index;
        }
    }
}
