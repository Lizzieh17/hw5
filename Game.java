/*
 * Lizzie Howell
 * 3/27/2024
 * Assignment 5 - Polymorphism
 */

import javax.swing.JFrame;
import java.awt.Toolkit;

//our main game class which holds our main function
public class Game extends JFrame
{
	private Model model;
	private View view;
	private Controller controller;
	static int WINDOW_HEIGHT = 800;
	static int WINDOW_WIDTH = 800;

	public Game()
	{
		model = new Model();
		controller = new Controller(model);
		view = new View(controller, model);
		this.setTitle("Assignment 5 - Mrs.Pacman by Lizzie Howell");
		this.setSize(WINDOW_WIDTH,WINDOW_HEIGHT);
		this.setFocusable(true);
		this.getContentPane().add(view);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		view.addMouseListener(controller);
		this.addKeyListener(controller);
		model.load();
		controller.setView(view);
	}

	public static void main(String[] args)
	{
		Game g = new Game();
		g.run();
	}

	public void run(){
		//show controls
		System.out.println("'c' or 'C' to remove all walls\n'e' or 'E' to enter edit mode\n'a' or 'A' to add walls\n'p' or 'P' to add pellets\n'f' or 'F' to add fruit\n'r' or 'R' to delete walls\n's' or 'S' to save map\n'l' or 'L' to load map\n'q','Q', or 'ESC' to quit");
		while(true){
			controller.update();
			model.update();
			view.repaint(); // This will indirectly call View.paintComponent
			Toolkit.getDefaultToolkit().sync(); // Updates screen

			// Go to sleep for 40 milliseconds
			try
			{
				Thread.sleep(40);
			} catch(Exception e) {
				e.printStackTrace();
				System.exit(1);
			}
		}
	}
}
