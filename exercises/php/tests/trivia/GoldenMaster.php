<?php

namespace Tests;

use trivia\GameRunner;

class GoldenMaster
{
    const BYTE_ENCODING = 'UTF-8';

    public static function getResult($i)
    {
        $filePath = sprintf("resources/output%d.txt", $i);

        if (file_exists($filePath)) {
            return file_get_contents($filePath, false, null, 0);
        }

        return self::generateExpectedResult($i);
    }

    private static function generateExpectedResult($i)
    {
        // Your implementation to generate the expected result
        // ...

        $originalOut = fopen('php://output', 'wb');
        $outContent = fopen('php://temp', 'wb+');

        try {
            ob_start();
            try {
//                include 'GameRunner.php'; // Include or require the file containing GameRunner logic
                (new GameRunner())->runGame($i);
            } finally {
                $result = ob_get_clean();
                fwrite($outContent, $result);
                file_put_contents(__DIR__ . sprintf("/../../resources/output%d.txt", $i), $result);
                fclose($originalOut);
            }

            return stream_get_contents($outContent);
        } finally {
            fclose($outContent);
        }
    }
}
