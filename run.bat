@echo off
if not exist mysql-connector-j-9.1.0.jar (
    echo Downloading mysql-connector-j-9.1.0.jar...
    powershell -Command "Invoke-WebRequest -Uri 'https://repo1.maven.org/maven2/com/mysql/mysql-connector-j/9.1.0/mysql-connector-j-9.1.0.jar' -OutFile 'mysql-connector-j-9.1.0.jar'"
)
echo Compiling...
javac -cp .;mysql-connector-j-9.1.0.jar *.java
echo Running...
java -cp .;mysql-connector-j-9.1.0.jar Main
pause
