/*
 * Lizzie Howell
 * 3/27/2024
 * Assignment 5 - Polymorphism
 */

//model class: handles sprites and updates them by iterator over the sprites array list
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
		//create outside iterator
        Iterator<Sprite> iter1 = sprites.iterator();
		//loop while there is another sprite to iterate to
        while(iter1.hasNext()){
			//select sprite in list
            Sprite sprite1 = iter1.next();
			//if the sprites update() returns fales (i.e. does not exist anymore) then delete the sprite
			// should only be pellets and fruits since they can be eaten
            if(!sprite1.update()) {
                iter1.remove();
                continue;
            }
			//if the sprite is not moving (i.e. this would be pellets and walls)
            if(!sprite1.isMoving()) {
                continue;
            }
			sprite1.shouldIWrap();
			//create inside iterator
            Iterator<Sprite> iter2 = sprites.iterator();
			//while the outside iterator has another sprite and the inside iterator has a sprite
            while(iter2.hasNext()){
				//create sprite from the 2nd iterator list
                Sprite sprite2 = iter2.next();
				//if the first sprite and second sprite are not the same and sprite1 collides with sprite2
                if (sprite1 != sprite2  && sprite1.doestItCollide(sprite2)){
					//pac v pellet
					//pacman is colliding with the pellet and will mark it as eaten
					if(sprite1.isPac() && sprite2.isPellet()){
						((Pellet)sprite2).eatPellet();
					}
					//pac v wall
					//pacman is colliding with a wall and will get out of that wall
					if(sprite1.isPac() && sprite2.isWall()){
						collidingWall = true;
						((Pacman)sprite1).getOutOfWall();
					}
					//pac v fruit
					//pacman is colliding with a fruit and will mark it as eaten
					if(sprite1.isPac() && sprite2.isFruit()){
						((Fruit)sprite2).eatFruit();
					}
					//fruit v wall
					//fruit is colliding will wall and will change its direction
					if(sprite1.isFruit() && sprite2.isWall()){
						((Fruit)sprite1).changedir();
					}
				}
            }
        }
    }

	// 0 = left; 1 = up; 2= right; 3 = down;
	public void arrowKeyPressed(int direction) {
		if (direction == 0) {
			// animate pacman left
			pacman.animate(0);
		}
		if (direction == 1) {
			// animate pacman up
			pacman.animate(1);
		}
		if (direction == 2) {
			// animate pacman right
			pacman.animate(2);
		}
		if (direction == 3) {
			// animate pacman down
			pacman.animate(3);
		}
	}

	public void startWalls(int x, int y) {
		//begin new wall by marking the beginning x and y
		begX = x;
		begY = y;
	}

	public void stopWalls(int newX, int newY, int scrollY) {
		//stop new wall by taking in the new x and new y and the scrollY
		// then setting the values appropriately
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
		//account for the scrollY
		if (scrollY != 0) {
			begY += scrollY;
		}
		// add the new wall
		Wall wall = new Wall(begX, begY, width, height);
		sprites.add(wall);
	}

	public void addPellet(int mouseX, int mouseY, int scrollY){
		//add a pellet to the sprites arraylist
		//acount for scrollY
		if (scrollY != 0) {
			mouseY += scrollY;
		}
		//create new pellet based on where the mouse is
		Pellet pellet = new Pellet(mouseX, mouseY);
		//add the new pellet to the sprites arraylist
		sprites.add(pellet);
	}
	public void addFruit(int mouseX, int mouseY, int scrollY, int fruitDir){
		//add fruit to the sprites array list
		//acount for scrollY
		if (scrollY != 0) {
			mouseY += scrollY;
		}
		//create new fruit based on mouse position
		Fruit fruit = new Fruit(mouseX, mouseY, fruitDir);
		//add new fruit to the sprites arraylist
		sprites.add(fruit);
	}

	public void clearScreen() {
		//clear all sprites from the screen by iteratoring through the sprites arraylist
		//  and removing each sprite
		System.out.println("Clearing sprites...");
		Iterator<Sprite> iterate = sprites.iterator();
		int size = sprites.size() - 1;
		while (iterate.hasNext()) {
			Sprite sprite = sprites.get(size);
			sprites.remove(sprite);
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

	public int getLowestWallY() {
		//grab the lowest wall's y for the scrolling mechanic
		int lowestY = 0;
		for (int i = 0; i < sprites.size(); i++) {
			if(sprites.get(i).isWall()){
				if (sprites.get(i).getY() > lowestY) {
					lowestY = (sprites.get(i).getY() + sprites.get(i).getH());
				}
			}
		}
		return lowestY;
	}

	public int getHighestWallY() {
		//grab the highest wall's y for the scrolling mechanic
		int highestY = Game.WINDOW_HEIGHT;
		for (int i = 0; i < sprites.size(); i++) {
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
	}

	JSON marshal() {
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