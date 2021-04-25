package me.raymondexe.mcplayerrenderer.renderer.point;

/** 
 * Static class to render a 3D space onto a 2D viewport
 * @author MeanRollerCoding (Youtube)
 * @author https://github.com/Raymond-exe/
 * @version 1.0
 * @since 0.1
*/

import java.awt.Point;

public class PointConverter {

	public static double SCALE = 1;
	public static int FOV_SCALE = 250;
	public static double CAM_DISTANCE = 15; //defaults to 15
	public static int WIDTH;
	public static int HEIGHT;
	
	public static Point convertPoint(double[] pt3d) {
		double x3d = pt3d[Point3d.X];
		double y3d = pt3d[Point3d.Z];
		double depth = pt3d[Point3d.Y] * SCALE;
		double[] newVal = scale(x3d, y3d, depth);
		int x2d = (int)(WIDTH / 2 + newVal[1]);
		int y2d = (int)(HEIGHT / 2 - newVal[0]);
		
		Point point2d = new Point(x2d, y2d);
		return point2d;
	}
	
	private static double[] scale(double x3d, double y3d, double depth) {
		double dist = Math.sqrt(x3d*x3d + y3d*y3d);
		double theta = Math.atan2(x3d, y3d);
		double camDepth = CAM_DISTANCE-depth; //since the camera is at x pos 15
		double localScale = Math.abs(FOV_SCALE/(camDepth+FOV_SCALE));
		dist *= localScale;
		
		double[] output = {dist*Math.cos(theta), dist*Math.sin(theta)};
		return output;
	}
	
	//TODO do method lol
	public static void rotateXAxis(double[] p, boolean CW, double degrees) {
		double radius = Math.sqrt(p[Point3d.Y]*p[Point3d.Y] + p[Point3d.Z]*p[Point3d.Z]);
		double theta = Math.atan2(p[Point3d.Y], p[Point3d.Z]);
		theta += 2*Math.PI/360*degrees*(CW?-1:1);
		
		p[Point3d.Y] = radius*Math.sin(theta);
		p[Point3d.Z] = radius*Math.cos(theta);
	}
	
	public static void rotateYAxis(double[] p, boolean CW, double degrees) {
		double radius = Math.sqrt(p[Point3d.X]*p[Point3d.X] + p[Point3d.Z]*p[Point3d.Z]);
		double theta = Math.atan2(p[Point3d.X], p[Point3d.Z]);
		theta += 2*Math.PI/360*degrees*(CW?1:-1);
		
		p[Point3d.Z] = radius*Math.cos(theta);
		p[Point3d.X] = radius*Math.sin(theta);
	}
	
	//TODO do method lol
	public static void rotateZAxis(double[] p, boolean CW, double degrees) {
		double radius = Math.sqrt(p[Point3d.X]*p[Point3d.X] + p[Point3d.Y]*p[Point3d.Y]);
		double theta = Math.atan2(p[Point3d.X], p[Point3d.Y]);
		theta += 2*Math.PI/360*degrees*(CW?-1:1);
		
		p[Point3d.X] = radius*Math.sin(theta);
		p[Point3d.Y] = radius*Math.cos(theta);
	}
	
	public static void increaseFovScale(int num) {
		FOV_SCALE+=num;
	}
	
	public static void decreaseFovScale(int num) {
		FOV_SCALE-=num;
	}
	
	public static void adjustZoom(double delta) {
		SCALE+=delta;
		System.out.println("New zoom: " + SCALE);
	}
	
	public static int getFovScale() {
		return FOV_SCALE;
	}
	
	public static double getCameraDistance() {
		return CAM_DISTANCE;
	}
	
	public static void setCameraDistance(double d) {
		CAM_DISTANCE = d;
	}
	
}
