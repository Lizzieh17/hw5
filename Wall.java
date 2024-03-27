/*
 * Lizzie Howell
 * 3/27/2024
 * Assignment 5 - Polymorphism
 */

import java.awt.Graphics;
import java.awt.Image;

public class Wall extends Sprite{
    static Image wall_image = null;

    public Wall() {
        x = 0;
        y = 0;
        w = 0;
        h = 0;
    }

    public Wall(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public boolean update() { 
        return true;
    }

    public void draw(Graphics g, int scrollY) {
        g.drawImage(wall_image, x, y - scrollY, w, h, null);
    }

    // unmarshaling contructor
    Wall(JSON ob) {
        h = (int) ob.getLong("h");
        y = (int) ob.getLong("y");
        w = (int) ob.getLong("w");
        x = (int) ob.getLong("x");

        if (wall_image == null) {
            try {
                wall_image = View.loadImage("sprite_images/wall.png");
            } catch (Exception e) {
                e.printStackTrace(System.err);
                System.exit(1);
            }
        }
    }

    public boolean isWall() {
        return true;
    }

    @Override
    public String toString() {
        return "Wall (x,y) = (" + x + ", " + y + "), w = " + w + ", h = " + h;
    }

    JSON marshal() {
        // System.out.println("marshal from Wall called.");
        JSON ob = JSON.newObject();
        ob.add("x", x);
        ob.add("y", y);
        ob.add("w", w);
        ob.add("h", h);
        return ob;
    }

    public void unmarshal(JSON ob) {
        // System.out.println("unmarshal from Wall called.");
        x = (int) ob.getLong("x");
        y = (int) ob.getLong("y");
        w = (int) ob.getLong("w");
        h = (int) ob.getLong("h");
    }
}
