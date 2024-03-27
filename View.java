/*
 * Lizzie Howell
 * 3/27/2024
 * Assignment 5 - Polymorphism
 */

//view class: draws our sprites and changes scrollY
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;

public class View extends JPanel {
	private Model model;
	private int scrollY = 0;

	public View(Controller c, Model m) {
		model = m;
		c.setView(this);
	}

	public void paintComponent(Graphics g) {
		if (model.isEditing()) {
			//if we are editing, change color of background and add text at the bottom right of the screen
			g.setColor(new Color(200, 180, 180));
			g.fillRect(0, 0, this.getWidth(), this.getHeight());
			g.setColor(new Color(245, 245, 10));
			g.drawString("Edit Mode: " + model.getAddMode(), 600, 750);
		} else {
			g.setColor(new Color(0));
			g.fillRect(0, 0, this.getWidth(), this.getHeight());
		}

		for (int i = 0; i < model.sprites.size(); i++) {
			model.sprites.get(i).draw(g, scrollY);
		}
	}

	static BufferedImage loadImage(String filepath) {
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File(filepath));
		} catch (Exception e) {
			e.printStackTrace(System.err);
			System.out.println(filepath);
			System.exit(1);
		}
		return image;
	}

	public void cameraUp() {
		// scroll up if we are not at the highest Y, we are not colliding, and pacman is
		// at a good position to scroll to be visible
		if ((model.getHighestWallY() < (scrollY)) && (!model.isCollidingWithWall()) && (model.pacman.getY() < 700)) {
			scrollY -= model.getModelSpeed();
		}
	}

	public void cameraDown() {
		// scroll down if we are not at the lowest Y, we are not colliding, and pacman
		// is at a good position to scroll to be visible
		if (((model.getLowestWallY() - 760) > (scrollY)) && (!model.isCollidingWithWall())
				&& (model.pacman.getY() > 200)) {
			scrollY += model.getModelSpeed();
		}
	}

	public int getScrollY() {
		return scrollY;
	}
}
