/*
 * Lizzie Howell
 * 3/27/2024
 * Assignment 5 - Polymorphism
 */

//Pacman class: our pacman sprite with her image, movement, and ability to get out of walls
import java.awt.Graphics;
import java.awt.Image;

public class Pacman extends Sprite{
    private double speed;
    static Image[][] pacmanImages = null;
    static int PAC_WIDTH = 40;
    static int PAC_HEIGHT = 40;
    private Image currentImage;
    private int direction;
    private int frame;
    private int prevX, prevY;
    private int MAX_IMAGES = 3;
    private int MAX_DIRECTION = 4;

    public Pacman() {
        x = 400;
        y = 400;
        w = PAC_WIDTH;
        h = PAC_HEIGHT;
        speed = 5.00;
        direction = 0;
        frame = 0;
        prevX = 400;
        prevY = 400;
        pacmanImages = new Image[MAX_DIRECTION][MAX_IMAGES];

        if (currentImage == null) {
            try {
                // i = direction, z = image
                int count = 0;
                for (int d = 0; d < 4; d++) {
                    for (int z = 0; z < 3; z++) {
                        count++;
                        pacmanImages[d][z] = View.loadImage("sprite_images/pacman_images/pacman" + (count) + ".png");
                    }
                }
                currentImage = pacmanImages[0][0];
            } catch (Exception e) {
                e.printStackTrace(System.err);
                System.exit(1);
            }
        }
    }

    public Pacman(int x, int y) {
        this.x = x;
        this.y = y;
        w = PAC_WIDTH;
        h = PAC_HEIGHT;
        speed = 5;
    }

    public void draw(Graphics g, int scrollY) {
        g.drawImage(currentImage, x, y - scrollY, w, h, null);
    }
    public boolean update() {   
        //pacman always exists
        return true;
    }

    // unmarshaling contructor
    Pacman(JSON ob) {
        h = (int) ob.getLong("h");
        y = (int) ob.getLong("y");
        w = (int) ob.getLong("w");
        x = (int) ob.getLong("x");
    }

    public void savePac() {
        //save pac's x and y to her previous x and y
        prevX = x;
        prevY = y;
    }

    public void getOutOfWall() {
        //get out of wall by setting pac's x and y to her previous x and y
        x = prevX;
        y = prevY;
    }

    public void setImage(int d, int i) {
        //set image based on pacs direction and which frame to update to
        direction = d;
        frame = i;
        currentImage = pacmanImages[d][i];
    }

    public void animate(int d) {
        //animate pacman and update her frame and direction
        direction = d;
        frame++;
        if (frame >= MAX_IMAGES) {
            frame = 0;
        }
        setImage(direction, frame);
    }

    public void movePacRight() {
        //move pacman to the right by updating her x with her speed and set her previous x
        prevX = x;
        x += speed;
    }

    public void movePacLeft() {
        //move pacman to the left by updating her x with her speed and set her previous x
        prevX = x;
        x -= speed;
    }

    public void movePacUp() {
        //move pacman up by updating her y with her speed and set her previous y
        prevY = y;
        y -= speed;
    }

    public void movePacDown() {
        //move pacman down by updating her y with her speed and set her previous y
        prevY = y;
        y += speed;
    }

    public boolean isPac() {
        return true;
    }

    public boolean isMoving(){
        return true;
    }

    public double getPacSpeed() {
        return speed;
    }

    @Override
    public String toString() {
        return "Pacman (x,y) = (" + x + ", " + y + ")" + ", Previous Pacman (x,y) = (" + prevX + " , " + prevY + " )";
    }

    JSON marshal() {
        JSON ob = JSON.newObject();
        ob.add("x", x);
        ob.add("y", y);
        ob.add("w", w);
        ob.add("h", h);
        return ob;
    }

    public void unmarshal(JSON ob) {
        x = (int) ob.getLong("x");
        y = (int) ob.getLong("y");
        w = (int) ob.getLong("w");
        h = (int) ob.getLong("h");
    }
}
