import Game from "./game";
import generator from "random-seed";

const gameRunner = (i) => {
    let weHaveAWinner = false;
  
    let game = new Game();
  
    game.add("Chet");
    game.add("Pat");
    game.add("Sue");
  
    const random = generator.create(i);
  
    do {
      game.roll(random.range(5) + 1);
  
      if (random.range(9) == 7) {
        weHaveAWinner = game.wasIncorrectlyAnswered();
      } else {
        weHaveAWinner = game.wasCorrectlyAnswered();
      }
    } while (weHaveAWinner);
  };
  
  export default gameRunner;