package me.raymondexe.mcplayerrenderer.renderer.point;

import java.awt.Point;

/**
 * Represents a point in 3D space
 * @author MeanRollerCoding (Youtube)
 * @author https://github.com/Raymond-exe/
 * @version 1.0
 * @since 0.1
*/

public class Point3d {

	private static int pointCount = 0;

	public static final int X = 0, Y = 1, Z = 2;

	public static double[] createPoint(double x, double y, double z) {
		pointCount++;
		return new double[]{x, y, z};
	}

	public static boolean checkEquality(double[] ptA, double[] ptB) {
		return (
				ptA[X] == ptB[X]
				&& ptA[Y] == ptB[Y]
				&& ptA[Z] == ptB[Z]
				);
	}

	public static double getDistanceBetween(double[] pt1, double[] pt2) {
		double deltaX = pt1[X]-pt2[X];
		double deltaY = pt1[Y]-pt2[Y];
		double deltaZ = pt1[Z]-pt2[Z];

		return Math.sqrt(deltaX*deltaX + deltaY*deltaY + deltaZ*deltaZ);
	}

	public static Point getPoint2d(double[] pt3d) {
		return PointConverter.convertPoint(pt3d);
	}

	public static String toString(double[] pt3d) {
		return "(" + pt3d[X] + ", " + pt3d[Y] + ", " + pt3d[Z] + ")";
	}
	
	public static double[] average(double[] ptA, double[] ptB) {
		if(checkEquality(ptA, ptB))
			return ptA;

		return Point3d.createPoint(
				(ptA[X]+ptB[X])/2,
				(ptA[Y]+ptB[Y])/2,
				(ptA[Z]+ptB[Z])/2
		);
	}
	
	public static int getPointCount() {
		return pointCount;
	}
	
}
