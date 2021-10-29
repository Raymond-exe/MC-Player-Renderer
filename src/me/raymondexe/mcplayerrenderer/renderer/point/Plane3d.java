package me.raymondexe.mcplayerrenderer.renderer.point;

import me.raymondexe.mcplayerrenderer.renderer.point.Line3d;

public class Plane3d {
    
    private double[] pointA;
    private double[] pointB;
    private double[] pointC;

    public Plane3d(double[] ptA, double[] ptB, double[] ptC) {
        pointA = ptA;
        pointB = ptB;
        pointC = ptC;
    }

    public double getXMax() {
        return Math.max(pointA[0], Math.max(pointB[0], pointC[0]));
    }

    public double getYMax() {
        return Math.max(pointA[1], Math.max(pointB[1], pointC[1]));
    }

    public double getZMax() {
        return Math.max(pointA[2], Math.max(pointB[2], pointC[2]));
    }

    public double getXMin() {
        return Math.min(pointA[0], Math.min(pointB[0], pointC[0]));
    }

    public double getYMin() {
        return Math.min(pointA[1], Math.min(pointB[1], pointC[1]));
    }

    public double getZMin() {
        return Math.min(pointA[2], Math.min(pointB[2], pointC[2]));
    }

    public boolean boundingPointsContains(double[] point3d) {
        return (
            point3d[0] > getXMin() && point3d[0] < getXMax() &&
            point3d[1] > getYMin() && point3d[1] < getYMax() &&
            point3d[2] > getZMin() && point3d[2] < getZMax()
        );
    }

    public double[] getIntersectionPoint(Line3d line) {
        return null; //TODO-RAY this
    }
}
