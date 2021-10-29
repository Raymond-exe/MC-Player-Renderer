package me.raymondexe.mcplayerrenderer.renderer.point;

public class Line3d {

    private double[] pointA;
    private double[] pointB;

    public Line3d(double[] ptA, double[] ptB) {
        pointA = ptA;
        pointB = ptB;
    }
    
    public double getXAt(double t) {
        return pointA[0] + t*(pointB[0]-pointA[0]);
    }
    
    public double getYAt(double t) {
        return pointA[1] + t*(pointB[1]-pointA[1]);
    }
    
    public double getZAt(double t) {
        return pointA[2] + t*(pointB[2]-pointA[2]);
    }

    public double[] pointAt(double t) {
        return new double[]{getXAt(t), getYAt(t), getZAt(t)};
    }
}
