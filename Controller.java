
/*
 * Lizzie Howell
 * 3/27/2024
 * Assignment 5 - Polymorphism
 */

//controller class: handles user input to effect sprites
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionEvent;

public class Controller implements ActionListener, MouseListener, KeyListener {
	private View view;
	private Model model;
	private boolean addWalls;
	private boolean deleteWalls;
	private boolean addPellets;
	private boolean addFruit;
	private boolean editMode;
	private boolean keyLeft;
	private boolean keyRight;
	private boolean keyUp;
	private boolean keyDown;

	public Controller(Model m) {
		model = m;
		addWalls = false;
		addPellets = false;
		addFruit = false;
		editMode = false;
		keyLeft = false;
		keyRight = false;
		keyUp = false;
		keyDown = false;
	}

	public void actionPerformed(ActionEvent e) {
	}

	void setView(View v) {
		view = v;
	}

	public void mousePressed(MouseEvent e) {
		if (editMode) {
			if (addWalls) {
				//begin the wall
				model.startWalls(e.getX(), e.getY());
			} 
			else if (addPellets){
				//add a pellet to screen where the mouse is
				model.addPellet(e.getX(), e.getY(), view.getScrollY());
			}
			else if (addFruit){
				// add a strawberry to the screen where the fruit is
				int fruitDir = model.sprites.size() % 2;
				// System.out.println("random direction for fruit: " + fruitDir);
				model.addFruit(e.getX(), e.getY(), view.getScrollY(), fruitDir);
			}else if(deleteWalls){
				//delete clicked wall
				for (int i = 0; i < model.sprites.size(); i++) {
					if ((addWalls == false) && (model.sprites.get(i).spriteClicked(e.getX(), (e.getY() + view.getScrollY())) == true) 
						&& (model.sprites.get(i).isWall())) {
						model.sprites.remove(i);
					}
				}
			}
		}
	}

	public void mouseReleased(MouseEvent e) {
		if (addWalls == true && editMode == true) {
			//stop wall and create it
			model.stopWalls(e.getX(), e.getY(), view.getScrollY());
		}
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mouseClicked(MouseEvent e) {
	}

	public void keyPressed(KeyEvent e) {
		char key = Character.toLowerCase(e.getKeyChar());
		// quit
		if (key == 'q') {
			System.exit(1);
		}
		switch (e.getKeyCode()) {
			case KeyEvent.VK_RIGHT:
				keyRight = true;
				break;
			case KeyEvent.VK_LEFT:
				keyLeft = true;
				break;
			case KeyEvent.VK_UP:
				keyUp = true;
				break;
			case KeyEvent.VK_DOWN:
				keyDown = true;
				break;
			case KeyEvent.VK_ESCAPE:
				System.exit(1);
				;
				break;
		}
	}

	public void keyReleased(KeyEvent e) {
		char key = Character.toLowerCase(e.getKeyChar());

		// save
		if (key == 's') {
			model.save();
		}
		// load
		if (key == 'l') {
			model.load();
		}
		//addWall mode
		if (key == 'a') {
			addWalls = !addWalls;
			addPellets = false;
			addFruit = false;
			deleteWalls = false;
			model.setAddMode("Adding Walls");
		}
		//addPellet mode
		if (key == 'p') {
			addPellets = true;
			addFruit = false;
			addWalls = false;
			deleteWalls = false;
			model.setAddMode("Adding Pellets");
		}
		//addFruit mode
		if (key == 'f') {
			addFruit = true;
			addWalls = false;
			addPellets = false;
			model.setAddMode("Adding Fruits");
		}
		//deleteWalls mode
		if (key == 'r') {
			addFruit = false;
			addWalls = false;
			addPellets = false;
			deleteWalls = true;
			model.setAddMode("Deleting Walls");
		}
		// enter editmode
		if (key == 'e') {	
			//change editmode value
			editMode = !editMode;
			//tell model we are editing so string on screen updates
			model.setEditing(editMode);
			if (addWalls == false) {
				addWalls = true;
				deleteWalls = false;
				model.setAddMode("Adding Walls");
			}
		}

		// clear screen of all sprites
		if (key == 'c') {
			if (model.sprites.size() > 0) {
				model.clearScreen();
				System.out.println("Cleared all Sprites.");
			} else {
				System.out.println("No Sprites to clear.");
			}
		}
		switch (e.getKeyCode()) {
			case KeyEvent.VK_RIGHT:
				keyRight = false;
				break;
			case KeyEvent.VK_LEFT:
				keyLeft = false;
				break;
			case KeyEvent.VK_UP:
				keyUp = false;
				break;
			case KeyEvent.VK_DOWN:
				keyDown = false;
				break;
		}
	}

	public void keyTyped(KeyEvent e) {
	}

	public void update() {
		model.pacman.savePac();
		if (keyRight) {
			//move pacman to the right and animate her
			model.pacman.movePacRight();
			model.arrowKeyPressed(2);
		}
		if (keyLeft) {
			//move pacman to the left and animate her
			model.pacman.movePacLeft();
			model.arrowKeyPressed(0);
		}
		if (keyDown) {
			//move pacman down, scroll down, and animate her
			model.pacman.movePacDown();
			view.cameraDown();
			model.arrowKeyPressed(3);
		}
		if (keyUp) {
			//move pacman up, scroll up, and animate her
			model.pacman.movePacUp();
			view.cameraUp();		
			model.arrowKeyPressed(1);
		}
	}
}
