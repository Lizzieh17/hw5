::@echo off
javac Game.java View.java Controller.java Pacman.java Model.java Wall.java JSON.java Sprite.java Pellet.java Fruit.java
if %errorlevel% neq 0 (
	echo There was an error; exiting now.	
) else (
	echo Compiled correctly!  Running Game...
	java Game	
)

