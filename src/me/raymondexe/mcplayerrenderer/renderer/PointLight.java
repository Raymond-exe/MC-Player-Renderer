package me.raymondexe.mcplayerrenderer.renderer;

import me.raymondexe.mcplayerrenderer.renderer.point.Point3d;

public class PointLight {

	private Point3d source;
	private double radius;
	private double power;
	
	public PointLight(Point3d src, double rad, double pow) {
		source = src;
		radius = rad;
		power = pow;
	}
	
	public double getIntensity(double[] pointCoords) {
		double distance = source.getDistanceFrom(pointCoords);
		
		double intensity = radius*Math.pow(distance/radius, power);
		
		return Math.min(1, Math.max(LightingControl.AMBIENT_LIGHTING_INDEX, intensity));
	}
	
}
