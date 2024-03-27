/*
 * Lizzie Howell
 * 3/27/2024
 * Assignment 5 - Polymorphism
 */

import java.util.ArrayList;
import java.util.Iterator;

public class Model {
	private int begX, begY;
	private String currentAddMode;
	private boolean collidingWall, editing;
	Pacman pacman;
	ArrayList<Sprite> sprites;

	public Model() {
		sprites = new ArrayList<Sprite>();
		pacman = new Pacman();
	}

	public void update() {
		collidingWall = false;
        Iterator<Sprite> iter1 = sprites.iterator();
        while(iter1.hasNext()){
            Sprite sprite1 = iter1.next();
            if(!sprite1.update()) {
                iter1.remove();
                continue;
            }
            if(!sprite1.isMoving()) {
                continue;
            }

            Iterator<Sprite> iter2 = sprites.iterator();
            while(iter2.hasNext()){
                Sprite sprite2 = iter2.next();
                if (sprite1 != sprite2  && sprite1.doestItCollide(sprite2)){
					//pac v pellet
					if(sprite1.isPac() && sprite2.isPellet()){
						((Pellet)sprite2).eatPellet();
					}
					//pac v wall
					if(sprite1.isPac() && sprite2.isWall()){
						collidingWall = true;
						((Pacman)sprite1).getOutOfWall();
					}
					//pac v fruit
					if(sprite1.isPac() && sprite2.isFruit()){
						((Fruit)sprite2).eatFruit();
					}
					//fruit v wall
					if(sprite1.isFruit() && sprite2.isWall()){
						((Fruit)sprite1).changeYdir();
					}
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
		if (scrollY != 0) {
			begY += scrollY;
		}
		// add the new wall
		Wall wall = new Wall(begX, begY, width, height);
		sprites.add(wall);
	}

	public void addPellet(int mouseX, int mouseY, int scrollY){
		if (scrollY != 0) {
			mouseY += scrollY;
		}
		Pellet pellet = new Pellet(mouseX, mouseY);
		sprites.add(pellet);
		//System.out.println(pellet.toString());
	}
	public void addFruit(int mouseX, int mouseY, int scrollY){
		if (scrollY != 0) {
			mouseY += scrollY;
		}
		Fruit fruit = new Fruit(mouseX, mouseY);
		sprites.add(fruit);
		//System.out.println(fruit.toString());
	}

	public void clearScreen() {
		System.out.println("Clearing sprites...");
		Iterator<Sprite> iterate = sprites.iterator();
		int size = getSprites().size() - 1;
		while (iterate.hasNext()) {
			Sprite sprite = getSprites().get(size);
			getSprites().remove(sprite);
			size--;
		}
	}

	public boolean isCollidingWithWall() {
		return collidingWall;
	}

	public boolean isEditing() {
		return editing;
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

	public String getAddMode() {
		return currentAddMode;
	}

	public void setEditing(boolean value) {
		this.editing = value;
	}

	public void setAddMode(String value) {
		currentAddMode = value;
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
		//System.out.println(getSprites().toString());
	}

	JSON marshal() {
		// System.out.println("marshal from model called.");
		JSON ob = JSON.newObject();
		JSON tmpListWalls = JSON.newList();
		ob.add("walls", tmpListWalls);
		JSON tmpListPellets = JSON.newList();
		ob.add("pellets", tmpListPellets);
		JSON tmpListFruits = JSON.newList();
		ob.add("fruits", tmpListFruits);
		for (int i = 0; i < sprites.size(); i++) {
			if (sprites.get(i).isWall()){
				tmpListWalls.add(sprites.get(i).marshal());
			}
			if (sprites.get(i).isPellet()){
				tmpListPellets.add(sprites.get(i).marshal());
			}
			if (sprites.get(i).isFruit()){
				tmpListFruits.add(sprites.get(i).marshal());
			}
		}
		return ob;
	}

	public void unmarshal(JSON ob) {
		// System.out.println("unmarshal from model called.");
		clearScreen();
		JSON tmpListWalls = ob.get("walls");
		for (int i = 0; i < tmpListWalls.size(); i++) {
			Wall wall = new Wall(tmpListWalls.get(i));
			sprites.add(wall);
		}
		JSON tmpListPellets = ob.get("pellets");
		for (int i = 0; i < tmpListPellets.size(); i++) {
			Pellet pellet = new Pellet(tmpListPellets.get(i));
			sprites.add(pellet);
		}
		JSON tmpListFruits = ob.get("fruits");
		for (int i = 0; i < tmpListFruits.size(); i++) {
			Fruit fruit = new Fruit(tmpListFruits.get(i));
			sprites.add(fruit);
		}
		sprites.add(pacman);
	}
}