<?php

namespace trivia;

class GameBoard
{
    private $places;

    function __construct()
    {
        $this->places = [
            "Pop", "Science", "Sports",
            "Rock", "Pop", "Science",
            "Sports", "Rock", "Pop",
            "Science", "Sports", "Rock"
        ];
    }

    function getcurrentCategory($currentPlayerPosition ) {
		return $this->places[$currentPlayerPosition];
	}
}
