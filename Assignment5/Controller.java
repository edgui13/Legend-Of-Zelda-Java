
//Name: Edgar Alcocer
//Date: March 31 2023
//Assignment Description: Build a map editor for a four-room video game with a link, boomerang, and pot sprites using polymorphism.
//This is the controller which takes care of all the key functionality
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

class Controller implements ActionListener, MouseListener, KeyListener {
	View view;
	Model model;

	boolean keyLeft;
	boolean keyRight;
	boolean keyUp;
	boolean keyDown;

	boolean editMode;
	boolean potMode;

	Controller(Model m) {
		model = m;
		Json loadFile = Json.load("map.json");
		model.unmarshal(loadFile);
		System.out.println("Map is loaded");
		editMode = false;
		potMode = false;
	}

	void setView(View v) {
		view = v;
	}

	public void actionPerformed(ActionEvent e) {
	}

	// Set destination for the image to go w/ the mouse
	public void mousePressed(MouseEvent e) {
		if (editMode) {
			int mousex = e.getX();
			int mousey = e.getY();
			int x = mousex - mousex % 50 + View.scrollPosX;
			int y = mousey - mousey % 50 + View.scrollPosY;
			if (potMode) {
				boolean potExists = false;
				for (Sprite sprite : model.sprites) {// loop though sprites to find a pot
					if (sprite.isPot()) {
						Pot pot = (Pot) sprite;
						if (pot.removePot(x, y)) {
							model.sprites.remove(pot);
							potExists = true;
							break;
						}
					}
				}
				if (!potExists) {
					model.addPot(x, y);
				}
			} else {
				// check if a tile already exists at the clicked position
				boolean tileExists = false;
				for (Sprite sprite : model.sprites) {// loop though sprites to find a tile
					if (sprite.isTile()) {
						Tile tile = (Tile) sprite;
						if (tile.removeTile(x, y)) {
							model.sprites.remove(tile);
							tileExists = true;
							break;
						}
					}
				}
				if (!tileExists) {
					model.addTile(x, y);
				}
			}
		}
	}

	public void mouseReleased(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mouseClicked(MouseEvent e) {
		if (e.getY() < 100) {
			System.out.println("break here");
		}
	}

	// Read the key that is pressed to move the image or close the window
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				if (!editMode) {
					keyLeft = true;
				}
				break;
			case KeyEvent.VK_RIGHT:
				if (!editMode) {
					keyRight = true;
				}
				break;
			case KeyEvent.VK_UP:
				if (!editMode) {
					keyUp = true;
				}
				break;
			case KeyEvent.VK_DOWN:
				if (!editMode) {
					keyDown = true;
				}
				break;
			case KeyEvent.VK_A:
				if (editMode) {
					System.out.println("Moving camera left");
					if (View.scrollPosX > 0) {
						model.link.x -= 700;
					}
				}
				break;
			case KeyEvent.VK_D:
				if (editMode) {
					System.out.println("Moving camera right");
					if (View.scrollPosX < View.MAX_SCROLL_X) {
						model.link.x += 700;
					}
				}
				break;
			case KeyEvent.VK_W:
				if (editMode) {
					System.out.println("Moving camera up");
					if (View.scrollPosY > 0) {
						model.link.y -= 500;
					}
				}
				break;
			case KeyEvent.VK_X:
				if (editMode) {
					System.out.println("Moving camera down");
					if (View.scrollPosY < View.MAX_SCROLL_Y) {
						model.link.y += 500;
					}
				}
				break;
		}
	}

	// Move the image after reading the key
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				if (!editMode) {
					keyLeft = false;
				}
				break;
			case KeyEvent.VK_RIGHT:
				if (!editMode) {
					keyRight = false;
				}
				break;
			case KeyEvent.VK_UP:
				if (!editMode) {
					keyUp = false;
				}
				break;
			case KeyEvent.VK_DOWN:
				if (!editMode) {
					keyDown = false;
				}
				break;
			case KeyEvent.VK_CONTROL:
				if (!editMode) {
					model.throwBoomerang();
				}
				break;
			case KeyEvent.VK_Q:
				System.exit(0); // will quit your program's execution if pressing q/Q.
				break;
			case KeyEvent.VK_ESCAPE:
				System.exit(0); // will quit your program's execution if pressing esc.
				break;
			case KeyEvent.VK_E:
				editMode = !editMode;
				// this will toggle my boolean variable
				view.printToScreenEdit = !view.printToScreenEdit;
				if (editMode) {
					System.out.println("Entering Edit Mode");
				} else {
					potMode = false;
					view.printToScreenPot = false;
					System.out.println("Exiting Edit Mode");
				}
				break;
			case KeyEvent.VK_P:
				if (editMode) {
					potMode = !potMode;
					// this will toggle my boolean variable
					view.printToScreenPot = !view.printToScreenPot;
					if (potMode) {
						System.out.println("Entering Pot Mode");
					} else {
						System.out.println("Exiting Pot Mode");
					}
				}
				break;
			case KeyEvent.VK_S:
				model.marshal().save("map.json");
				System.out.println("Map is Saved!");
				break;
			case KeyEvent.VK_L:
				Json loadFile = Json.load("map.json");
				model.unmarshal(loadFile);
				System.out.println("Map is loaded");
				break;
		}
	}

	public void keyTyped(KeyEvent e) {
	}

	void update() {
		View.scrollPosX = 700 * ((model.link.x + model.link.width / 2) / 700);
		View.scrollPosY = 500 * ((model.link.y + model.link.height / 2) / 500);
		if (keyRight) {
			model.link.prevX = model.link.x;
			model.link.linkDirection = 0;
			model.link.x += Link.speed;
		}
		if (keyLeft) {
			model.link.prevX = model.link.x;
			model.link.linkDirection = 1;
			model.link.x -= Link.speed;
		}
		if (keyDown) {
			model.link.prevY = model.link.y;
			model.link.linkDirection = 2;
			model.link.y += Link.speed;
		}
		if (keyUp) {
			model.link.prevY = model.link.y;
			model.link.linkDirection = 3;
			model.link.y -= Link.speed;
		}
		model.link.updateImageNum(keyUp, keyDown, keyLeft, keyRight);
	}
}
