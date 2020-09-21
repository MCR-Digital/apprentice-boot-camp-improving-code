namespace TriviaGame
{
    public abstract class Question
    {
        public string Name;
        public Question(string name)
        {
            Name = name;
        }
    }

    public class ScienceQuestion : Question
    {
        public ScienceQuestion(string name) : base(name)
        {
        }
    }    
    public class PopQuestion : Question
    {
        public PopQuestion(string name) : base(name)
        {
        }
    }    
    public class SportsQuestion : Question
    {
        public SportsQuestion(string name) : base(name)
        {
        }
    }    
    public class RockQuestion : Question
    {
        public RockQuestion(string name) : base(name)
        {
        }
    }
}