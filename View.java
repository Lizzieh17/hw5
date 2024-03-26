/*
 * Lizzie Howell
 * 3/27/2024
 * Assignment 5 - Polymorphism
 */

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
//import java.io.IOException;
import java.io.File;

public class View extends JPanel
{
	private Model model;
	private int scrollY = 0;

	public View(Controller c, Model m)
	{
		model = m;
		c.setView(this);
	}

	public void paintComponent(Graphics g){
		g.setColor(new Color(25, 25, 25));
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		
		for(int i = 0; i < model.getSprites().size(); i++){
			model.sprites.get(i).draw(g, scrollY);
		}
	}

	static BufferedImage loadImage(String filepath){
		BufferedImage image = null;
		try{
			image = ImageIO.read(new File(filepath));
		}
		catch(Exception e){
			e.printStackTrace(System.err);
			System.out.println(filepath);
			System.exit(1);
		}
		return image;
	}

	public void cameraUp(){
		if((model.getHighestWallY() < (scrollY)) && (!model.isColliding())){
			scrollY -= model.getModelSpeed();
		}
	}
	
	public void cameraDown(){
		if(((model.getLowestWallY() - 760) > (scrollY)) && (!model.isColliding())){
			scrollY += model.getModelSpeed();
		}
	}
	public int getScrollY(){
		return scrollY;
	}
}
