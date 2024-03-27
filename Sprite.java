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
           // System.out.println("Sprite detected!");
            //System.out.println(toString());
            return true;
        }
        return false;
    }
    
    public boolean doestItCollide(Sprite sprite2){
			int sprite1Head = y;
			int sprite1Left = x;
			int sprite1Bottom = (y + h);
			int sprite1Right = (x + w);
			int sprite2Top = sprite2.getY();
			int sprite2Left = sprite2.getX();
			int sprite2Bottom = (sprite2.getY() + sprite2.getH());
			int sprite2Right = (sprite2.getX() + sprite2.getW());
            boolean colliding = false;

			if ((((sprite1Head < sprite2Top) && (sprite1Bottom > sprite2Bottom)) || ((sprite1Bottom > sprite2Top) && (sprite1Bottom < sprite2Bottom)))
					&& ((sprite1Right > sprite2Left) && (sprite1Left < sprite2Right))) {
                colliding = true;
			}
			if ((((sprite1Left > sprite2Left) && (sprite1Left < sprite2Right)) || ((sprite1Right > sprite2Left) && (sprite1Right < sprite2Right)))
					&& ((sprite1Bottom > sprite2Top) && (sprite1Head < sprite2Bottom))) {
                colliding = true;
            }
        return colliding;
    }

    public boolean isMoving(){
        return false;
    }
    public boolean isWall(){
        return false;
    }
    public boolean isPellet(){
        return false;
    }
    public boolean isFruit(){
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
    abstract boolean update();
    abstract JSON marshal();
}
