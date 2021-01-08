package me.raymondexe.mcplayerrenderer.playerSkin;

import java.security.InvalidParameterException;

import me.raymondexe.mcplayerrenderer.playerSkin.PlayerSkin.SkinConfig;

/** 
 * Represents an player skin's pose.
 * @author https://github.com/Raymond-exe/
 * @version 1.0
 * @since 0.5
*/

public class SkinPose {
	
	private String name;
	private double[][][] values;	

	public static final int HEAD = 0;
	public static final int CHEST = 1;
	public static final int LEFT_ARM = 2;
	public static final int RIGHT_ARM = 3;
	public static final int LEFT_LEG = 4;
	public static final int RIGHT_LEG = 5;
	
	public static final int LOCATION = 0;
	public static final int ROTATION = 1;
	
	public SkinPose(String name) {
		this.name = name;
		values = new double[6][2][3];
	}
	
	public SkinPose(String name, double[][][] values) {
		this(name);
		this.values = values.clone();
	}
	
	public String getPoseName() {
		return name;
	}

	public double[] get(int limb, int property) {
		if(limb > RIGHT_LEG || limb < HEAD || (property != LOCATION && property != ROTATION)) {
			throw new InvalidParameterException();
		}
		
		return values[limb][property].clone();
	}

	public double get(int limb, int property, char c) {
		if(limb > RIGHT_LEG || limb < HEAD || (property != LOCATION && property != ROTATION)) {
			throw new InvalidParameterException();
		}		
		
		return values[limb][property][toInt(c)];
	}
	
	public boolean set(int limb, int property, double[] xyz) {
		if(xyz.length != 3)
			return false;
		
		boolean success = true;
		for(char c = 'x'; c <= 'z'; c++) {
			success = success&&set(limb, property, c, xyz[toInt(c)]);
		}
		return success;
	}
	
	public boolean set(int limb, int property, char c, double val) {
		try {
			get(limb, property, c); //just to verify limb, property, and c are all valid values
			
			values[limb][property][toInt(c)] = val;
			
			return values[limb][property][toInt(c)] == val;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public void setVisible(int limb, int property, boolean visible) {
		//TODO find a way to hide/show certain limbs. I have no idea why, but it might be useful
	}
	
	private int toInt(char c) {
		int xyz;
		switch(c) {
		case 'x':
		case 'X':
			xyz = 0;
			break;
		case 'y':
		case 'Y':
			xyz = 1;
			break;
		case 'z':
		case 'Z':
			xyz = 2;
			break;
		default:
			throw new InvalidParameterException();
		}
		return xyz;
	}
	
	/**
	 * Retrieves the location and rotation values of each limb for the skin's pose
	 * @return location and rotation values of each limb of a PlayerSkin.
	 */
	public double[][][] getValues() {
		return values.clone();
	}
	
	
	
	/* ********* STATIC METHODS ********* */
	

	
	public static SkinPose sitting() {
									//location, rotation
		double[][] headValues = 	{{0, 0, 2.5}, {0, 0, 0}};
		double[][] chestValues = 	{{0, 0, 0}, {0, 0, 0}};
		double[][] leftArmValues = 	{{1.5, 0.838776, 0.350786}, {40, 0, 0}};
		double[][] rightArmValues = {{-1.5, 0.838776, 0.350786}, {40, 0, 0}};
		double[][] leftLegValues = 	{{-0.75, 1.14952, -1.88823}, {75, 0, 15}};
		double[][] rightLegValues = {{0.75, 1.14952, -1.88823}, {75, 0, -15}};
		
		double[][][] poseValues = {headValues, chestValues, leftArmValues, rightArmValues, leftLegValues, rightLegValues};
		
		return new SkinPose("sitting", poseValues);
	}
	
	public static SkinPose sitting(SkinConfig skinType) {
		SkinPose output = sitting();
		
		if(skinType == SkinConfig.ALEX) {
			output.set(LEFT_ARM, LOCATION, 'x', (41.0/30));
			output.set(RIGHT_ARM, LOCATION, 'x', -(41.0/30));
		}
		
		return output;
	}
	
	public static SkinPose standing() {
									//location, rotation
		double[][] headValues = 	{{0, 0, 2.5}, {0, 0, 0}};
		double[][] chestValues = 	{{0, 0, 0}, {0, 0, 0}};
		double[][] leftArmValues = 	{{1.5, 0, 0}, {0, 0, 0}};
		double[][] rightArmValues = {{-1.5, 0, 0}, {0, 0, 0}};
		double[][] leftLegValues = 	{{-0.5, 0, -3}, {0, 0, 0}};
		double[][] rightLegValues = {{0.5, 0, -3}, {0, 0, 0}};
		
		double[][][] poseValues = {headValues, chestValues, leftArmValues, rightArmValues, leftLegValues, rightLegValues};
		
		return new SkinPose("standing", poseValues);
	}
	
	public static SkinPose standing(SkinConfig skinType) {
		SkinPose output = standing();
		
		if(skinType == SkinConfig.ALEX) {
			output.set(LEFT_ARM, LOCATION, 'x', (41.0/30));
			output.set(RIGHT_ARM, LOCATION, 'x', -(41.0/30));
		}
		
		return output;
	}
	
	public static SkinPose template() {
									//location, rotation
		double[][] headValues = 	{{0, 0, 0}, {0, 0, 0}};
		double[][] chestValues = 	{{0, 0, 0}, {0, 0, 0}};
		double[][] leftArmValues = 	{{0, 0, 0}, {0, 0, 0}};
		double[][] rightArmValues = {{0, 0, 0}, {0, 0, 0}};
		double[][] leftLegValues = 	{{0, 0, 0}, {0, 0, 0}};
		double[][] rightLegValues = {{0, 0, 0}, {0, 0, 0}};
		
		double[][][] poseValues = {headValues, chestValues, leftArmValues, rightArmValues, leftLegValues, rightLegValues};
		
		return new SkinPose("template", poseValues);
	}
	
}
