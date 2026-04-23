#!/bin/bash
if [ ! -f mysql-connector-j-9.1.0.jar ]; then
    echo "Downloading mysql-connector-j-9.1.0.jar..."
    wget https://repo1.maven.org/maven2/com/mysql/mysql-connector-j/9.1.0/mysql-connector-j-9.1.0.jar
fi
echo "Compiling..."
javac -cp .:mysql-connector-j-9.1.0.jar *.java
echo "Running..."
java -cp .:mysql-connector-j-9.1.0.jar Main
