package renderer.shapes;

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
}
