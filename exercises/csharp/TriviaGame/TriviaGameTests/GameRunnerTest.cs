using System;
using System.IO;
using NUnit.Framework;

namespace TriviaGame
{
    public class GameRunnerTest
    {
        private StringWriter _writer;

        [SetUp]
        public void Setup()
        {
            _writer = new StringWriter();
            Console.SetOut(_writer);
        }

        [TearDown]
        public void TearDown()
        {
            _writer.Close();
            Console.Out.Close();
            Console.SetOut(new StreamWriter(Console.OpenStandardOutput()));
        }

        [Test]
        public void GoldenMasterTest()
        {
            for (int i = 0; i < 10000; i++)
            {
                GameRunner.Main(new string[] { i.ToString() });

                var expectedContent = _writer.ToString();
                var actualContent = GoldenMaster.GetResult(i);

                Assert.That(actualContent, Is.EqualTo(expectedContent));

                _writer.GetStringBuilder().Clear();
            }
        }

        [Test]
        public void IsOdd_returns_true_when_odd_number()
        {
            var result = IsEven(3);
            Assert.That(result, Is.True);
        }

        private bool IsEven(int rollResult)
        {
            return rollResult % 2 != 0;
        }
    }
}
