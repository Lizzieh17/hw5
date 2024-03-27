/*
 * Lizzie Howell
 * 3/27/2024
 * Assignment 5 - Polymorphism
 */

import java.awt.Graphics;
import java.awt.Image;

public class Fruit extends Sprite {
    static Image fruit_image = null;
    static int FRUIT_WIDTH = 25;
    static int FRUIT_HEIGHT = 30;
    private boolean eaten;
    private int ydir;
    private int speed;

    public Fruit() {
        x = 0;
        y = 0;
        w = FRUIT_WIDTH;
        h = FRUIT_HEIGHT;
        eaten = false;
        ydir = 1;
        speed = 8;
    }

    public Fruit(int x, int y) {
        this.x = x;
        this.y = y;
        this.w = FRUIT_WIDTH;
        this.h = FRUIT_HEIGHT;
        this.ydir = 1;
        this.speed = 8;
        if (fruit_image == null) {
            try {
                fruit_image = View.loadImage("sprite_images/fruit_images/fruit2.png");
            } catch (Exception e) {
                e.printStackTrace(System.err);
                System.exit(1);
            }
        }
    }

    Fruit(JSON ob) {
        h = (int) ob.getLong("h");
        y = (int) ob.getLong("y");
        w = (int) ob.getLong("w");
        x = (int) ob.getLong("x");

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
            ydir = 1;
        }
        if(speed == 0){
            speed = 8;
        }
        y += speed * ydir;
        if (eaten) {
            return false;
        }
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
        eaten = true;
    }
    public void changeYdir() {
        ydir *= -1;
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
        return ob;
    }

    public void unmarshal(JSON ob) {
        // System.out.println("unmarshal from Pellet called.");
        x = (int) ob.getLong("x");
        y = (int) ob.getLong("y");
        w = (int) ob.getLong("w");
        h = (int) ob.getLong("h");
    }
}
