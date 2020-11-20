package renderer.shapes;

import renderer.point.Point3d;

public class Vector3d {
	
	private Point3d start;
	private Point3d end;
	
	public Vector3d(Point3d s, Point3d e) {
		start = s;
		end = e;
	}
	
	public Vector3d(double x, double y, double z) {
		start = new Point3d(0, 0, 0);
		end = new Point3d(x, y, z);
	}
	
	public Point3d getStart() {
		return start;
	}
	
	public Point3d getEnd() {
		return end;
	}
	
	public double getX() {
		return start.x-end.x;
	}
	
	public double getY() {
		return start.y-end.y;
	}
	
	public double getZ() {
		return start.y-end.y;
	}
	
	public Vector3d getVectorFromOrigin() {
		return new Vector3d(new Point3d(0,0,0), new Point3d(getX(), getY(), getZ()));
	}
	
	public double getMagnitude() {
		double magnitude = Math.sqrt(getX()*getX() + getY()*getY() + getZ()*getZ());
		return magnitude;
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
				v1.getZ()*v2.getX() - v1.getX()*v2.getZ(),
				v1.getX()*v2.getY() - v1.getY()*v2.getX()
				);
	}

	public static Vector3d normalize(Vector3d vector) {
		double magnitude = Math.sqrt(vector.getX()*vector.getX() + vector.getY()*vector.getY() + vector.getZ()*vector.getZ());
		return new Vector3d(vector.getX()/magnitude, vector.getY()/magnitude, vector.getZ()/magnitude);
	}
	
}
