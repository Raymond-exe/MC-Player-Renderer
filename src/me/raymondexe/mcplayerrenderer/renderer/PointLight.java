package me.raymondexe.mcplayerrenderer.renderer;

import me.raymondexe.mcplayerrenderer.renderer.point.Point3d;

public class PointLight {

	private static final double DEFAULT_AMBIENT_LIGHTING = 0.15;

	private double[] source;
	private double radius;
	private double power;
	private double ambientLight;
	
	public PointLight(double[] src, double rad, double pow, double ambience) {
		source = src;
		radius = rad;
		power = pow;
		ambientLight = ambience;
	}

	public PointLight(double[] src, double rad, double pow) {
		this(src, rad, pow, DEFAULT_AMBIENT_LIGHTING); // ambientLight defaults to 0.15
	}
	
	public double getIntensity(double[] pointCoords) {
		double distance = Point3d.getDistanceBetween(source, pointCoords);
		
		double intensity = radius*Math.pow(distance/radius, power);
		
		return Math.min(1, Math.max(ambientLight, intensity));
	}
	
	public double[] origin() {
		return source;
	}
	
}
