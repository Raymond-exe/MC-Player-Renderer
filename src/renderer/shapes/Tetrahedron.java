package renderer.shapes;

import java.awt.Color;

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

import renderer.PointLight;
import renderer.point.Point3d;

public class Tetrahedron implements Groupable {
	
	public double xRotation; //rotation stored in degrees, to reset if needed
	public double yRotation; //rotation stored in degrees, to reset if needed
	public double zRotation; //rotation stored in degrees, to reset if needed
	
	private List<Polygon3d> polygons;
	private ObjectGroup parent;
	
	public Tetrahedron(Polygon3d... polygons) {
		this();
		for(Polygon3d poly : polygons) {
			this.polygons.add(poly);
		}
	}
	
	public Tetrahedron() {
		this.polygons = new ArrayList<>();
		
		xRotation = 0;
		yRotation = 0;
		zRotation = 0;
		
		parent = null;
	}
	
	public Tetrahedron(List<Polygon3d> polygons) {
		this();
		for(Polygon3d poly : polygons) {
			this.polygons.add(poly.copy());
		}
	}

	@Override
	public void render(Graphics g) {
		sortPolygons();
		for(Polygon3d poly : polygons) {
			poly.render(g);
		}
	}
	
	public void renderLighting(Graphics g, int numSubdivisions, ArrayList<PointLight> lightingSources) {
		
		//TODO subdivide so that this thing is more even
		
		Tetrahedron lightingTetra = this.subdivide(numSubdivisions);
		for(Polygon3d poly : lightingTetra.polygons) {
			double shading = lightingSources.get(0).getIntensity(poly.getAverage());
			
			for(PointLight light : lightingSources) {
				if(shading > light.getIntensity(poly.getAverage())) {
					shading = light.getIntensity(poly.getAverage());
				}
			}
			
			Color oldPolyColor = poly.getColor();
			int newRed  = Math.max(0, Math.min(255, (int)(oldPolyColor.getRed() - oldPolyColor.getRed()*shading)));
			int newGreen= Math.max(0, Math.min(255, (int)(oldPolyColor.getGreen() - oldPolyColor.getGreen()*shading)));
			int newBlue = Math.max(0, Math.min(255, (int)(oldPolyColor.getBlue() - oldPolyColor.getBlue()*shading)));
			poly.setColor(new Color(newRed, newGreen, newBlue, oldPolyColor.getAlpha()));
		}
		
		lightingTetra.render(g);
	}
	
	public void liteRender(Graphics g, int num) {
		sortPolygons();
		
		if(num > polygons.size())
			num = polygons.size();
		
		for(int i = polygons.size()-num; i < polygons.size(); i++) {
			polygons.get(i).render(g);
		}
	}
	
	public void rotate(boolean CW, double xRotation, double yRotation, double zRotation, Vector3d lightVector) {
		this.xRotation+=xRotation;
		this.yRotation+=yRotation;
		this.zRotation+=zRotation;
		
		for(Polygon3d poly : polygons) {
			poly.rotate(CW, xRotation, yRotation, zRotation, lightVector);
		}
		sortPolygons();
	}

	@Override
	public void setRotation(double xRotation, double yRotation, double zRotation) {
		double deltaX = xRotation-this.xRotation;
		double deltaY = yRotation-this.yRotation;
		double deltaZ = zRotation-this.zRotation;
		
		rotate(true, deltaX, deltaY, deltaZ, null);
	}

	@Override
	public void resetRotation() {
		rotate(false, xRotation, yRotation, zRotation, null);
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
	
	public Point3d getAverage() {
		double xSum = 0;
		double ySum = 0;
		double zSum = 0;
		
		for(Polygon3d poly : polygons) {
			xSum+=poly.getXAverage();
			ySum+=poly.getYAverage();
			zSum+=poly.getZAverage();
		}

		xSum/=polygons.size();
		ySum/=polygons.size();
		zSum/=polygons.size();
		
		return new Point3d(xSum, ySum, zSum);
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
	
	public List<Polygon3d> getPolygons() {
		return polygons;
	}
	
	public Tetrahedron subdivide(int numSubdivisions) {
		Tetrahedron output = new Tetrahedron();
		
		for(Polygon3d poly : polygons) {
			output.merge(poly.subdivide(numSubdivisions));
		}
		
		return output;
	}
	
	public Tetrahedron merge(Tetrahedron other) {
		for(Polygon3d poly : other.getPolygons()) {
			polygons.add(poly.copy());
		}
		
		return this;
	}
	
	@Override
	public void delete() {
		for(int i = 0; i < polygons.size(); i++) {
			polygons.get(i).delete();
			polygons.set(i, null);
		}
		polygons = null;
	}
	
}
