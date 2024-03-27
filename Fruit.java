/*
 * Lizzie Howell
 * 3/27/2024
 * Assignment 5 - Polymorphism
 */


//Fruit class, uses strawberry image and moves in the y direction by bouncing off walls
import java.awt.Graphics;
import java.awt.Image;

public class Fruit extends Sprite {
    static Image fruit_image = null;
    static int FRUIT_WIDTH = 25;
    static int FRUIT_HEIGHT = 30;
    private boolean eaten;
    private int ydir, xdir, dir;
    private int speed;

    public Fruit() {
        x = 0;
        y = 0;
        w = FRUIT_WIDTH;
        h = FRUIT_HEIGHT;
        eaten = false;
        ydir = 1;
        xdir = 1;
        dir = 1;
        speed = 8;
    }

    public Fruit(int x, int y, int dir) {
        this.x = x;
        this.y = y;
        this.w = FRUIT_WIDTH;
        this.h = FRUIT_HEIGHT;
        this.dir = dir;
        this.ydir = 1;
        this.xdir = 1;
        this.speed = 8;
    }

    //unmarshalling constructor
    Fruit(JSON ob) {
        h = (int) ob.getLong("h");
        y = (int) ob.getLong("y");
        w = (int) ob.getLong("w");
        x = (int) ob.getLong("x");
        dir = (int) ob.getLong("dir");

        if (fruit_image == null) {
            try {
                fruit_image = View.loadImage("sprite_images/fruit_images/fruit2.png");
            } catch (Exception e) {
                e.printStackTrace(System.err);
                System.exit(1);
            }
        }
    }

    public boolean update() {
        if(ydir == 0){
            //make sure ydir is set
            ydir = 1;
        }
        if(xdir == 0){
            //make sure xdir is set
            xdir = 1;
        }
        if(speed == 0){
            //make sure a speed is set
            speed = 8;
        }
        //update y for fruit
        if(dir == 1){
            y += speed * ydir;
        }
        if(dir == 0){
            x += speed * xdir;
        }
        //if marked as eaten, return false
        if (eaten) {
            return false;
        }
        //fruit is not marked as eaten, so it still exists
        return true;
    }

    public void draw(Graphics g, int scrollY) {
        g.drawImage(fruit_image, x, y - scrollY, w, h, null);
    }

    public boolean isFruit() {
        return true;
    }
    public boolean isMoving() {
        return true;
    }
    public void eatFruit() {
        //mark fruit as eaten
        eaten = true;
    }
    public void changedir() {
        //change direction to opposite
        if(dir == 1){
            ydir *= -1;
        }else{
            xdir *= -1;
        }
    }

    @Override
    public String toString() {
        return "Pellet (x,y) = (" + x + ", " + y + ")";
    }

    JSON marshal() {
        // System.out.println("marshal from Pellet called.");
        JSON ob = JSON.newObject();
        ob.add("x", x);
        ob.add("y", y);
        ob.add("w", w);
        ob.add("h", h);
        ob.add("dir", dir);
        return ob;
    }

    public void unmarshal(JSON ob) {
        // System.out.println("unmarshal from Pellet called.");
        x = (int) ob.getLong("x");
        y = (int) ob.getLong("y");
        w = (int) ob.getLong("w");
        h = (int) ob.getLong("h");
        dir = (int) ob.getLong("dir");
    }
}
