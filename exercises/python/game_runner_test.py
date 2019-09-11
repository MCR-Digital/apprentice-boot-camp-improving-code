import unittest
from golden_master import GoldenMaster
from trivia import run
from io import StringIO
import sys

class GameRunnerTest(unittest.TestCase):
        
    def test_golden_master(self):

        original_out = sys.stdout
        for i in range(0, 10):
            out_content = StringIO()
            sys.stdout = out_content

            run(i)
            sys.stdout = original_out

            result = GoldenMaster().get_result(i)
            self.assertEqual(str(out_content.getvalue()), result)


if __name__ == '__main__':
    unittest.main()
