const captureOutput = (functionToCaptureOutput) => {
  let output = ''

  const originalStdoutWrite = process.stdout.write.bind(process.stdout)

  process.stdout.write = (chunk, encoding, callback) => {
    if (typeof chunk === 'string') {
      // hacky way of extracting only console output without timings
      
      const progOutput = chunk.split("\n");
      if (progOutput[1]) {
        output += progOutput[1].trim() + "\n";
      }
    }

    return originalStdoutWrite(chunk, encoding, callback)
  }

  functionToCaptureOutput()

  process.stdout.write = originalStdoutWrite

  return output
}

export default captureOutput
