
//Name: Edgar Alcocer
//Date: March 31 2023
//Assignment Description: Build a map editor for a four-room video game with a link, boomerang, and pot sprites using polymorphism.
//this is the link class that draw itself and get out of tile if neccessary
import java.awt.Graphics;
import java.awt.image.BufferedImage;

class Link extends Sprite {
    final static double speed = 7;
    int linkDirection = 2;// link begins by looking down so if he starts to shoot it will be from the
    // bottom
    BufferedImage[] linkImages;
    final int NUM_IMAGES = 50;
    final int MAX_IMAGES = 13;
    int currentImage;
    int currentImageTicker = 0;

    public Link(int x, int y) {
        this.x = x;
        this.y = y;
        prevX = x;
        prevY = y;
        width = 50;
        height = 50;
        if (linkImages == null) {
            linkImages = new BufferedImage[50];
            for (int i = 0; i < NUM_IMAGES; i++) {
                linkImages[i] = View.loadImage("images/link" + (i + 1) + ".png");
            }
            currentImage = 0;
        }
    }

    public int getLinkDir() {
        return linkDirection;
    }

    public void updateImageNum(boolean keyUp, boolean keyDown, boolean keyLeft, boolean keyRight) {
        boolean keyy = keyUp != keyDown;
        boolean keyx = keyRight != keyLeft;
        if (!keyy && !keyx)
            return;
        currentImageTicker++;
        if (keyx) {
            if (keyRight) {
                currentImageTicker %= 6;
                currentImage = currentImageTicker + 30;
            } else {
                currentImageTicker %= 12;
                currentImage = currentImageTicker + 13;
            }
        } else {
            if (keyDown) {
                currentImageTicker %= 12;
                currentImage = currentImageTicker + 0;
            } else {
                currentImageTicker %= 11;
                currentImage = currentImageTicker + 39;
            }
        }
    }

    public void getOutOfTile(Sprite tile) {
        int differenceX = 0;
        int differenceY = 0;
        if (this.x + width > tile.x && this.prevX + this.width <= tile.x) { // right of link
            // System.out.println("Colliding right");
            differenceX = (tile.x - width) - x;
        } else if (this.x < tile.x + tile.width && this.prevX >= tile.x + tile.width) { // left of link
            // System.out.println("Colliding left");
            differenceX = (tile.x + tile.width) - x;
        }
        x += differenceX;
        if (this.y < tile.y + tile.height && this.prevY >= tile.y + tile.height) { // top of link
            // System.out.println("Colliding Top");
            differenceY = (tile.y + tile.height) - y;
        } else if (this.y + height > tile.y && this.prevY + this.height <= tile.y) { // bottom of link
            // System.out.println("Colliding Bottom");
            differenceY = (tile.y - height) - y;
        }
        y += differenceY;
    }

    @Override
    boolean isLink() {
        return true;
    }

    @Override
    public String toString() {
        return "Link (x,y): (" + x + ", " + y + ") width: " + width + ", height: " + height;
    }

    void draw(Graphics g) {
        g.drawImage(linkImages[currentImage], x - View.scrollPosX, y - View.scrollPosY, width, height, null);
    }

    boolean update() {
        return false;
    }

    Json marshal() {
        Json ob = Json.newObject();
        ob.add("link_x", x);
        ob.add("link_y", y);
        return ob;
    }
}
