import getResult from './golden-master'
import gameRunner from '../src/game'
import captureOutput from './capture-console-output'

describe('Running the golden master', () => {
  it('should run against 50 results from the golden master and be equivalent', () => {
    for (let i = 0; i < 50; i++) {
      const result = getResult(i)

      expect(result).toEqual(captureOutput(() => gameRunner(i)))
    }
  })
})
