
//Name: Edgar Alcocer
//Date: March 31 2023
//Assignment Description: Build a map editor for a four-room video game with a link, boomerang, and pot sprites using polymorphism.
//this is the Sprite class that is focused on the ploymophism of sprites in the game.
import java.awt.image.BufferedImage;
import java.awt.Graphics;

abstract class Sprite {
    int x;
    int y;
    int width;
    int height;
    int prevX;
    int prevY;
    boolean active = false;
    BufferedImage image;

    abstract void draw(Graphics g);

    abstract boolean update();

    abstract Json marshal();

    public boolean collisionDetection(Sprite sprite) {
        if (this.x + this.width <= sprite.x) // right of collider
        {
            return false;
        } else if (this.x >= sprite.x + sprite.width) // Left of collider
        {
            return false;
        } else if (this.y + this.height <= sprite.y) // Bottom of collider
        {
            return false;
        } else if (this.y >= sprite.y + sprite.height) // Top of collider
        {
            return false;
        }
        return true;
    }

    boolean isTile() {
        return false;
    }

    boolean isLink() {
        return false;
    }

    boolean isPot() {
        return false;
    }

    boolean isBoomerang() {
        return false;
    }

    @Override
    public String toString() {
        return "Sprite: (" + x + ", " + y + ") width= " + width + ", height= " + height;
    }
}
