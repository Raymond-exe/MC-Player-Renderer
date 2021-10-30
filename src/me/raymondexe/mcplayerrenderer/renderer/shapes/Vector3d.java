package me.raymondexe.mcplayerrenderer.renderer.shapes;

import me.raymondexe.mcplayerrenderer.renderer.point.Point3d;

public class Vector3d {
	
	private double[] start;
	private double[] end;
	
	public Vector3d(double[] s, double[] e) {
		start = s;
		end = e;
	}
	
	public Vector3d(double x, double y, double z) {
		this(Point3d.createPoint(0, 0, 0), Point3d.createPoint(x, y, z));
	}
	
	public double[] getStart() {
		return start;
	}
	
	public double[] getEnd() {
		return end;
	}
	
	public double getX() {
		return start[Point3d.X]-end[Point3d.X];
	}
	
	public double getY() {
		return start[Point3d.Y]-end[Point3d.Y];
	}
	
	public double getZ() {
		return start[Point3d.Y]-end[Point3d.Y]; //why is it y?
	}
	
	public Vector3d getVectorFromOrigin() {
		return new Vector3d(Point3d.createPoint(0,0,0), Point3d.createPoint(getX(), getY(), getZ()));
	}
	
	public double getMagnitude() {
		return Math.sqrt(getX()*getX() + getY()*getY() + getZ()*getZ());
	}
	
	public String toString() {
		return "<" + getX() + "i, " + getY() + "j, " + getZ() + "k>";
	}
	
	/* * * * * STATIC METHODS * * * * */
	
	//self note: 2 parallel vectors will dot product to 1
	//if vectors are perpendicular, will return 0
	//if vectors are anti-parallel, will return -1
	public static double dot(Vector3d v1, Vector3d v2) {
		return v1.getX()*v2.getX() + v1.getY()*v2.getY() + v1.getZ()*v2.getZ();
	}
	
	public static Vector3d cross(Vector3d v1, Vector3d v2) {
		return new Vector3d(
				v1.getY()*v2.getZ() - v1.getZ()*v2.getY(),
				v1.getX()*v2.getZ() - v1.getZ()*v2.getX(),
				v1.getX()*v2.getY() - v1.getY()*v2.getX()
				);
	}

	public static Vector3d normalize(Vector3d vector) {
		double magnitude = Math.sqrt(vector.getX()*vector.getX() + vector.getY()*vector.getY() + vector.getZ()*vector.getZ());
		return new Vector3d(vector.getX()/magnitude, vector.getY()/magnitude, vector.getZ()/magnitude);
	}
	
}
