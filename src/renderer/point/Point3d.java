package renderer.point;

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
	
	public int getX2d() {
		return PointConverter.convertPoint(this).x;
	}
	
	public int getY2d() {
		return PointConverter.convertPoint(this).y;
	}
	
}
