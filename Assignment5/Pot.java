
//Name: Edgar Alcocer
//Date: March 31 2023
//Assignment Description: Build a map editor for a four-room video game with a link, boomerang, and pot sprites using polymorphism.
//This is the pot class that is being used to used as collision breakers for link and the boomerang.

import java.awt.Graphics;
import java.awt.image.BufferedImage;

class Pot extends Sprite {
    boolean isBroken;
    long brokenTimestamp;
    static BufferedImage potBrokenImg;
    static BufferedImage potImg;
    int xDirection;
    int yDirection;
    final static double speed = 9;

    public Pot(int x, int y) {
        this.x = x;
        this.y = y;
        width = 50;
        height = 50;
        isBroken = false;
        loadPot();
        loadBroken();
    }

    public boolean removePot(int x, int y) {
        if (x >= this.x && x <= this.x + this.width / 2 && y >= this.y && y <= this.y + this.height / 2) {
            // System.out.println("Removed Pot");
            return true;
        } else {
            return false;
        }
    }

    void breakPot() {
        if (!isBroken) {
            isBroken = true;
            brokenTimestamp = System.currentTimeMillis();
            loadBroken();
        }
    }

    void loadPot() {
        if (potImg == null) {
            potImg = View.loadImage("images/pot.png");
        }
        width = potImg.getWidth();
        height = potImg.getHeight();
    }

    void loadBroken() {
        if (potBrokenImg == null) {
            potBrokenImg = View.loadImage("images/pot_broken.png");
            if (potBrokenImg == null) {
                System.out.println("Error: Unable to load 'pot_broken.png'");
            }
        }
    }

    @Override
    boolean isPot() {
        return true;
    }

    @Override
    public String toString() {
        return "Pot (x,y): (" + x + ", " + y + ") width: " + width + ", height: " + height;
    }

    void draw(Graphics g) {
        if (!isBroken) {
            g.drawImage(potImg, x - View.scrollPosX, y - View.scrollPosY, null);
        } else {
            g.drawImage(potBrokenImg, x - View.scrollPosX, y - View.scrollPosY, null);
        }
    }

    boolean update() {
        if (isBroken) {
            if (System.currentTimeMillis() - brokenTimestamp >= 500) {
                return true;
            }
        } else if (!isBroken) {
            x = x + (int) (xDirection * speed);
            y = y + (int) (yDirection * speed);
        }
        return false;
    }

    // marshaling
    Json marshal() {
        Json ob = Json.newObject();
        ob.add("pot_x", x);
        ob.add("pot_y", y);
        return ob;
    }

    // unmarshal
    public Pot(Json ob) {
        x = (int) ob.getLong("pot_x");
        y = (int) ob.getLong("pot_y");
        loadPot();
    }
}
