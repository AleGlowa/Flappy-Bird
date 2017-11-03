package flappyBird;

import javax.swing.JPanel;

import java.awt.Graphics;

// Extends JPanel for declarates window content
public class Renderer extends JPanel {

	private static final long serialVersionUID = 1L;
	
	// Create graphics
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		FlappyBird.flappyBird.repaint(g);	// paint graphics for particular instance of a game
	}

}
