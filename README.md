# Java Log Analyzer CLI

Java Log Analyzer CLI is a small defensive cybersecurity project that analyzes authentication log files from the command line.

The current version reads a sample log file, counts failed login attempts, groups failed login attempts by IP address, flags suspicious IPs when they reach a fixed threshold, and detects successful logins outside normal working hours. It is designed as a beginner-friendly Java project for practicing file handling, maps, and basic security analysis.

## Features

- Reads log files line by line
- Counts total failed login attempts
- Counts failed login attempts by IP address
- Flags suspicious IPs using a configurable failed-attempt threshold
- Detects successful logins outside normal working hours
- Prints clear messages when no suspicious IPs or after-hours logins are found
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
    brute_force.log
    clean.log
    mixed_activity.log
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

Run it with a specific log file and threshold:

```bash
java -cp out LogAnalyzer sample_logs/brute_force.log 4
```

In this example, an IP is considered suspicious when it has 4 or more failed login attempts.

Other included sample logs:

```bash
java -cp out LogAnalyzer sample_logs/clean.log
java -cp out LogAnalyzer sample_logs/brute_force.log 3
java -cp out LogAnalyzer sample_logs/mixed_activity.log 3
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

================================
After-hours successful logins:
2026-07-11 02:13:40 INFO Successful login for user alice from 192.168.1.10
2026-07-11 21:45:02 INFO Successful login for user bob from 192.168.1.18
```

If no IP reaches the selected threshold, the program prints:

```text
No suspicious IPs found.
```

If no successful login happens outside normal working hours, the program prints:

```text
No after-hours successful logins found.
```

The IP order may be different because the program uses a `HashMap`.

## How It Works

The program reads the log file path from the first command-line argument. If no argument is provided, it uses:

```text
sample_logs/auth.log
```

The suspicious-IP threshold is read from the second command-line argument. If no threshold is provided, it uses:

```text
3
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

If an IP has at least the selected number of failed login attempts, it is added to a separate map of suspicious IPs.

The after-hours login detection checks lines containing:

```text
Successful login
```

It extracts the hour from the timestamp and prints successful logins that happen before `08:00` or at/after `20:00`. If none are found, it prints a clear message instead of leaving the section empty.

## Example Use Case

This type of tool is useful for learning basic Blue Team concepts, such as identifying repeated failed login attempts that may indicate brute-force behavior or spotting valid logins at unusual hours.

## Sample Logs

- `auth.log`: default authentication log with successful and failed login attempts
- `clean.log`: normal activity with no failed login attempts
- `brute_force.log`: repeated failed login attempts from the same IP address
- `mixed_activity.log`: failed login attempts from multiple IP addresses

## Current Limitations

- The log format is fixed and simple
- The default file path is still hardcoded when no argument is provided
- The default suspicious-IP threshold is 3 when no argument is provided
- Normal working hours are hardcoded as 08:00 to 20:00
- It only detects lines containing `Failed login`
- It only detects after-hours activity for lines containing `Successful login`
- It does not include automated tests yet

## Future Improvements

- Add support for different log formats
- Make normal working hours configurable
- Sort results by number of failed attempts
- Add unit tests with JUnit
- Improve error handling and output formatting

## Ethics

This project is intended for defensive learning and log analysis practice. It does not perform attacks, scan systems, or interact with external machines.
