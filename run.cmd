@echo off
cd /d "%~dp0"
java -jar dist\board-rest-api-1.0.0.jar --spring.profiles.active=demo
pause
