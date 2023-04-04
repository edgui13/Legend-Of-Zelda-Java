
//Name: Edgar Alcocer
//Date: March 31 2023
//Assignment Description: Build a map editor for a four-room video game with a link, boomerang, and pot sprites using polymorphism.
//This is the tile class that is being used to map out the video game model.
import java.awt.Graphics;
import java.awt.image.BufferedImage;

class Tile extends Sprite {
    static BufferedImage tileImg = View.loadImage("images/tile.jpg");

    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
        width = 50;
        height = 50;
    }

    public boolean removeTile(int x, int y) {
        if (x >= this.x && x <= this.x + this.width / 2 && y >= this.y && y <= this.y + this.height / 2) {
            // System.out.println("Removed Tile");
            return true;
        } else {
            return false;
        }
    }

    void loadTile() {
        if (tileImg == null) {
            tileImg = View.loadImage("images/tile.jpg");
        }
    }

    @Override
    boolean isTile() {
        return true;
    }

    @Override
    public String toString() {
        return "Tile (x,y): (" + x + ", " + y + ") width: " + width + ", height: " + height;
    }

    void draw(Graphics g) {
        g.drawImage(tileImg, x - View.scrollPosX, y - View.scrollPosY, null);
    }

    boolean update() {
        return false;
    }

    // marshaling
    Json marshal() {
        Json ob = Json.newObject();
        ob.add("tile_x", x);
        ob.add("tile_y", y);
        return ob;
    }

    // unmarshaling
    public Tile(Json ob) {
        // System.out.println("Tiles is being called");
        x = (int) ob.getLong("tile_x");
        y = (int) ob.getLong("tile_y");
        width = 50;
        height = 50;
    }
}
