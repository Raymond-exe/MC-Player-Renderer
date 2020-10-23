package renderer.point;

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
	
}
