using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace TriviaGame
{
    public class Questions
    {
        public LinkedList<string> popQuestions = new LinkedList<string>();
        public LinkedList<string> scienceQuestions = new LinkedList<string>();
        public LinkedList<string> sportsQuestions = new LinkedList<string>();
        public LinkedList<string> rockQuestions = new LinkedList<string>();

        public Questions()
        {
            SeedQuestions();
        }

        public void SeedQuestions()
        {
            for (int i = 0; i < 50; i++)
            {
                popQuestions.AddLast("Pop Question " + i);
                scienceQuestions.AddLast("Science Question " + i);
                sportsQuestions.AddLast("Sports Question " + i);
                rockQuestions.AddLast("Rock Question " + i);
            }
        }
        public string GetCurrentQuestionCategory(int playerPlace)
        {
            switch (playerPlace)
            {
                case 0:
                case 4:
                case 8:
                    return "Pop";
                case 1:
                case 5:
                case 9:
                    return "Science";
                case 2:
                case 6:
                case 10:
                    return "Sports";
                default:
                    return "Rock";
            }
        }

        public void AskQuestion(int playerPlace)
        {
            string currentCategory = GetCurrentQuestionCategory(playerPlace);

            if (currentCategory == "Pop")
            {
                Console.WriteLine(popQuestions.First());
                popQuestions.RemoveFirst();
            }
            if (currentCategory == "Science")
            {
                Console.WriteLine(scienceQuestions.First());
                scienceQuestions.RemoveFirst();
            }
            if (currentCategory == "Sports")
            {
                Console.WriteLine(sportsQuestions.First());
                sportsQuestions.RemoveFirst();
            }
            if (currentCategory == "Rock")
            {
                Console.WriteLine(rockQuestions.First());
                rockQuestions.RemoveFirst();
            }
        }
    }
}
