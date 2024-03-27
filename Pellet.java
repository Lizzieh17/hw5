/*
 * Lizzie Howell
 * 3/27/2024
 * Assignment 5 - Polymorphism
 */

//pellet class: our pellet extends the sprite class; pellet draws itself, updates if it is eaten or not, and marshalls itself
import java.awt.Graphics;
import java.awt.Image;

public class Pellet extends Sprite {
    static Image pellet_image = null;
    static int PELLET_WIDTH = 40;
    static int PELLET_HEIGHT = 25;
    private boolean eaten;

    public Pellet() {
        x = 0;
        y = 0;
        w = PELLET_WIDTH;
        h = PELLET_HEIGHT;
        eaten = false;
    }

    public Pellet(int x, int y) {
        this.x = x;
        this.y = y;
        this.w = PELLET_WIDTH;
        this.h = PELLET_HEIGHT;
        if (pellet_image == null) {
            try {
                pellet_image = View.loadImage("sprite_images/pellet.png");
            } catch (Exception e) {
                e.printStackTrace(System.err);
                System.exit(1);
            }
        }
    }

    Pellet(JSON ob) {
        h = (int) ob.getLong("h");
        y = (int) ob.getLong("y");
        w = (int) ob.getLong("w");
        x = (int) ob.getLong("x");

        if (pellet_image == null) {
            try {
                pellet_image = View.loadImage("sprite_images/pellet.png");
            } catch (Exception e) {
                e.printStackTrace(System.err);
                System.exit(1);
            }
        }
    }

    public boolean update() {
        //if marked as eaten, then retunr false and get removed
        // if not marked (i.e. the pellet still exists) then return true
        if(eaten){
            return false;
        }
        return true;
    }

    public void draw(Graphics g, int scrollY) {
        g.drawImage(pellet_image, x, y - scrollY, w, h, null);
    }

    public void eatPellet(){
        eaten = true;
    }
    public boolean isPellet() {
        return true;
    }

    @Override
    public String toString() {
        return "Pellet (x,y) = (" + x + ", " + y + ")";
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
