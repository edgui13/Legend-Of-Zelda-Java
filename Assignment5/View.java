
//Name: Edgar Alcocer
//Date: March 31 2023
//Assignment Description: Build a map editor for a four-room video game with a link, boomerang, and pot sprites using polymorphism.
//This is the view which is the frame that is shown to the user it moves with in combianation with the controls.
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.Color;
import java.awt.Font;
import java.util.Iterator;

class View extends JPanel {
	Model model;
	static int scrollPosX;
	static int scrollPosY;
	static int MAX_SCROLL_X = 700;
	static int MAX_SCROLL_Y = 500;
	boolean printToScreenEdit;
	boolean printToScreenPot;

	// View Constructor
	View(Controller c, Model m) {
		printToScreenEdit = false;
		printToScreenPot = false;
		this.model = m;
		c.setView(this);
	}

	public static BufferedImage loadImage(String filename) {
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File(filename));// loads the image into memor itdoesnt draw it
			// System.out.println(filename + " loaded");
		} catch (Exception e) {
			e.printStackTrace(System.err);
			System.out.println("Couldn't find " + filename);
			System.exit(1);
		}
		return image;
	}

	// draw the Tiles
	public void paintComponent(Graphics g) { // this will draw the images form whats in the memory
		// Colors in the screen for the background
		g.setColor(new Color(128, 255, 255));
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		// using iterator for drawing the tiles
		for (Iterator<Sprite> spriteIterator = model.sprites.iterator(); spriteIterator.hasNext();) {
			Sprite s = spriteIterator.next();
			s.draw(g);
		}
		if (printToScreenEdit) {// This will draw the edit mode condition onto the screen
			g.setColor(new Color(255, 165, 0));
			g.setFont(new Font("default", Font.BOLD, 20));
			g.drawString("Edit Mode", 575, 450);
		}
		if (printToScreenPot) {// This will draw the pot mode condition onto the screen
			g.setColor(new Color(255, 165, 0));
			g.setFont(new Font("default", Font.BOLD, 20));
			g.drawString("Pot Mode", 575, 400);
		}
	}
}