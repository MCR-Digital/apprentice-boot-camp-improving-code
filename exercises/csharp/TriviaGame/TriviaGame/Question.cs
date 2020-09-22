using System.Collections.Generic;

namespace TriviaGame
{
    public abstract class Question
    {
        public LinkedList<string> Questions { get; } = new LinkedList<string>();

    }

    public class ScienceQuestion : Question
    {
        public static int[] ScienceSpaces { get; set; }

    }    
    public class PopQuestion : Question
    {
    
    }    
    public class SportsQuestion : Question
    {

    }    
    public class RockQuestion : Question
    {

    }
}