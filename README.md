# Project Title

Simple project parser for CRON expression.

## Dependencies
* Java version 17+ added to user environemnts variables
* Maven version 3.1+ added to user environemnts variables

## To run the program and test it, you need to:
1. Copy the Git repository.
2. Build the project using the Maven tool.
```
mvn clean install
```
3. Navigate to the target folder and run the program
```
cd target
```
4 Execute jar file.
```
java -jar cronparser-1.0.jar "*/15 0 1,15 JAN-SEP 1-5 /usr/bin/find"+
```



