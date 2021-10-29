package me.raymondexe.mcplayerrenderer.renderer.shapes;

/** 
 * Represents a collection of Polygons
 * @author MeanRollerCoding (Youtube)
 * @author https://github.com/Raymond-exe/
 * @version 1.0
 * @since 0.1
*/

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import me.raymondexe.mcplayerrenderer.renderer.PointLight;
import me.raymondexe.mcplayerrenderer.renderer.point.Point3d;

public class Tetrahedron implements Groupable {
	
	public double xRotation; //rotation stored in degrees, to reset if needed
	public double yRotation; //rotation stored in degrees, to reset if needed
	public double zRotation; //rotation stored in degrees, to reset if needed
	
	private List<Polygon3d> polygons;
	private ObjectGroup parent;
	
	public Tetrahedron(Polygon3d... polygons) {
		this();
		Collections.addAll(this.polygons, polygons);
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
	
	public void render(Graphics g, PointLight light) {
		sortPolygons();
		for(Polygon3d poly : polygons) {
			poly.render(g, light);
		}
	}
	
	public void renderLighting(Graphics g, ArrayList<PointLight> lightingSources) {
		renderLighting(g, 0, lightingSources);
	}
	
	public void renderLighting(Graphics g, int numSubdivisions, ArrayList<PointLight> lightingSources) {
		renderLighting(g, numSubdivisions, lightingSources, false);
	}
	
	public void renderLighting(Graphics g, int numSubdivisions, ArrayList<PointLight> lightingSources, boolean gradientShading) {

		//TODO subdivide so that this thing is more even
		//TODO NO NO BAD NO SUBDIVIDING
		
		Tetrahedron lightingTetra = this.subdivide(numSubdivisions);
		Color oldPolyColor;
		for(Polygon3d poly : lightingTetra.polygons) {
			double closeIntensity = lightingSources.get(0).getIntensity(poly.getClosestPoint(lightingSources.get(0).origin()));
			double farIntensity = lightingSources.get(0).getIntensity(poly.getFurthestPoint(lightingSources.get(0).origin()));

			for(PointLight light : lightingSources) {
				if(closeIntensity > light.getIntensity(poly.getAverage())) {
					closeIntensity = light.getIntensity(poly.getAverage());
				}
			}
			
			oldPolyColor = poly.getColor();
			int closeRed  = Math.max(0, Math.min(255, (int)(oldPolyColor.getRed() - oldPolyColor.getRed()*closeIntensity)));
			int closeGreen= Math.max(0, Math.min(255, (int)(oldPolyColor.getGreen() - oldPolyColor.getGreen()*closeIntensity)));
			int closeBlue = Math.max(0, Math.min(255, (int)(oldPolyColor.getBlue() - oldPolyColor.getBlue()*closeIntensity)));
			poly.setColor(new Color(closeRed, closeGreen, closeBlue, oldPolyColor.getAlpha()));
			
			if(gradientShading) {
				int farRed  = Math.max(0, Math.min(255, (int)(oldPolyColor.getRed() - oldPolyColor.getRed()*farIntensity)));;
				int farGreen= Math.max(0, Math.min(255, (int)(oldPolyColor.getGreen() - oldPolyColor.getGreen()*farIntensity)));;
				int farBlue = Math.max(0, Math.min(255, (int)(oldPolyColor.getBlue() - oldPolyColor.getBlue()*farIntensity)));;
				poly.setShadedColor(new Color(farRed, farGreen, farBlue, oldPolyColor.getAlpha()));
			}
		}
		
		lightingTetra.render(g, lightingSources.get(0));
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
		if(polygons.isEmpty())
			return 0;
		
		int xSum = 0;
		for(Polygon3d poly : polygons) {
			xSum+=poly.getXAverage();
		}

		//assign x, y, and z to the average of the polygons' averages
		return xSum / polygons.size();
	}

	public double getYCoordinate() {
		if(polygons.isEmpty())
			return 0;
		
		int ySum = 0;
		for(Polygon3d poly : polygons) {
			ySum+=poly.getYAverage();
		}

		//assign x, y, and z to the average of the polygons' averages
		return ySum / polygons.size();
	}

	public double getZCoordinate() {
		if(polygons.isEmpty())
			return 0;
		
		int zSum = 0;
		for(Polygon3d poly : polygons) {
			zSum+=poly.getZAverage();
		}

		//assign x, y, and z to the average of the polygons' averages
		return zSum / polygons.size();
	}
	
	public double[] getAverage() {
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
		
		return Point3d.createPoint(xSum, ySum, zSum);
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
	
	public void add(Polygon3d poly) {
		polygons.add(poly);
	}
	
	public Tetrahedron merge(Tetrahedron other) {
		for(Polygon3d poly : other.getPolygons()) {
			polygons.add(poly.copy());
		}
		
		return this;
	}
	
	@Override
	public void delete() {
		while(polygons.size()>0) {
			polygons.get(0).delete();
			polygons.remove(0);
		}
		polygons = null;
	}
	
}
