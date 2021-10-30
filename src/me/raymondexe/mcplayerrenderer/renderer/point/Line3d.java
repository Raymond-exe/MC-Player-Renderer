package me.raymondexe.mcplayerrenderer.renderer.point;

public class Line3d {

    public double[] pointA;
    public double[] pointB;

    public Line3d(double[] ptA, double[] ptB) {
        pointA = ptA;
        pointB = ptB;
        // System.out.println("Created line: " + doubleArray(ptA) + " -> " + doubleArray(ptB));
    }
    
    public double getXAt(double t) {
        return pointA[0] + t*pointDifference(0);
    }
    
    public double getYAt(double t) {
        return pointA[1] + t*pointDifference(1);
    }
    
    public double getZAt(double t) {
        return pointA[2] + t*pointDifference(2);
    }

    // shorthand for `pointB[#]-pointA[#]` where `#` is the given parameter
    public double pointDifference(int xyz) { // 0=x, 1=y, 2=z
        if(xyz < 0 || xyz > 2)
            throw new IndexOutOfBoundsException("Given parameter for pointDifference() must be 0, 1, or 2 for x, y, or z.");
        return pointB[xyz]-pointA[xyz];
    }

    public double[] getPointAt(double t) {
        return new double[]{getXAt(t), getYAt(t), getZAt(t)};
    }

    @Override
    public String toString() {
        return "x(t) = " + pointA[0] + " + t(" + pointB[0] + " - " + pointA[0] + ")\n"
        + "y(t) = " + pointA[1] + " + t(" + pointB[1] + " - " + pointA[1] + ")\n"
        + "z(t) = " + pointA[2] + " + t(" + pointB[2] + " - " + pointA[2] + ")";
    }

    // used for printing out double arrays
    private static String doubleArray(double[] arr) {
        if(arr.length == 0) {
            return "{}";
        }

        String str = "{";
        for(double d : arr) {
            str += d + ", ";
        }

        return str.substring(0, str.length()-2) + "}";
    }
}
