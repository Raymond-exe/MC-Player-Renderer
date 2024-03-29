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
import java.util.Arrays;
import java.util.List;

import me.raymondexe.mcplayerrenderer.renderer.PointLight;
import me.raymondexe.mcplayerrenderer.renderer.point.Line;
import me.raymondexe.mcplayerrenderer.renderer.point.Point3d;
import me.raymondexe.mcplayerrenderer.renderer.point.PointConverter;

public class Polygon3d {

	private Color baseColor;
	private Color shadedColor;
	private List<double[]> points;
	
	public Polygon3d(Color color, double[]... pts) {
		this.baseColor = color;
		points = new ArrayList<>();

		points.addAll(Arrays.asList(pts));
	}
	
	public Polygon3d(Color color, List<double[]> pts) {
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
			Point center = PointConverter.convertPoint(getAverage());
			Point[] closestPoints = getClosestTwoFlatPoints(PointConverter.convertPoint(light.origin()));
			Point[] furthestPoints = getFurthestTwoFlatPoints(PointConverter.convertPoint(light.origin()));
			Line lineFromLight = new Line(PointConverter.convertPoint(light.origin()), center);
			Point gradientStart = new Line(closestPoints[0], closestPoints[1]).getInterception(lineFromLight);
			Point gradientEnd = new Line(furthestPoints[0], furthestPoints[1]).getInterception(lineFromLight);
			g2d.setPaint(new GradientPaint((int)gradientEnd.getX(), (int)gradientEnd.getY(), shadedColor, (int)gradientStart.getX(), (int)gradientStart.getY(), baseColor));
			g2d.fillPolygon(poly);
		}
	}
		
	public double getYAverage() {
		double sum = 0;
		
		for(double[] p : points) {
			sum+= p[Point3d.Y];
		}
		return sum/points.size();
	}
	
	public double getXAverage() {
		double sum = 0;
		
		for(double[] p : points) {
			sum+= p[Point3d.X];
		}
		return sum/points.size();
	}
	
	public double getZAverage() {
		double sum = 0;
		
		for(double[] p : points) {
			sum+= p[Point3d.Z];
		}
		return sum/points.size();
	}
	
	public double[] getAverage() {
		return new double[] {getXAverage(), getYAverage(), getZAverage()};
	}
	
	public void rotate(boolean CW, double xRotation, double yRotation, double zRotation) {
		for (double[] p : points) {
			PointConverter.rotateXAxis(p, CW, xRotation);
			PointConverter.rotateYAxis(p, CW, yRotation);
			PointConverter.rotateZAxis(p, CW, zRotation);
		}
		
	}
	
	public double getYMax() {
		double yMax = points.get(0)[Point3d.Y];
		
		for(double[] p : points) {
			if(p[Point3d.Y] > yMax)
				yMax = p[Point3d.Y];
		}
		
		return yMax;
	}
	
	public double getYMin() {
		double yMax = points.get(0)[Point3d.Y];

		for(double[] p : points) {
			if(p[Point3d.Y] < yMax)
				yMax = p[Point3d.Y];
		}

		return yMax;
	}
	
	public double getXMax() {
		double xMax = points.get(0)[Point3d.X];

		for(double[] p : points) {
			if(p[Point3d.X] > xMax)
				xMax = p[Point3d.X];
		}

		return xMax;
	}
	
	public double getXMin() {
		double xMax = points.get(0)[Point3d.X];

		for(double[] p : points) {
			if(p[Point3d.X] < xMax)
				xMax = p[Point3d.X];
		}

		return xMax;
	}
	
	public double getZMax() {
		double zMax = points.get(0)[Point3d.Z];

		for(double[] p : points) {
			if(p[Point3d.Z] > zMax)
				zMax = p[Point3d.Z];
		}

		return zMax;
	}
	
	public double getZMin() {
		double zMax = points.get(0)[Point3d.Z];

		for(double[] p : points) {
			if(p[Point3d.Z] < zMax)
				zMax = p[Point3d.Z];
		}

		return zMax;
	}
	
	public double[] getClosestPoint(double[] other) {
		double[] closest = points.get(0);
		double distance = Point3d.getDistanceBetween(closest, other);
		for(double[] pt : points) {
			if(Point3d.getDistanceBetween(pt, other) < distance) {
				closest = pt;
				distance = Point3d.getDistanceBetween(pt, other);
			}
		}
		return closest;
	}

	public double[][] getClosestTwoPoints(double[] other) {
		double[] closest1 = points.get(0); // the furthest point
		double[] closest2 = points.get(0); // the 2nd furthest point
		double distance1 = Point3d.getDistanceBetween(closest1, other);
		double distance2 = Point3d.getDistanceBetween(closest2, other);
		double ptDistance;
		for(double[] pt : points) {
			ptDistance = Point3d.getDistanceBetween(pt, other);
			if(ptDistance < distance2) {
				if(ptDistance < distance1) {
					closest1 = pt;
					distance1 = ptDistance;
				} else {
					closest2 = pt;
					distance2 = ptDistance;
				}
			}
		}
		return new double[][]{closest1, closest2};
	}

	public Point[] getClosestTwoFlatPoints(Point other) {
		Point closest1 = PointConverter.convertPoint(points.get(0)); // the furthest point
		Point closest2 = PointConverter.convertPoint(points.get(0)); // the 2nd furthest point
		double distance1 = closest1.distance(other);
		double distance2 = closest2.distance(other);
		double ptDistance;
		for(double[] pt : points) {
			ptDistance = other.distance(PointConverter.convertPoint(pt));
			if(ptDistance < distance2) {
				if(ptDistance < distance1) {
					closest1 = PointConverter.convertPoint(pt);
					distance1 = ptDistance;
				} else {
					closest2 = PointConverter.convertPoint(pt);
					distance2 = ptDistance;
				}
			}
		}
		return new Point[]{closest1, closest2};
	}

	public double[] getFurthestPoint(double[] other) {
		double[] furthest = points.get(0);
		double distance = Point3d.getDistanceBetween(furthest, other);
		for(double[] pt : points) {
			if(Point3d.getDistanceBetween(pt, other) > distance) {
				furthest = pt;
				distance = Point3d.getDistanceBetween(pt, other);
			}
		}
		return furthest;
	}

	public double[][] getFurthestTwoPoints(double[] other) {
		double[] furthest1 = points.get(0); // the furthest point
		double[] furthest2 = points.get(0); // the 2nd furthest point
		double distance1 = Point3d.getDistanceBetween(furthest1, other);
		double distance2 = Point3d.getDistanceBetween(furthest2, other);
		double ptDistance;
		for(double[] pt : points) {
			ptDistance = Point3d.getDistanceBetween(pt, other);
			if(ptDistance > distance2) {
				if(ptDistance > distance1) {
					furthest1 = pt;
					distance1 = ptDistance;
				} else {
					furthest2 = pt;
					distance2 = ptDistance;
				}
			}
		}
		return new double[][]{furthest1, furthest2};
	}

	public Point[] getFurthestTwoFlatPoints(Point other) {
		Point furthest1 = PointConverter.convertPoint(points.get(0)); // the furthest point
		Point furthest2 = PointConverter.convertPoint(points.get(0)); // the 2nd furthest point
		double distance1 = furthest1.distance(other);
		double distance2 = furthest2.distance(other);
		double ptDistance;
		for(double[] pt : points) {
			ptDistance = other.distance(PointConverter.convertPoint(pt));
			if(ptDistance > distance2) {
				if(ptDistance > distance1) {
					furthest1 = PointConverter.convertPoint(pt);
					distance1 = ptDistance;
				} else {
					furthest2 = PointConverter.convertPoint(pt);
					distance2 = ptDistance;
				}
			}
		}
		return new Point[]{furthest1, furthest2};
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
		
		for(double[] pt : points) {
			pt[Point3d.X]+=deltaX;
			pt[Point3d.Y]+=deltaY;
			pt[Point3d.Z]+=deltaZ;
		}
		
	}
	
	public Vector3d getNormal() {
		Vector3d v1 = new Vector3d(points.get(0), points.get(1));
		Vector3d v2 = new Vector3d(points.get(1), points.get(2));
		
		return Vector3d.normalize(Vector3d.cross(v2, v1));
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
		ArrayList<double[]> newPolyPoints = new ArrayList<>();
		//go through each point and...
		for(double[] ptA : points) {
			newPolyPoints = new ArrayList<>();
			//create a new point between itself and all the other points
			for(double[] ptB : points) {
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
