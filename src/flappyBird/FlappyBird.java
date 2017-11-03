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

public class FlappyBird implements ActionListener {

	public static FlappyBird flappyBird;
	
	public final int WIDTH = 800, HEIGHT = 800;
	
	public Renderer renderer;
	
	public Rectangle bird;
	
	public ArrayList<Rectangle> columns;
	
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
		int height = 50 + rand.nextInt(300);
		
		if (start) {
			columns.add(new Rectangle(WIDTH + width * columns.size() * 300, HEIGHT - height - 120, width, height));
			columns.add(new Rectangle(WIDTH + width + (columns.size() - 1) * 300, 0, width, HEIGHT - height - space));
		}
		else {
			columns.add(new Rectangle)
		}
	}
	
	public void paintColumn(Graphics g, Rectangle column) {
		g.setColor(Color.GREEN.darker());
		g.fillRect(column.x, column.y, column.width, column.height);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		renderer.repaint();
	}
	
	public void repaint(Graphics g) {
	
		g.setColor(Color.CYAN);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		g.setColor(Color.ORANGE);
		g.fillRect(0, HEIGHT - 120, WIDTH, 150);
		
		g.setColor(Color.GREEN);
		g.fillRect(0, HEIGHT - 120, WIDTH, 20);
		
		g.setColor(Color.RED);
		g.fillRect(bird.x, bird.y, bird.width, bird.height);
	}
	
	public static void main(String[] args) {
		
		flappyBird = new FlappyBird();
		
	}
	
}
