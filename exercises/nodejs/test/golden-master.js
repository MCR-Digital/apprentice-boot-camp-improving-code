import fs from 'fs'
import gameRunner from '../src/game'
import captureOutput from './capture-console-output'

function createFolder(foldername) {
  try {
    fs.mkdirSync(foldername);
  } catch (error) {
    if (!error === "EEXIST") {
      throw error;
    }
  }
}

const generateExpectedResult = (i) => {
  createFolder("./resources");

  const masterFile = fs.createWriteStream(`./resources/output${i}.txt`, {
    flags: 'w'
  })

  const output = captureOutput(() => gameRunner(i))

  masterFile.write(output)

  return output
}

const getResult = (i) => {
  let result
  try {
    result = fs.readFileSync(`./resources/output${i}.txt`, 'utf-8')
  } catch (error) {
    result = generateExpectedResult(i)
  }
  return result
}

export default getResult


