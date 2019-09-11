using System;
using System.IO;

namespace TriviaGame
{
    public class GoldenMaster
    {

        public static string GetResult(int i)
        {
            string result = null;

            try
            {
                result = File.ReadAllText($"{AppDomain.CurrentDomain.BaseDirectory}/resources/output{i}.txt");
            }
            catch (IOException e)
            {
                result = GenerateExpectedResult(i);
            }
            return result;
        }

        private static string GenerateExpectedResult(int i)
        {
            TextWriter oldOut = Console.Out;

            string result;
            MemoryStream memoryStream = new MemoryStream();

            StreamWriter writer = new StreamWriter(memoryStream);

            Console.SetOut(writer);

            GameRunner.Main(new String[] { i.ToString() });
            writer.Flush();

            try
            {
                File.WriteAllBytes($"{AppDomain.CurrentDomain.BaseDirectory}/resources/output{i}.txt", memoryStream.ToArray());
            }
            catch (IOException e)
            {
                Console.WriteLine(e.StackTrace);
            }
            finally
            {
                memoryStream.Position = 0;
                StreamReader reader = new StreamReader(memoryStream);
                result = reader.ReadToEnd();

                memoryStream.SetLength(0);
                Console.SetOut(oldOut);

                try
                {
                    memoryStream.Close();
                    writer.Close();
                }
                catch (IOException e)
                {
                    Console.WriteLine(e.StackTrace);
                }
            }
            return result;
        }
    }
}
