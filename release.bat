@echo off
rd /s /q bin
md bin
xcopy /e src\* bin
dir /s /b bin\*.java > sources.txt
javac -cp lib\*;. @sources.txt
del /s /q bin\*.java
del sources.txt
cd bin
jar -cvfm game.jar META-INF\MANIFEST.MF *
cd ..

rd /s /q out
md out
md out\lib
md out\natives
xcopy lib\* out\lib
xcopy natives\* out\natives
move bin\game.jar out\
pause