<?php

namespace Tests;

use PHPUnit\Framework\TestCase;
use trivia\GameRunner;

class GameRunnerTest extends TestCase
{
    private $outContent;
    private $originalOut;

    protected function setUp(): void
    {
        $this->originalOut = fopen('php://output', 'w');
    }

    protected function tearDown(): void
    {
        fclose($this->originalOut);
    }

    public function testGoldenMaster()
    {

        for ($i = 0; $i < 10000; $i++) {
            ob_start();
            (new GameRunner())->runGame($i);
            $capturedOutput = ob_get_clean(); // Capture the output directly
            GoldenMaster::getResult($i); // shouldn’t need this line
            $this->assertEquals(GoldenMaster::getResult($i), $capturedOutput);
        }
    }
}
