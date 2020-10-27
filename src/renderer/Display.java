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
import renderer.shapes.ObjectGroup;
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
		
		PlayerSkin skin = new PlayerSkin("6bed63dbbeda461fa3a2d138fd8e5bf4");
		figure = skin.getFigure(10).mergeAll();
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
		
		/*
		//TEMP just an easier rotation control
		int xRot = 0, yRot = 0, zRot = 0, sensetivity = 10;
		
		switch(mouse.getButton()) {
		case 1:
			zRot = sensetivity;
			break;
		case 3:
			zRot = -sensetivity;
			break;
		case 4:
			xRot = -sensetivity;
			break;
		case 5:
			xRot = sensetivity;
			break;
		case 2:
		default:
			xRot = 0;
			yRot = 0;
			zRot = 0;
			System.out.println(figure.getChild("LEFT_ARM"));
		}

		figure.rotate(true, xRot, yRot, zRot);
		mouse.resetButton(); //*/
		
		//*
		if(mouse.getButton()==1) {
			initialMouseX = mouse.getMouseX();
			initialMouseY = mouse.getMouseY();
			mouse.resetButton();
			Mouse.pressed = true;
		} //*/
		
		if(Mouse.pressed) {
			int deltaX = mouse.getMouseX() - initialMouseX;
			int deltaY = mouse.getMouseY() - initialMouseY;
			
			initialMouseX = mouse.getMouseX();
			initialMouseY = mouse.getMouseY();
			figure.rotate(true, -deltaY/2.0, 0, -deltaX/2.0);
		} else {
			initialMouseX = -1;
			initialMouseY = -1;
		}
	}

}
