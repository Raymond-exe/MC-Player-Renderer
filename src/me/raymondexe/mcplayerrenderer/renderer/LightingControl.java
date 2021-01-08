package me.raymondexe.mcplayerrenderer.renderer;

import me.raymondexe.mcplayerrenderer.renderer.shapes.Vector3d;

public class LightingControl {

	public static Vector3d lightVector = Vector3d.normalize(new Vector3d(-0.5, -1, -1));
	//public static final double AMBIENT_OCCLUSION_RADIUS = 15;
	//public static final double AMBIENT_OCCLUSION_INDEX = 0.005;
	public static final double AMBIENT_LIGHTING_INDEX = 0.15;
	
}
