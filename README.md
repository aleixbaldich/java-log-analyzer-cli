# Java Log Analyzer CLI

Java Log Analyzer CLI is a small defensive cybersecurity project that analyzes authentication log files from the command line.

The current version reads a sample log file, counts failed login attempts, groups failed login attempts by IP address, and flags suspicious IPs when they reach a fixed threshold. It is designed as a beginner-friendly Java project for practicing file handling, maps, and basic security analysis.

## Features

- Reads log files line by line
- Counts total failed login attempts
- Counts failed login attempts by IP address
- Flags suspicious IPs with 3 or more failed login attempts
- Accepts a log file path as a command-line argument
- Uses a sample authentication log for testing and demonstration
- Runs locally and does not connect to external systems

## Project Structure

```text
java-log-analyzer-cli/
  src/
    LogAnalyzer.java
  sample_logs/
    auth.log
  README.md
  LICENSE
```

## Requirements

- Java JDK installed


## Usage

Compile the program:

```bash
javac -d out src/LogAnalyzer.java
```

Run it with the default sample log:

```bash
java -cp out LogAnalyzer
```

Run it with a specific log file:

```bash
java -cp out LogAnalyzer sample_logs/auth.log
```

Expected output with the included sample log:

```text
Analyzing file: sample_logs/auth.log
Failed login attempts: 8
================================

Failed login attempts by IP:
192.168.1.25 -> 5
10.0.0.7 -> 3
================================

Suspicious IPs (3 or more attempts):
192.168.1.25 -> 5 failed attempts
10.0.0.7 -> 3 failed attempts
```

The IP order may be different because the program uses a `HashMap`.

## How It Works

The program reads the log file path from the first command-line argument. If no argument is provided, it uses:

```text
sample_logs/auth.log
```

Then it opens the selected file and reads it line by line.

If a line contains:

```text
Failed login
```

it is counted as a failed login attempt.

For IP-based counting, the program extracts the text after:

```text
from
```

and stores the result in a `HashMap<String, Integer>` where:

- the key is the IP address
- the value is the number of failed login attempts from that IP

The suspicious IP detection uses a threshold of `3`. If an IP has 3 or more failed login attempts, it is added to a separate map of suspicious IPs.

## Example Use Case

This type of tool is useful for learning basic Blue Team concepts, such as identifying repeated failed login attempts that may indicate brute-force behavior.

## Current Limitations

- The log format is fixed and simple
- The default file path is still hardcoded when no argument is provided
- The suspicious-IP threshold is hardcoded to 3
- It only detects lines containing `Failed login`
- It does not include automated tests yet

## Future Improvements

- Make the suspicious-IP threshold configurable
- Add more sample log files for different scenarios
- Add support for different log formats
- Sort results by number of failed attempts
- Add unit tests with JUnit
- Improve error handling and output formatting

## Ethics

This project is intended for defensive learning and log analysis practice. It does not perform attacks, scan systems, or interact with external machines.
