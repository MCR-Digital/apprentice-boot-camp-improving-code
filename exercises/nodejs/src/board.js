import {
  POP_CATEGORY,
  SPORT_CATEGORY,
  ROCK_CATEGORY,
  SCIENCE_CATEGORY,
} from "./categories";

const BOARD_SIZE = 12;

export class Board {
  constructor() {
    this.boardPosition = [];
    const categories = [];
    categories.push(POP_CATEGORY);
    categories.push(SCIENCE_CATEGORY);
    categories.push(SPORT_CATEGORY);
    categories.push(ROCK_CATEGORY);

    const categoryPositions = BOARD_SIZE / categories.length;

    for (let i = 0; i < categoryPositions; i++) {
      this.boardPosition.push(POP_CATEGORY);
      this.boardPosition.push(SCIENCE_CATEGORY);
      this.boardPosition.push(SPORT_CATEGORY);
      this.boardPosition.push(ROCK_CATEGORY);
    }
  }
  getCategoryForBoardPosition(position) {
    return this.boardPosition[position];
  }
}
