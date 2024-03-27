
/*
 * Lizzie Howell
 * 3/27/2024
 * Assignment 5 - Polymorphism
 */

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
				model.startWalls(e.getX(), e.getY());
			} 
			else if (addPellets){
				model.addPellet(e.getX(), e.getY(), view.getScrollY());
			}
			else if (addFruit){
				model.addFruit(e.getX(), e.getY(), view.getScrollY());
			}else {
				for (int i = 0; i < model.getSprites().size(); i++) {
					if ((addWalls == false) && (model.getSprites().get(i).spriteClicked(e.getX(), (e.getY() + view.getScrollY())) == true) 
						&& (model.getSprites().get(i).isWall())) {
						model.getSprites().remove(i);
					}
				}
			}
		}
	}

	public void mouseReleased(MouseEvent e) {
		if (addWalls == true && editMode == true) {
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
		if (key == 'a') {
			addWalls = !addWalls;
			addPellets = false;
			addFruit = false;
			//System.out.println("addWalls is " + addWalls);
			model.setAddMode("Adding Walls");
		}
		if (key == 'p') {
			addPellets = !addPellets;
			addFruit = false;
			addWalls = false;
			//System.out.println("addPellets is " + addPellets);
			model.setAddMode("Adding Pellets");
		}
		if (key == 'f') {
			addFruit = !addFruit;
			addWalls = false;
			addPellets = false;
			//System.out.println("addFruit is " + addFruit);
			model.setAddMode("Adding Fruits");
		}
		// enter editmode
		if (key == 'e') {	
			editMode = !editMode;
			model.setEditing(editMode);
			// System.out.println("Edit mode is " + editMode);
			if (addWalls == false) {
				addWalls = true;
				model.setAddMode("Adding Walls");
				// System.out.println("Add mode is " + addWalls);
			}
		}

		// clear screen
		if (key == 'c') {
			if (model.getSprites().size() > 0) {
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
			model.pacman.movePacRight();
			model.arrowKeyPressed(2);
		}
		if (keyLeft) {
			model.pacman.movePacLeft();
			model.arrowKeyPressed(0);
		}
		if (keyDown) {
			model.pacman.movePacDown();
			view.cameraDown();
			model.arrowKeyPressed(3);
		}
		if (keyUp) {
			model.pacman.movePacUp();
			view.cameraUp();		
			model.arrowKeyPressed(1);
		}
	}
}
