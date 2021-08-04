@echo off
title 编译中...
rd /s /q bin
md bin
xcopy /e src\* bin
dir /s /b bin\*.java > sources.txt
javac -cp lib\*;. @sources.txt
del /s /q bin\*.java
del sources.txt
pause