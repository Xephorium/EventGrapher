
@echo off

:: Clean Project
call clean.bat

:: Compile Project (Targets Sent to "build" Directory)
javac -d build src\*.java src\ui\*.java src\ui\utility\*.java src\io\*.java src\model\*.java src\model\types\*.java

:: Print Update
echo Project compiled.
echo Project running.

:: Run Project (Binaries Read from "build" Directory)
java -cp build Main