package me.raymondexe.mcplayerrenderer.renderer.shapes;

/** 
 * Represents a collection of points in 3d space
 * @author MeanRollerCoding (Youtube)
 * @author https://github.com/Raymond-exe/
 * @version 1.0
 * @since 0.1
*/

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.List;

import me.raymondexe.mcplayerrenderer.renderer.PointLight;
import me.raymondexe.mcplayerrenderer.renderer.point.Point3d;
import me.raymondexe.mcplayerrenderer.renderer.point.PointConverter;

public class Polygon3d {
	//private static final double AMBIENT_LIGHTING = 0.05;

	private Color baseColor;
	private Color shadedColor;
	private List<Point3d> points;
	
	public Polygon3d(Color color, Point3d... pts) {
		this.baseColor = color;
		points = new ArrayList<>();
		
		for(int i = 0; i < pts.length; i++)
			points.add(pts[i]);
	}
	
	public Polygon3d(Color color, List<Point3d> pts) {
		this.baseColor = color;
		points = pts;
	}
	
	public void render(Graphics g) {
		render(g, null);
	}

	public void render(Graphics g, PointLight light) {
		if(baseColor.getAlpha() == 0)
			return;
		
		Polygon poly = new Polygon();
		
		Point p;
		for(int i = 0; i < points.size(); i++) {
			p = PointConverter.convertPoint(points.get(i));
			poly.addPoint(p.x, p.y);
		}
		
		if(shadedColor == null || light == null) {
			g.setColor(baseColor);
			g.fillPolygon(poly);
		} else {
			Graphics2D g2d = (Graphics2D)g;
			Point close = PointConverter.convertPoint(getClosestPoint(light.origin()));
			Point far = PointConverter.convertPoint(getFurthestPoint(light.origin()));
			g2d.setPaint(new GradientPaint((int)far.getX(), (int)far.getY(), shadedColor, (int)close.getX(), (int)close.getY(), baseColor));
			g2d.fillPolygon(poly);
		}
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
	
	public double[] getAverage() {
		return new double[] {getXAverage(), getYAverage(), getZAverage()};
	}
	
	public void rotate(boolean CW, double xRotation, double yRotation, double zRotation, Vector3d lightVector) {
		for (Point3d p : points) {
			PointConverter.rotateXAxis(p, CW, xRotation);
			PointConverter.rotateYAxis(p, CW, yRotation);
			PointConverter.rotateZAxis(p, CW, zRotation);
		}
		
		updateLighting(lightVector);
		
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
	
	public Point3d getClosestPoint(Point3d other) {
		Point3d closest = points.get(0);
		double distance = closest.getDistanceFrom(other);
		for(Point3d pt : points) {
			if(pt.getDistanceFrom(other) < distance) {
				closest = pt;
				distance = pt.getDistanceFrom(other);
			}
		}
		return closest;
	}
	
	public Point3d getFurthestPoint(Point3d other) {
		Point3d furthest = points.get(0);
		double distance = furthest.getDistanceFrom(other);
		for(Point3d pt : points) {
			if(pt.getDistanceFrom(other) > distance) {
				furthest = pt;
				distance = pt.getDistanceFrom(other);
			}
		}
		return furthest;
	}

	public Color getColor() {
		return baseColor;
	}
	
	public Color getShadedColor() {
		return shadedColor;
	}
	
	public void setColor(Color c) {
		baseColor = c;		
	}
	
	public void setShadedColor(Color c) {
		shadedColor = c;
	}

	public void move(double deltaX, double deltaY, double deltaZ) {
		
		for(Point3d pt : points) {
			pt.x+=deltaX;
			pt.y+=deltaY;
			pt.z+=deltaZ;
		}
		
	}
	
	public Vector3d getNormal() {
		Vector3d v1 = new Vector3d(points.get(0), points.get(1));
		Vector3d v2 = new Vector3d(points.get(1), points.get(2));
		Vector3d normal = Vector3d.normalize(Vector3d.cross(v2, v1));
		
		return normal;
	}
	
	public Tetrahedron subdivide(int numSubdivisions) {
		if(numSubdivisions<=0 || points.size() < 3)
			return new Tetrahedron(this);
		
		Tetrahedron output = new Tetrahedron();
		
		/*
		//can only do this with squares for now
		if(points.size()==4) {
			System.out.println("4!");
			Point3d ptA = points.get(0);
			Point3d ptB = points.get(1);
			Point3d ptC = points.get(2);
			Point3d ptD = points.get(3);
			
			Point3d ptAB = Point3d.average(ptA, ptB);
			Point3d ptBC = Point3d.average(ptB, ptC);
			Point3d ptCD = Point3d.average(ptC, ptD);
			Point3d ptAD = Point3d.average(ptD, ptA);
			Point3d ptMid = Point3d.average(ptA, ptC);

			output.merge(new Polygon3d(baseColor, ptA, ptAB, ptMid, ptAD).subdivide(numSubdivisions-1));
			output.merge(new Polygon3d(baseColor, ptAB, ptB, ptBC, ptMid).subdivide(numSubdivisions-1));
			output.merge(new Polygon3d(baseColor, ptMid, ptBC, ptC, ptCD).subdivide(numSubdivisions-1));
			output.merge(new Polygon3d(baseColor, ptAD, ptMid, ptCD, ptD).subdivide(numSubdivisions-1));
			
			return output;
		} //*/
		
		
		//if it's not a square, shit
		ArrayList<Polygon3d> newPolygons = new ArrayList<>();
		ArrayList<Point3d> newPolyPoints = new ArrayList<>();
		double[] coords;
		//go through each point and...
		for(Point3d ptA : points) {
			newPolyPoints = new ArrayList<>();
			//create a new point between itself and all the other points
			for(Point3d ptB : points) {
				newPolyPoints.add(Point3d.average(ptA, ptB));
			}
			newPolygons.add(new Polygon3d(baseColor, newPolyPoints));
		}
		
		numSubdivisions--;
		
		for(Polygon3d poly : newPolygons) {
			output.merge(poly.subdivide(numSubdivisions));
		}
		
		return output;
			
	}
	
	private void updateLighting(Vector3d lightVector) {
		/*
		if(points.size() < 3 || lightVector == null) {
			//TODO ?
			return;
		}
		
		Vector3d normal = getNormal();
		
		double dot = Vector3d.dot(normal, lightVector);
		double sign = (dot<0? -1 : 1);
		dot = sign*dot*dot;
		System.out.println("Dot: " + dot);
		
		dot = (dot+1) / 2 * 0.95;
		
		double lightRatio = AMBIENT_LIGHTING + dot;
		
		//Updating lightColor
		int r = (int)(baseColor.getRed()*lightRatio);
		int g = (int)(baseColor.getGreen()*lightRatio);
		int b = (int)(baseColor.getBlue()*lightRatio);
		lightColor = new Color(r, g, b, baseColor.getAlpha());
		//*/
	}	
	
	public Polygon3d copy() {
		return new Polygon3d(baseColor, points);
	}
	
	public void delete() {
		while(points.size() > 0)
			points.remove(0);
		
		baseColor = null;
		points = null;
	}
}
