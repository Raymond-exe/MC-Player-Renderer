package renderer.shapes;


public interface Groupable {

	boolean addToGroup(ObjectGroup group);
	
	ObjectGroup getParent();

	void move(double deltaX, double deltaY, double deltaZ);
	
	void setLocation(double newX, double newY, double newZ);
	
	void resetLocation();
	
	void rotate(boolean CW, double xRotation, double yRotation, double zRotation);
	
	void setRotation(double xRotation, double yRotation, double zRotation);
	
	void resetRotation();
	
	double getXCoordinate();
	
	double getYCoordinate();
	
	double getZCoordinate();
	
}
