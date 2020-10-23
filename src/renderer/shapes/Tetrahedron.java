package renderer.shapes;

/** 
 * Represents a collection of Polygons
 * @author MeanRollerCoding (Youtube)
 * @author https://github.com/Raymond-exe/
 * @version 1.0
 * @since 0.1
*/

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Tetrahedron implements Groupable {
	
	public double xRotation; //rotation stored in degrees, to reset if needed
	public double yRotation; //rotation stored in degrees, to reset if needed
	public double zRotation; //rotation stored in degrees, to reset if needed
	
	private List<Polygon3d> polygons;
	private ObjectGroup parent;
	
	public Tetrahedron(Polygon3d... polygons) {
		this.polygons = new ArrayList<>();		
		
		for(Polygon3d poly : polygons) {
			this.polygons.add(poly);
		}

		xRotation = 0;
		yRotation = 0;
		zRotation = 0;
		
		parent = null;
	}
	
	public Tetrahedron(List<Polygon3d> polygons) {
		this.polygons = new ArrayList<>();
		this.polygons.addAll(polygons);
		
		
		xRotation = 0;
		yRotation = 0;
		zRotation = 0;
		
		parent = null;
	}

	public void render(Graphics g) {
		sortPolygons();
		for(Polygon3d poly : polygons) {
			poly.render(g);
		}
	}
	
	public void liteRender(Graphics g, int num) {
		sortPolygons();
		
		if(num > polygons.size())
			num = polygons.size();
		
		for(int i = polygons.size()-num; i < polygons.size(); i++) {
			polygons.get(i).render(g);
		}
	}
	
	public void rotate(boolean CW, double xRotation, double yRotation, double zRotation) {
		this.xRotation+=xRotation;
		this.yRotation+=yRotation;
		this.zRotation+=zRotation;
		
		for(Polygon3d poly : polygons) {
			poly.rotate(CW, xRotation, yRotation, zRotation);
		}
		sortPolygons();
	}

	@Override
	public void setRotation(double xRotation, double yRotation, double zRotation) {
		double deltaX = xRotation-this.xRotation;
		double deltaY = yRotation-this.yRotation;
		double deltaZ = zRotation-this.zRotation;
		
		rotate(true, deltaX, deltaY, deltaZ);
	}

	@Override
	public void resetRotation() {
		rotate(false, xRotation, yRotation, zRotation);
		xRotation = 0;
		yRotation = 0;
		zRotation = 0;
	}
	
	@Override
	public void move(double deltaX, double deltaY, double deltaZ) {
		for(Polygon3d poly : polygons) {
			poly.move(deltaX, deltaY, deltaZ);
		}
	}

	@Override
	public void setLocation(double newX, double newY, double newZ) {
		double deltaX = newX-getXCoordinate();
		double deltaY = newY-getYCoordinate();
		double deltaZ = newZ-getZCoordinate();
		
		move(deltaX, deltaY, deltaZ);
	}
	
	@Override
	public void resetLocation() {
		move(-getXCoordinate(), -getYCoordinate(), -getZCoordinate());
	}

	public double getXCoordinate() {
		int xSum = 0;
		for(Polygon3d poly : polygons) {
			xSum+=poly.getXAverage();
		}

		//assign x, y, and z to the average of the polygons' averages
		return xSum / polygons.size();
	}

	public double getYCoordinate() {
		int ySum = 0;
		for(Polygon3d poly : polygons) {
			ySum+=poly.getYAverage();
		}

		//assign x, y, and z to the average of the polygons' averages
		return ySum / polygons.size();
	}

	public double getZCoordinate() {
		int zSum = 0;
		for(Polygon3d poly : polygons) {
			zSum+=poly.getZAverage();
		}

		//assign x, y, and z to the average of the polygons' averages
		return zSum / polygons.size();
	}
	
	private void sortPolygons() {
		Collections.sort(polygons, new PolygonSorter());
	}

	@Override
	public boolean addToGroup(ObjectGroup group) {
		if(parent==null) {
			group.add(this);
			parent = group;
			return true;
		}
		return false;
	}

	@Override
	public ObjectGroup getParent() {
		return parent;
	}
	
	@Override
	public void setParent(ObjectGroup objectGroup) {
		parent = objectGroup;
	}
	
}
