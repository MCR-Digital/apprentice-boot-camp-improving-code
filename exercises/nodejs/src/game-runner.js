import generator from "random-seed";
import Game from "./game";

const gameRunner = (i) => {
  const game = new Game();

  let hasWonGame = false;

  game.addPlayer("Chet");
  game.addPlayer("Pat");
  game.addPlayer("Sue");

  const random = generator.create(i);

  do {
    game.roll(random.range(5) + 1);

    if (random.range(9) === 7) {
      hasWonGame = game.wasIncorrectlyAnswered();
    } else {
      hasWonGame = game.wasCorrectlyAnswered();
    }
  } while (hasWonGame);
};

export default gameRunner;
