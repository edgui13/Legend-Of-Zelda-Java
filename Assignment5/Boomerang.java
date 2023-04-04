
//Name: Edgar Alcocer
//Date: March 31 2023
//Assignment Description: Build a map editor for a four-room video game with a link, boomerang, and pot sprites using polymorphism.
//This is the boomerang class that is being used to take care of the boomerang different direction load.

import java.awt.Graphics;
import java.awt.image.BufferedImage;

class Boomerang extends Sprite {
    int speed = 8;
    int xDirection;
    int yDirection;
    BufferedImage boomImg;
    int boomerangXlinkDir;

    public Boomerang() {
        width = 8;
        height = 12;
        active = true;
        loadBoomerang();
    }

    void loadBoomerang() {
        if (boomImg == null && boomerangXlinkDir == 0) {
            boomImg = View.loadImage("images/boomerang3.png");
        }
        if (boomImg != null && boomerangXlinkDir == 1) {
            boomImg = View.loadImage("images/boomerang1.png");
        }
        if (boomImg != null && boomerangXlinkDir == 2) {
            boomImg = View.loadImage("images/boomerang4.png");
        }
        if (boomImg != null && boomerangXlinkDir == 3) {
            boomImg = View.loadImage("images/boomerang2.png");
        }
    }

    @Override
    boolean isBoomerang() {
        return true;
    }

    @Override
    public String toString() {
        return "Boomerang (x,y): (" + x + ", " + y + ") width: " + width + ", height: " + height;

    }

    void draw(Graphics g) {
        g.drawImage(boomImg, x - View.scrollPosX, y - View.scrollPosY, null);
    }

    boolean update() {
        x = x + (xDirection * speed);
        y = y + (yDirection * speed);
        return false;
    }

    // marshaling
    Json marshal() {
        Json ob = Json.newObject();
        return ob;
    }

}
