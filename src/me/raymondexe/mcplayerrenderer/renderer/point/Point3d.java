package me.raymondexe.mcplayerrenderer.renderer.point;

/** 
 * Represents a point in 3D space
 * @author MeanRollerCoding (Youtube)
 * @author https://github.com/Raymond-exe/
 * @version 1.0
 * @since 0.1
*/

public class Point3d {

	public double x, y, z;
	
	public Point3d(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Point3d copy() {
		return new Point3d(x, y, z);
	}
	
	public double getDistanceFrom(Point3d other) {
		double deltaX = x-other.x;
		double deltaY = y-other.y;
		double deltaZ = z-other.z;
		
		double deltaXY = Math.sqrt(deltaX*deltaX + deltaY*deltaY);
		double deltaXYZ = Math.sqrt(deltaXY*deltaXY + deltaZ*deltaZ);
		
		return deltaXYZ;
	}
	
	@Override
	public boolean equals(Object o) {
		if(!(o instanceof Point3d))
			return false;
		return equals((Point3d)o);
	}
	
	public boolean equals(Point3d other) {
		boolean matchX = this.x == other.x;
		boolean matchY = this.y == other.y;
		boolean matchZ = this.z == other.z;
		
		return matchX && matchY && matchZ;
	}
	
	public int getX2d() {
		return PointConverter.convertPoint(this).x;
	}
	
	public int getY2d() {
		return PointConverter.convertPoint(this).y;
	}
	
	public String toString() {
		return "(" + x + ", " + y + ", " + z + ")";
	}
	
	public static Point3d average(Point3d ptA, Point3d ptB) {
		double xAvg = (ptA.x + ptB.x)/2.0;
		double yAvg = (ptA.y + ptB.y)/2.0;
		double zAvg = (ptA.z + ptB.z)/2.0;
		
		return new Point3d(xAvg, yAvg, zAvg);
	}
	
}
