package renderer.shapes;

import renderer.point.Point3d;

public class Vector3d {
	
	private Point3d start;
	private Point3d end;
	
	public Vector3d(Point3d s, Point3d e) {
		start = s;
		end = e;
	}
	
	public Point3d getStart() {
		return start;
	}
	
	public Point3d getEnd() {
		return end;
	}
	
	public double getDeltaX() {
		return start.x-end.x;
	}
	
	public double getDeltaY() {
		return start.y-end.y;
	}
	
	public double getDeltaZ() {
		return start.y-end.y;
	}
	
	public Vector3d getVectorFromOrigin() {
		return new Vector3d(new Point3d(0,0,0), new Point3d(getDeltaX(), getDeltaY(), getDeltaZ()));
	}
	
	public double getLength() {
		return start.getDistanceFrom(end);
	}
	
	// Used this video lol: https://www.youtube.com/watch?v=pWbOisq1MJU 
	public Vector3d crossProduct(Vector3d other) {
		double determinantI = this.getDeltaY()*other.getDeltaZ() - this.getDeltaZ()*other.getDeltaY();
		double determinantJ = -(this.getDeltaX()*other.getDeltaZ() - this.getDeltaZ()*other.getDeltaX());
		double determinantK = this.getDeltaX()*other.getDeltaY() - this.getDeltaY()*other.getDeltaX();
		
		Point3d outputStart = Point3d.average(this.start, other.start);
		Point3d outputEnd = new Point3d(outputStart.x+determinantI, outputStart.y+determinantJ, outputStart.z+determinantK);
		
		return new Vector3d(outputStart, outputEnd);
	}
	
	public String toString() {
		return "<" + getDeltaX() + "i, " + getDeltaY() + "j, " + getDeltaZ() + "k>";
	}
	
}
