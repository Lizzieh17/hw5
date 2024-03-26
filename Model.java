/*
 * Lizzie Howell
 * 3/27/2024
 * Assignment 5 - Polymorphism
 */

import java.util.ArrayList;
import java.util.Iterator;

public class Model {
	private ArrayList<Wall> walls;
	private int begX, begY;
	private boolean colliding;
	Pacman pacman;
	ArrayList<Sprite> sprites;

	public Model() {
		walls = new ArrayList<Wall>();
		sprites = new ArrayList<Sprite>();
		pacman = new Pacman();
	}

	public void update() {
		// pacman.update();
	}

	public void checkCollision(int scrollY) {
		// y = head, x = left, toes = y + h, right = x + w
		int pacHead = pacman.getY();
		int pacLeft = pacman.getX();
		int pacToes = (pacman.getY() + pacman.getH());
		int pacRight = (pacman.getX() + pacman.getW());
		colliding = false;

		for (int i = 0; i < walls.size(); i++) {
			Wall wall = walls.get(i);
			int wallTop = wall.getY();
			int wallLeft = wall.getX();
			int wallBottom = (wall.getY() + wall.getH());
			int wallRight = (wall.getX() + wall.getW());

			if ((((pacHead < wallTop) && (pacToes > wallBottom)) || ((pacToes > wallTop) && (pacToes < wallBottom)))
					&& ((pacRight > wallLeft) && (pacLeft < wallRight))) {
				colliding = true;
				pacman.getOutOfWall(scrollY);
			}
			if ((((pacLeft > wallLeft) && (pacLeft < wallRight)) || ((pacRight > wallLeft) && (pacRight < wallRight)))
					&& ((pacToes > wallTop) && (pacHead < wallBottom))) {
				colliding = true;
				pacman.getOutOfWall(scrollY);
			}
		}
	}

	// 0 = left; 1 = up; 2= right; 3 = down;
	public void arrowKeyPressed(int direction) {
		if (direction == 0) {
			// waka left
			pacman.animate(0);
		}
		if (direction == 1) {
			// waka up
			pacman.animate(1);
		}
		if (direction == 2) {
			// waka right
			pacman.animate(2);
		}
		if (direction == 3) {
			// waka down
			pacman.animate(3);
		}
	}

	public void startWalls(int x, int y) {
		begX = x;
		begY = y;
	}

	public void stopWalls(int newX, int newY, int scrollY) {
		// System.out.println("Stoping wall...");
		int width = 0;
		int height = 0;

		// accounting for when the user drags right to left
		if (newX < begX) {
			width = begX - newX;
			begX = newX;
		} else if (newX > begX) {
			width = newX - begX;
		}
		if (newY < begY) {
			height = begY - newY;
			begY = newY;
		} else if (newY > begY) {
			height = newY - begY;
		}
		// add the new wall
		if (scrollY != 0) {
			begY += scrollY;
		}
		Wall wall = new Wall(begX, begY, width, height);
		sprites.add(wall);
	}

	public void clearScreen() {
		System.out.println("Clearing walls...");
		Iterator<Sprite> iterate = sprites.iterator();
		int size = getSprites().size() - 1;
		while (iterate.hasNext()) {
			Sprite sprite = getSprites().get(size);
			getSprites().remove(sprite);
			size--;
		}
	}

	public boolean isColliding() {
		return colliding;
	}

	public double getModelSpeed() {
		return pacman.getPacSpeed();
	}

	public ArrayList<Sprite> getSprites() {
		return sprites;
	}

	public Pacman getPacman() {
		return pacman;
	}

	public int getLowestWallY() {
		int lowestY = 0;
		for (int i = 0; i < getSprites().size(); i++) {
			if(sprites.get(i).isWall()){
				if (sprites.get(i).getY() > lowestY) {
					lowestY = (sprites.get(i).getY());
				}
			}
		}
		return lowestY;
	}

	public int getHighestWallY() {
		int highestY = 800;
		for (int i = 0; i < getSprites().size(); i++) {
			if(sprites.get(i).isWall()){
				if (sprites.get(i).getY() < highestY) {
					highestY = (sprites.get(i).getY()) + sprites.get(i).getH();
				}
			}
		}
		return highestY;
	}

	public void save() {
		JSON mapsave = marshal();
		mapsave.save("map.json");
		System.out.println("Your map have been saved.");
	}

	public void load() {
		JSON mapload = JSON.load("map.json");
		unmarshal(mapload);
		System.out.println("Your map have been loaded.");
		System.out.println(getSprites().toString());
	}

	JSON marshal() {
		// System.out.println("marshal from model called.");
		JSON ob = JSON.newObject();
		JSON tmpListWalls = JSON.newList();
		ob.add("walls", tmpListWalls);
		for (int i = 0; i < sprites.size(); i++) {
			if (sprites.get(i).isWall()){
				tmpListWalls.add(sprites.get(i).marshal());
			}
		}
		return ob;
	}

	public void unmarshal(JSON ob) {
		// System.out.println("unmarshal from model called.");
		//clearScreen();
		JSON tmpListWalls = ob.get("walls");
		for (int i = 0; i < tmpListWalls.size(); i++) {
			Wall wall = new Wall(tmpListWalls.get(i));
			sprites.add(wall);
		}
		sprites.add(pacman);
	}
}