package renderer.shapes;

import java.awt.Graphics;

/** 
 * An object that can be grouped into an ObjectGroup
 * @author https://github.com/Raymond-exe/
 * @version 1.0
 * @since 0.1
*/

public interface Groupable {

	boolean addToGroup(ObjectGroup group);
	
	ObjectGroup getParent();
	
	void setParent(ObjectGroup objectGroup);

	void move(double deltaX, double deltaY, double deltaZ);
	
	void setLocation(double newX, double newY, double newZ);
	
	void resetLocation();
	
	void rotate(boolean CW, double xRotation, double yRotation, double zRotation, Vector3d lightVector);
	
	void setRotation(double xRotation, double yRotation, double zRotation);
	
	void resetRotation();
	
	double getXCoordinate();
	
	double getYCoordinate();
	
	double getZCoordinate();
	
	void render(Graphics g);
	
}
