# Java Log Analyzer CLI

Java Log Analyzer CLI is a small defensive cybersecurity project that analyzes authentication log files from the command line.

The current version reads a sample log file, counts failed login attempts, and groups failed login attempts by IP address. It is designed as a beginner-friendly Java project for practicing file handling, maps, and basic security analysis.

## Features

- Reads log files line by line
- Counts total failed login attempts
- Counts failed login attempts by IP address
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

You can check your Java installation with:

```bash
java -version
javac -version
```

## Usage

Compile the program:

```bash
javac src/LogAnalyzer.java
```

Run it:

```bash
java -cp src LogAnalyzer
```

Expected output with the included sample log:

```text
Failed login attempts: 8

Failed login attempts by IP:
192.168.1.25 -> 5
10.0.0.7 -> 3
```

The IP order may be different because the program uses a `HashMap`.

## How It Works

The program opens `sample_logs/auth.log` and reads it line by line.

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

## Example Use Case

This type of tool is useful for learning basic Blue Team concepts, such as identifying repeated failed login attempts that may indicate brute-force behavior.

## Current Limitations

- The log format is fixed and simple
- The file path is hardcoded in the Java file
- It only detects lines containing `Failed login`
- It does not yet define a threshold for suspicious IPs
- It does not include automated tests yet

## Future Improvements

- Accept the log file path as a command-line argument
- Detect suspicious IPs after a configurable number of failed attempts
- Add support for different log formats
- Sort results by number of failed attempts
- Add unit tests with JUnit
- Improve error handling and output formatting

## Ethics

This project is intended for defensive learning and log analysis practice. It does not perform attacks, scan systems, or interact with external machines.
