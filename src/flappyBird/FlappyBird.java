package flappyBird;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.Timer;

// Implements ActionListener for events service
public class FlappyBird implements ActionListener {

	public static FlappyBird flappyBird;	// instance of the game
	
	public final int WIDTH = 1000, HEIGHT = 800;	// dimensions of the window
	
	public Renderer renderer;
	
	public Rectangle bird;	// position and size of a bird - player
	
	public int ticks, yMotion;
	
	public ArrayList<Rectangle> columns; // list of the obstacles - columns
	
	public Random rand;
	
	public FlappyBird() {
		JFrame jframe = new JFrame();
		Timer timer = new Timer(20, this);
		
		renderer = new Renderer();
		rand = new Random();
		
		jframe.add(renderer);
		jframe.setSize(HEIGHT, HEIGHT);
		jframe.setTitle("Flappy Bird");
		jframe.setVisible(true);
		jframe.setResizable(false);
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		bird = new Rectangle(WIDTH / 2 - 10, HEIGHT / 2 - 10, 20, 20);
		columns = new ArrayList<Rectangle>();
		
		addColumn(true);
		addColumn(true);
		addColumn(true);
		addColumn(true);
		
		timer.start();
	}
	
	public void addColumn(boolean start) {
		int space = 300;
		int width = 100;
		int height = 50 + rand.nextInt(300);	// max height=50+0=50/min height=50+299=349
		
		if (start) {
			// upper column
			columns.add(new Rectangle(WIDTH + width + columns.size() * 300, HEIGHT - height - 120, width, height));
			// bottom column
			columns.add(new Rectangle(WIDTH + width + (columns.size() - 1) * 300, 0, width, HEIGHT - height - space));
		}
		else {
			columns.add(new Rectangle(columns.get(columns.size() - 1).x + 600, HEIGHT - height - 120, width, height));
			columns.add(new Rectangle(columns.get(columns.size() - 1).x, 0, width, HEIGHT - height - space));
		}
	}
	
	// paint column
	public void paintColumn(Graphics g, Rectangle column) {
		g.setColor(Color.GREEN.darker());
		g.fillRect(column.x, column.y, column.width, column.height);
	}
	
	// rendering graphics
	@Override
	public void actionPerformed(ActionEvent e) {
		int speed = 10;
		ticks++;
		
		// columns are getting closer
		for (int i = 0; i < columns.size(); ++i) {
			Rectangle column = columns.get(i);
			column.x -= speed;
		}
		
		if (ticks % 2 == 0 && yMotion < 15) {
			yMotion += 2;
		}
		
		// remove columns when they are on position.x < 0
		for (int i = 0; i < columns.size(); ++i) {
			Rectangle column = columns.get(i);
			
			if (column.x + column.width < 0) {
				columns.remove(column);
				
				// if it's upper column
				if (column.y == 0)
					addColumn(false);
			}
		}
			
		bird.y += yMotion;
		renderer.repaint();
	}
	
	public void repaint(Graphics g) {
	
		// set background color and position and size
		g.setColor(Color.CYAN);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		// set surface color and position and size
		g.setColor(Color.ORANGE);
		g.fillRect(0, HEIGHT - 120, WIDTH, 150);
		
		// set upper surface color and position and size
		g.setColor(Color.GREEN);
		g.fillRect(0, HEIGHT - 120, WIDTH, 20);
		
		// set bird color and get position and size
		g.setColor(Color.RED);
		g.fillRect(bird.x, bird.y, bird.width, bird.height);
	
		// paint every column
		for (Rectangle column : columns) {
			paintColumn(g, column);
		}
	
	}
	
	public static void main(String[] args) {
		
		flappyBird = new FlappyBird();
		
	}
	
}
