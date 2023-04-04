
//Name: Edgar Alcocer
//Date: March 31 2023
//Assignment Description: Build a map editor for a four-room video game with a link, boomerang, and pot sprites using polymorphism.
//This is the model/world view of the video game.
import java.util.ArrayList;
// import java.util.Iterator;

class Model {
    // int numCollisions = 0;
    Link link;
    Boomerang boomerang;
    ArrayList<Sprite> sprites;

    Model() {
        link = new Link(150, 150);
        sprites = new ArrayList<Sprite>();
        sprites.add(link);
        boomerang = new Boomerang();
        sprites.add(boomerang);
    }

    public void update() {
        for (int i = 0; i < sprites.size(); i++) {
            Sprite s1 = sprites.get(i);
            boolean shouldRemove = s1.update();
            for (int j = 0; j < sprites.size(); j++) {
                Sprite s2 = sprites.get(j);
                if (s1.collisionDetection(s2)) {
                    if (s1.isLink() && s2.isTile()) {
                        link.getOutOfTile(s2);
                    }
                    if (s1.isLink() && s2.isPot()) {
                        Pot pot = (Pot) s2;
                        if (!pot.isBroken) {
                            if (s1.x + s1.width > s2.x && s1.prevX + s1.width <= s2.x) {
                                // right side ofLink
                                pot.xDirection = 1;
                                pot.yDirection = 0;
                            } else if (s1.x < s2.x + s2.width && s1.prevX > s2.x + s2.width) {
                                // leftside ofLink
                                pot.xDirection = -1;
                                pot.yDirection = 0;
                            } else if (s1.y < s2.y + s2.height && s1.prevY >= s2.y + s2.height) {
                                // top of Link
                                pot.xDirection = 0;
                                pot.yDirection = -1;
                            } else if (s1.y + s1.height > s2.y && s1.prevY + s1.height <= s2.y) {
                                // bottom of Link
                                pot.xDirection = 0;
                                pot.yDirection = 1;
                            } else {
                                pot.xDirection = -1;
                                pot.yDirection = 0;
                            }
                        }
                    }
                    if (s1.isBoomerang() && s2.isTile()) {
                        shouldRemove = true;
                    }
                    if (s1.isBoomerang() && s2.isPot()) {
                        ((Pot) s2).breakPot();
                        shouldRemove = true;
                    }
                    if (s1.isPot() && s2.isTile()) {
                        Pot pot = (Pot) s1;
                        pot.breakPot();
                        if (pot.update()) {
                            shouldRemove = true;
                        }
                    }
                }
            }
            // Remove the sprites that should be removed form the ArrayList
            if (shouldRemove) {
                sprites.remove(s1);
            }
        }
    }

    public void throwBoomerang() {
        Boomerang b = new Boomerang();
        int lDirection = link.getLinkDir();
        if (lDirection == 0) {// right
            b.xDirection = 1;
            b.yDirection = 0;
            b.boomerangXlinkDir = 0;
            b.x = link.x + link.width;
            b.y = link.y + (link.height / 2);
            b.loadBoomerang();
            b.update();
        } else if (lDirection == 1) {// left
            b.xDirection = -1;
            b.yDirection = 0;
            b.boomerangXlinkDir = 1;
            b.x = link.x;
            b.y = link.y + (link.height / 2);
            b.loadBoomerang();
            b.update();
        } else if (lDirection == 2) {// down
            b.xDirection = 0;
            b.yDirection = 1;
            b.boomerangXlinkDir = 2;
            b.x = link.x + (link.width / 2);
            b.y = link.y + link.height;
            b.loadBoomerang();
            b.update();
        } else if (lDirection == 3) {// up
            b.xDirection = 0;
            b.yDirection = -1;
            b.boomerangXlinkDir = 3;
            b.x = link.x + (link.width / 2);
            b.y = link.y;
            b.loadBoomerang();
            b.update();
        }
        // System.out.println("Shooting the Boomerang");
        sprites.add(b);
    }

    // Add tile function
    public void addTile(int x, int y) {
        Sprite tile = new Tile(x, y);
        sprites.add(tile);
    }

    // Add pot function
    public void addPot(int x, int y) {
        Sprite pot = new Pot(x, y);
        sprites.add(pot);
    }

    // ----------------------------------------
    // Marshalling interactions
    // ----------------------------------------
    // marshal the tiles
    Json marshal() {
        Json ob = Json.newObject();
        Json tmpList = Json.newList();
        ob.add("tiles", tmpList);
        Json tmpListPot = Json.newList();
        ob.add("pot", tmpListPot);
        for (int i = 0; i < sprites.size(); i++) {
            if (sprites.get(i).isTile()) {
                tmpList.add(sprites.get(i).marshal());
            }
            if (sprites.get(i).isPot()) {
                tmpListPot.add(sprites.get(i).marshal());
            }
        }
        return ob;
    }

    // unmarshal the tiles
    void unmarshal(Json ob) {
        sprites.clear();
        // sprites = new ArrayList<Sprite>();
        sprites.add(link);
        Json tmpList = ob.get("tiles");
        for (int i = 0; i < tmpList.size(); i++) {
            sprites.add(new Tile(tmpList.get(i)));
        }

        Json tmpListPot = ob.get("pot");
        for (int i = 0; i < tmpListPot.size(); i++) {
            sprites.add(new Pot(tmpListPot.get(i)));
        }
    }
}