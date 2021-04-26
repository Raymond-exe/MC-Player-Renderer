package me.raymondexe.mcplayerrenderer.renderer.point;

import java.awt.*;

public class Line {

    public Point start;
    public Point end;

    public Line(double[] s, double[] e) {
        this(Point3d.getPoint2d(s), Point3d.getPoint2d(e));
    }

    public Line(Point s, Point e) {
        start = s;
        end = e;
    }

    public double getSlope() {
        double deltaX = start.x - end.x;
        double deltaY = start.y - end.y;
        return deltaY/deltaX;
    }

    public double getYIntercept() {
        return getYIntercept(true);
    }

    public double getYIntercept(boolean useStart) {
        if(useStart) {
            return start.y - getSlope()*start.x;
        } else {
            return end.y - getSlope()*end.x;
        }
    }

//    public double getXIntercept() {
//        //TODO but will this be useful?
//    }

    public double getXCoordOf(double yCoord) {
        return (yCoord-getYIntercept())/getSlope();
    }

    public double getYCoordOf(double xCoord){
        return getSlope()*xCoord + getYIntercept();
    }

    public double[] getInterceptionExact(Line other) {
//        if(this.getSlope() == other.getSlope()) {
//            return null;
//        }

        double xCoord = (other.getYIntercept()-this.getYIntercept())/(this.getSlope()-other.getSlope());
        double yCoord = getYCoordOf(xCoord);

        return new double[]{xCoord, yCoord};
    }

    public Point getInterception(Line other) {
        double[] pt = getInterceptionExact(other);
        return new Point(
                (int)Math.round(pt[0]),
                (int)Math.round(pt[1]));
    }

}
