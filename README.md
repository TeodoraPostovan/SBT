# SBT - Security Benchmarking Tool
## Introduction
A simple implementation of a Security Benchmarking Tool for Lab1.
This version provides the following functionality:
1. Load and parse a Tenable Security Policy file (*.audit).
2. Present the Security Policy on screen in JSON format.
3. Save the parsed Security Policy in a file in JSON format.
4. Load the Security Polici from a previousely saved JSON file.

## Technologies
- The tool is developed using Java 8.
- The GUI is implemented using java SWING classes.
- It uses the org.json.json library (https://github.com/stleary/JSON-java).

## Build and Launch
To run the tool you need to have Java 8 installed.
1. Edit build.cmd and run.cmd by setting JAVA_HOME to match the path of Java 8 installation on your Computer.
2. Launch build.cmd and wait for it to finish. It will build the tool. This operation is necessary only the first time.
3. Launch run.cmd to start the tool.