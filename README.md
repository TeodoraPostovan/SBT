# SBT - Security Benchmarking Tool

## Introduction
A simple implementation of a Security Benchmarking Tool for Lab3.
This version provides the following functionality:
1. Load and parse a Tenable Security Policy file (*.audit).
2. Present the Security Policy on screen in table format.
3. Save the parsed Security Policy in a file in JSON format.
4. Load the Security Polici from a previousely saved JSON file.
5. Filter the options: present only the options that contain the specified text (in any column).
6. Select / deselect individual options.
7. Select / deselect all options that match the filter.
8. Create and save a policy that contains only the selected options under the same name or a different one.
9. Perform an audit of the workstation, using the options that were selected. Please note that current version performs audit only for options of "REGISTRY_SETTING" type.
10. Present in the table the result of the audit:
	- The "Status" column contains the result of the audit: "PASSED", "FAILED" or "UNKNOWN" (for the options that are not supported).
	- The "System Value" column contains the value on the computer.
	- The "value_date" column contains the value that should be as per the Security Policy.
11. Enforce the policy on the selected settings. Theoretically it should work for most of the settings of the "REGISTRY_SETTING" type. However, in order to avoid accidentally altering computer settings with undesired values, I have limited the tool to be able to enforce only the following settings:
	- Access data sources across domains - Internet Zone
	- Access data sources across domains - Restricted Sites Zone
	- Allow Basic authentication - Client - AllowBasic
	- Allow Basic authentication - Service - AllowBasic
	- Allow META REFRESH
	- Interactive logon: Smart card removal behavior
12. Rollback to the system's initial settings.

## Technologies
- The tool is developed using Java 8.
- The GUI is implemented using java SWING classes.
- It uses the org.json.json library (https://github.com/stleary/JSON-java).

## Build and Launch
To run the tool you need to have Java 8 installed.

!!! Important !!! You need to run the tool as Administrator in order to be able to enforse a Security Policy.

1. Edit build.cmd and run.cmd by setting JAVA_HOME to match the path of Java 8 installation on your Computer.
2. Open the Windows Start menu and type "cmd".
3. Right-click the "Command Prompt" and click "Run as administrator".
4. In the command window that opens change directory to where the stb is copied. I.e.: >cd c:\stb
2. Launch build.cmd and wait for it to finish. It will build the tool. This operation is necessary only the first time.
3. Launch run.cmd to start the tool.