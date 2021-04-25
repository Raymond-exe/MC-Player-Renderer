package me.raymondexe.mcplayerrenderer.renderer;

import me.raymondexe.mcplayerrenderer.renderer.point.Point3d;

public class PointLight {

	private double[] source;
	private double radius;
	private double power;
	
	public PointLight(double[] src, double rad, double pow) {
		source = src;
		radius = rad;
		power = pow;
	}
	
	public double getIntensity(double[] pointCoords) {
		double distance = Point3d.getDistanceBetween(source, pointCoords);
		
		double intensity = radius*Math.pow(distance/radius, power);
		
		return Math.min(1, Math.max(LightingControl.AMBIENT_LIGHTING_INDEX, intensity));
	}
	
	public double[] origin() {
		return source;
	}
	
}
