package renderer.shapes;

/** 
 * Represents a collection of points in 3d space
 * @author MeanRollerCoding (Youtube)
 * @author https://github.com/Raymond-exe/
 * @version 1.0
 * @since 0.1
*/

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.List;

import renderer.point.Point3d;
import renderer.point.PointConverter;

public class Polygon3d {

	private Color color;
	private List<Point3d> points;
	
	public Polygon3d(Color color, Point3d... pts) {
		this.color = color;
		points = new ArrayList<>();
		
		for(Point3d p : pts) {
			this.points.add(p.copy());
		}
	}
	
	public Polygon3d(Color color, List<Point3d> pts) {
		this.color = color;
		points = new ArrayList<>();
		
		for(Point3d p : pts) {
			this.points.add(p.copy());
		}
	}

	public void render(Graphics g) {
		Polygon poly = new Polygon();
		
		for(int i = 0; i < points.size(); i++) {
			Point p = PointConverter.convertPoint(points.get(i));
			poly.addPoint(p.x, p.y);
		}
		
		g.setColor(color);
		g.fillPolygon(poly);		
	}
		
	public double getYAverage() {
		double sum = 0;
		
		for(Point3d p : points) {
			sum+= p.y;
		}
		return sum/points.size();
	}
	
	public double getXAverage() {
		double sum = 0;
		
		for(Point3d p : points) {
			sum+= p.x;
		}
		return sum/points.size();
	}
	
	public double getZAverage() {
		double sum = 0;
		
		for(Point3d p : points) {
			sum+= p.z;
		}
		return sum/points.size();
	}
	
	public void rotate(boolean CW, double xRotation, double yRotation, double zRotation) {
		for (Point3d p : points) {
			PointConverter.rotateXAxis(p, CW, xRotation);
			PointConverter.rotateYAxis(p, CW, yRotation);
			PointConverter.rotateZAxis(p, CW, zRotation);
		}
		
	}
	
	public double getYMax() {
		Point3d pointMax = points.get(0);
		
		for(Point3d p : points) {
			if(p.y > pointMax.y)
				pointMax = p;
		}
		
		return pointMax.y;
	}
	
	public double getYMin() {
		Point3d pointMin = points.get(0);
		
		for(Point3d p : points) {
			if(p.y < pointMin.y)
				pointMin = p;
		}
		
		return pointMin.y;		
	}
	
	public double getXMax() {
		Point3d pointMax = points.get(0);
		
		for(Point3d p : points) {
			if(p.x > pointMax.x)
				pointMax = p;
		}
		
		return pointMax.x;
	}
	
	public double getXMin() {
		Point3d pointMin = points.get(0);
		
		for(Point3d p : points) {
			if(p.x < pointMin.x)
				pointMin = p;
		}
		
		return pointMin.x;		
	}
	
	public double getZMax() {
		Point3d pointMax = points.get(0);
		
		for(Point3d p : points) {
			if(p.z > pointMax.z)
				pointMax = p;
		}
		
		return pointMax.z;
	}
	
	public double getZMin() {
		Point3d pointMin = points.get(0);
		
		for(Point3d p : points) {
			if(p.z < pointMin.z)
				pointMin = p;
		}
		
		return pointMin.z;
	}

	public void setColor(Color c) {
		color = c;		
	}

	public void move(double deltaX, double deltaY, double deltaZ) {
		
		for(Point3d pt : points) {
			pt.x+=deltaX;
			pt.y+=deltaY;
			pt.z+=deltaZ;
		}
		
	}
	
	public double getNormalDistance(Point3d point) {
		Point3d ptA = points.get(0);
		Point3d ptB = null;
		Point3d ptC = null;
		
		for(Point3d pt : points) {
			if(ptA.equals(pt))
				continue;
			if(ptB == null || pt.getDistanceFrom(ptA) < ptB.getDistanceFrom(ptA)) {
				ptB = pt;
				continue;
			}
			if(ptC == null || pt.getDistanceFrom(ptA) < ptC.getDistanceFrom(ptA)) {
				ptC = pt;
			}
		}
		
		if(ptB==null || ptC==null)
			return -1;
		
		Vector3d normalVector = (new Vector3d(ptA, ptB)).crossProduct(new Vector3d(ptA, ptC));
		double deltaX = normalVector.getDeltaX();
		double deltaY = normalVector.getDeltaY();
		double deltaZ = normalVector.getDeltaZ();
		double d = -(deltaX*ptA.x + deltaY*ptA.y + deltaZ*ptA.z);
		
		double distance = Math.abs(deltaX*point.x + deltaY*point.y + deltaZ*point.z + d) /		//numerator
						  Math.sqrt(deltaX*deltaX + deltaY*deltaY + deltaZ*deltaZ);				//denominator
		
		return distance;
	}
	
	
	public Polygon3d copy() {
		return new Polygon3d(color, points);
	}
}
