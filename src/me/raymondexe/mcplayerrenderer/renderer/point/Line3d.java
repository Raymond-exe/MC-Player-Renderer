package me.raymondexe.mcplayerrenderer.renderer.point;

public class Line3d {

    private double[] pointA;
    private double[] pointB;

    public Line3d(double[] ptA, double[] ptB) {
        pointA = ptA;
        pointB = ptB;
    }

    public double getYSlope() {
        double deltaX = pointA[0] - pointB[0];
        double deltaY = pointA[1] - pointB[1];
        return deltaY/deltaX;
    }

    public double getZSlope() {
        double deltaX = pointA[0] - pointB[0];
        double deltaZ = pointA[2] - pointB[2];
        return deltaZ/deltaX;
    }
}
