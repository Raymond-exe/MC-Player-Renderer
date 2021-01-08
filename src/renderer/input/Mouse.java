package renderer.input;

/** 
 * Monitors input for the user's mouse
 * @author MeanRollerCoding (Youtube)
 * @author https://github.com/Raymond-exe/
 * @version 1.0
 * @since 0.1
*/

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import renderer.Display;
import renderer.point.PointConverter;

public class Mouse implements MouseListener, MouseMotionListener, MouseWheelListener {

	public static int mouseX = -1;
	public static int mouseY = -1;
	public static int mouseB = -1;
	public static int mouseScroll = -1;
	public static boolean pressed = false;
	
	
	/////GETTER METHODS\\\\\
	public int getMouseX() {
		return mouseX;
	}
	
	public int getMouseY() {
		return mouseY;
	}
	
	public int getButton() {
		return mouseB;
	}
	
	public void resetButton() {
		mouseB = -1;
	}
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent event) {
		PointConverter.adjustZoom(-event.getUnitsToScroll()*0.1);
	}

	@Override
	public void mouseDragged(MouseEvent event) {
		Mouse.mouseX = event.getX();
		Mouse.mouseY = event.getY();
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent event) {
		Display.updateMousePos(event.getX(), event.getY());
		
	}

	@Override
	public void mousePressed(MouseEvent event) {
		mouseB = event.getButton();
		
		switch(mouseB) {
		case 1:
			Display.updateMousePos(event.getX(), event.getY());
			break;
		case 2:
			break;
		case 3:
			break;
		case 4:
			
//			PointConverter.CAM_DISTANCE+=10;
//			System.out.println(PointConverter.CAM_DISTANCE);
			
			PointConverter.increaseFovScale(50);
			System.out.println("New FOV: " + PointConverter.getFovScale());
			break;
		case 5:
			
//			PointConverter.CAM_DISTANCE-=10;
//			System.out.println(PointConverter.CAM_DISTANCE);
			
			PointConverter.decreaseFovScale(50);
			System.out.println("New FOV: " + PointConverter.getFovScale()); 
			break;
		}
	}
	
	//(for me)
	//LMB = 1
	//MID = 2
	//RMB = 3
	//PAGE BACK = 4
	//PAGE FORW = 5

	@Override
	public void mouseReleased(MouseEvent e) {
		pressed = false;
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
