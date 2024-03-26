/*
 * Lizzie Howell
 * 3/27/2024
 * Assignment 5 - Polymorphism
 */

import java.util.ArrayList;
import java.util.Iterator;

public class Model {
	private int begX, begY;
	private boolean colliding;
	Pacman pacman;
	ArrayList<Sprite> sprites;

	public Model() {
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

		for (int i = 0; i < sprites.size(); i++) {
			Sprite sprite = sprites.get(i);
			int spriteTop = sprite.getY();
			int spriteLeft = sprite.getX();
			int spriteBottom = (sprite.getY() + sprite.getH());
			int spriteRight = (sprite.getX() + sprite.getW());

			if ((((pacHead < spriteTop) && (pacToes > spriteBottom)) || ((pacToes > spriteTop) && (pacToes < spriteBottom)))
					&& ((pacRight > spriteLeft) && (pacLeft < spriteRight))) {
				colliding = true;
				if (sprite.isWall()){
					pacman.getOutOfWall(scrollY);
				}
			}
			if ((((pacLeft > spriteLeft) && (pacLeft < spriteRight)) || ((pacRight > spriteLeft) && (pacRight < spriteRight)))
					&& ((pacToes > spriteTop) && (pacHead < spriteBottom))) {
				colliding = true;
				if (sprite.isWall()){
					pacman.getOutOfWall(scrollY);
				}
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

	public int getLowestWallY() {
		int lowestY = 0;
		for (int i = 0; i < getSprites().size(); i++) {
			if(sprites.get(i).isWall()){
				if (sprites.get(i).getY() > lowestY) {
					lowestY = (sprites.get(i).getY() + sprites.get(i).getH());
				}
			}
		}
		return lowestY;
	}

	public int getHighestWallY() {
		int highestY = Game.WINDOW_HEIGHT;
		for (int i = 0; i < getSprites().size(); i++) {
			if(sprites.get(i).isWall()){
				if (sprites.get(i).getY() < highestY) {
					highestY = (sprites.get(i).getY());
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