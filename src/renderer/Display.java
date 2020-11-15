package renderer;

/** 
 * A viewport for the 3D space
 * @author MeanRollerCoding (Youtube)
 * @author https://github.com/Raymond-exe/
 * @version 1.0
 * @since 0.1
*/

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

import playerSkin.PlayerSkin;
import renderer.input.Mouse;
import renderer.shapes.Tetrahedron;

public class Display extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;
	
	private static final int FRAMES_PER_SECOND = 60;
	
	private Thread thread;
	private JFrame jFrame;
	private static String title = "Minecraft Player Renderer";
	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720;
	private boolean running = false;
	private Mouse mouse;
	private static int initialMouseX;
	private static int initialMouseY;
	
	private static Tetrahedron figure;
	
	public Display() {
		this.jFrame = new JFrame();
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		
		mouse = new Mouse();
		
		this.addMouseListener(mouse);
		this.addMouseMotionListener(mouse);
		this.addMouseWheelListener(mouse);
	}
	
	public static void main(String[] args) {
		Display display = new Display();
		display.jFrame.setTitle(title);
		display.jFrame.add(display);
		display.jFrame.pack();
		display.jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		display.jFrame.setLocationRelativeTo(null);
		display.jFrame.setResizable(false);
		display.jFrame.setVisible(true);
		
		PlayerSkin skin = new PlayerSkin("bfe1135e0cbc41c482fecb4ea695231e");
		figure = skin.getFigure(10, 1).mergeAll();
		figure.resetLocation();
		//figure.addTetrahedron(Shapes.getFigure(50));
		//Shapes.fixFigureRotation(figure.getTetrahedrons());
		display.start();
	}
	
	public synchronized void start() {
		running = true;
		this.thread = new Thread(this, "Display");
		this.thread.start();
	}
	
	public synchronized void stop() {
		try {
			this.thread.join();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		final double ns = 1000000000.0 / FRAMES_PER_SECOND;
		double delta = 0;
		int frames = 0;
		
		while(running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta >= 1) {
				update();
				delta--;
				render();
				frames++;
			}
			
			//*
			if(System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				this.jFrame.setTitle(title + " | " + frames + " fps");
				frames = 0;
			} //*/
		}
	}
	
	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics2D g = (Graphics2D)bs.getDrawGraphics();
		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		figure.render(g);
		
		g.dispose();
		bs.show();
	}
	
	public static void updateMousePos(int x, int y) {
		initialMouseX = x;
		initialMouseY = y;
	}
	
	private void update() {
		
		if(mouse.getButton()==1) {
			initialMouseX = mouse.getMouseX();
			initialMouseY = mouse.getMouseY();
			mouse.resetButton();
			Mouse.pressed = true;
		}
		
		if(Mouse.pressed) {
			int deltaX = mouse.getMouseX() - initialMouseX;
			int deltaY = mouse.getMouseY() - initialMouseY;
			
			initialMouseX = mouse.getMouseX();
			initialMouseY = mouse.getMouseY();
			figure.rotate(true, -deltaY/2.0, 0, -deltaX/2.0);

			/*
			System.out.println(figure.getChild("HEAD"));
			System.out.println(figure.getChild("CHEST"));
			System.out.println(figure.getChild("LEFT_ARM"));
			System.out.println(figure.getChild("RIGHT_ARM"));
			System.out.println(figure.getChild("LEFT_LEG"));
			System.out.println(figure.getChild("RIGHT_LEG"));
			*/
		} else {
			initialMouseX = -1;
			initialMouseY = -1;
		}
	}

}
