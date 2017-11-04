package flappyBird;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.Timer;

// Implements ActionListener for events service, in addition MouseListener for mouse events
public class FlappyBird implements ActionListener, MouseListener {

	public static FlappyBird flappyBird;	// instance of the game
	
	public final int WIDTH = 1000, HEIGHT = 800;	// dimensions of the window
	
	public Renderer renderer;
	
	public Rectangle bird;	// position and size of a bird - player
	
	public ArrayList<Rectangle> columns; // list of the obstacles - columns
	
	public int ticks, yMotion;
	
	public boolean gameOver, started;
	
	public Random rand;
	
	public FlappyBird() {
		JFrame jframe = new JFrame();
		Timer timer = new Timer(20, this);
		
		renderer = new Renderer();
		rand = new Random();
		
		jframe.add(renderer);
		jframe.setSize(HEIGHT, HEIGHT);
		jframe.addMouseListener(this);	// add mouse listener to service a event
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
		// spawn infinity number of columns
		else {
			columns.add(new Rectangle(columns.get(columns.size() - 1).x + 600, HEIGHT - height - 120, width, height));
			columns.add(new Rectangle(columns.get(columns.size() - 1).x, 0, width, HEIGHT - height - space));
		}
	}
	
	// paint a column
	public void paintColumn(Graphics g, Rectangle column) {
		g.setColor(Color.GREEN.darker());
		g.fillRect(column.x, column.y, column.width, column.height);
	}
	
	// bird's jump
	public void jump() {
		if (gameOver) {
			gameOver = false;
		}
		
		if (!started) {
			started = true;
		}
	}
	
	// rendering graphics
	@Override
	public void actionPerformed(ActionEvent e) {
		int speed = 10;
		ticks++;
		
		// if a bird is alive
		if (started) {
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
					
					// if it's upper column add another columns
					if (column.y == 0)
						addColumn(false);
				}
			}
				
			bird.y += yMotion;
			
			// game over when a bird crash with a column
			for (Rectangle column : columns) {
				if (column.intersects(bird)) {
					gameOver = true;
					bird.x = column.x - bird.width;	// set bird positon.x before the column
				}
			}
			// game over when a bird fly over window or crash with a surface
			if (bird.y > HEIGHT - 120 || bird.y < 0) {
				gameOver = true;
			}
			
			// if game over then set bird position above upper surface
			if (gameOver) {
				bird.y = HEIGHT - 120 - bird.height;
			}
		}
		
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
		
		g.setColor(Color.WHITE);	// set string color
		g.setFont(new Font("Arial", 1, 100));	// set font(name, style, size)
		
		// if game is over then show game over string
		if (!gameOver)
			g.drawString("Click to start!", 75, HEIGHT / 2 - 20);
		
		// if game is over then show game over string
		if (gameOver)
			g.drawString("Game Over!", 100, HEIGHT / 2 - 20);
	
	}
	
	public static void main(String[] args) {
		
		flappyBird = new FlappyBird();
		
	}

	// when mouse has been clicked then jump()
	@Override
	public void mouseClicked(MouseEvent arg0) {
		jump();
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
