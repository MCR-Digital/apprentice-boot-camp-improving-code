<?php

namespace trivia;

class GameBoard
{
    private $places;
    private $popQuestions;
    private $scienceQuestions;
    private $sportsQuestions;
    private $rockQuestions;

    private const MAX_QUESTIONS_PER_CATEGORY = 50;

    function __construct()
    {
        $this->places = [
            "Pop", "Science", "Sports",
            "Rock", "Pop", "Science",
            "Sports", "Rock", "Pop",
            "Science", "Sports", "Rock"
        ];

        $this->popQuestions = array();
        $this->scienceQuestions = array();
        $this->sportsQuestions = array();
        $this->rockQuestions = array();

        for ($index = 0; $index < self::MAX_QUESTIONS_PER_CATEGORY; $index++) {
            array_push($this->popQuestions, "Pop Question " . $index);
            array_push($this->scienceQuestions, ("Science Question " . $index));
            array_push($this->sportsQuestions, ("Sports Question " . $index));
            array_push($this->rockQuestions, $this->createRockQuestion($index));
        }
    }

    function createRockQuestion($index)
    {
        return "Rock Question " . $index;
    }


    function getcurrentCategory($currentPlayerPosition)
    {
        return $this->places[$currentPlayerPosition];
    }

    function  askQuestion($currentPlayerPosition)
    {
        if ($this->getcurrentCategory($currentPlayerPosition) == "Pop")
            echoln(array_shift($this->popQuestions));
        if ($this->getcurrentCategory($currentPlayerPosition) == "Science")
            echoln(array_shift($this->scienceQuestions));
        if ($this->getcurrentCategory($currentPlayerPosition) == "Sports")
            echoln(array_shift($this->sportsQuestions));;
        if ($this->getcurrentCategory($currentPlayerPosition) == "Rock")
            echoln(array_shift($this->rockQuestions));
    }
}
