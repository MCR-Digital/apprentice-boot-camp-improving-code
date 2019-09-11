using System;
using System.IO;

namespace TriviaGame
{
    public static class GoldenMaster
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
            string result;

            var oldOut = Console.Out;
            var memoryStream = new MemoryStream();
            var writer = new StreamWriter(memoryStream);

            Console.SetOut(writer);

            GameRunner.Main(new [] { i.ToString() });
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
                var reader = new StreamReader(memoryStream);
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
