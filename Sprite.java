/*
 * Lizzie Howell
 * 3/27/2024
 * Assignment 5 - Polymorphism
 */

//our abstract sprite class that fruit, pellet, pacman, and wall extend
import java.awt.Graphics;

abstract class Sprite {
    protected int x, y, w, h;

    public void shouldIWrap() {
        if (x >= 775) {
            x = 4;
        } else if (x <= 4) {
            x = 775;
        }
    }

    public boolean spriteClicked(int mouseX, int mouseY) {
        // returns true if the mouse's x and y are where a sprite is
        if ((mouseX > x && mouseX < (x + w)) && (mouseY > y && mouseY < (y + h))) {
            return true;
        }
        return false;
    }

    public boolean doestItCollide(Sprite sprite2) {
        // collision detection between the sprite calling the method and the sprite
        // passed as a parameter. Also updates the colliding variable for the return value
        int sprite1Head = y;
        int sprite1Left = x;
        int sprite1Bottom = (y + h);
        int sprite1Right = (x + w);
        int sprite2Top = sprite2.getY();
        int sprite2Left = sprite2.getX();
        int sprite2Bottom = (sprite2.getY() + sprite2.getH());
        int sprite2Right = (sprite2.getX() + sprite2.getW());
        boolean colliding = false;

        if ((((sprite1Head < sprite2Top) && (sprite1Bottom > sprite2Bottom))
                || ((sprite1Bottom > sprite2Top) && (sprite1Bottom < sprite2Bottom)))
                && ((sprite1Right > sprite2Left) && (sprite1Left < sprite2Right))) {
            colliding = true;
        }
        if ((((sprite1Left > sprite2Left) && (sprite1Left < sprite2Right))
                || ((sprite1Right > sprite2Left) && (sprite1Right < sprite2Right)))
                && ((sprite1Bottom > sprite2Top) && (sprite1Head < sprite2Bottom))) {
            colliding = true;
        }
        return colliding;
    }

    public boolean isMoving() {
        return false;
    }

    public boolean isWall() {
        return false;
    }

    public boolean isPellet() {
        return false;
    }

    public boolean isFruit() {
        return false;
    }

    public boolean isPac() {
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

    //abstract methods each class that extends sprite should have
    abstract void draw(Graphics g, int scrollY);

    abstract boolean update();

    abstract JSON marshal();
}
