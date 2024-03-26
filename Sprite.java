/*
 * Lizzie Howell
 * 3/27/2024
 * Assignment 5 - Polymorphism
 */

import java.awt.Graphics;

abstract class Sprite {
    protected int x, y, w, h;


    public boolean spriteClicked(int mouseX, int mouseY) {
        if ((mouseX > x && mouseX < (x + w)) && (mouseY > y && mouseY < (y + h))) {
            System.out.println("Sprite detected!");
            System.out.println(toString());
            return true;
        }
        return false;
    }

    public boolean isWall(){
        return false;
    }
    public boolean isPac(){
        return false;
    }
    
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getW() {
        return w;
    }

    public int getH() {
        return h;
    }

    abstract void draw(Graphics g, int scrollY);
    abstract void update();
    abstract JSON marshal();
}
