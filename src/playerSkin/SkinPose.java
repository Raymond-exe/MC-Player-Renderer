package playerSkin;

public interface SkinPose {

	public double[] getHeadLocation();
	public double[] getHeadRotation();
	public double[] getChestLocation();
	public double[] getChestRotation();
	public double[] getLeftArmLocation();
	public double[] getLeftArmRotation();
	public double[] getRightArmLocation();
	public double[] getRightArmRotation();
	public double[] getLeftLegLocation();
	public double[] getLeftLegRotation();
	public double[] getRightLegLocation();
	public double[] getRightLegRotation();

	public static final int HEAD = 0;
	public static final int CHEST = 1;
	public static final int LEFT_ARM = 2;
	public static final int RIGHT_ARM = 3;
	public static final int LEFT_LEG = 4;
	public static final int RIGHT_LEG = 5;
	
	public static final int LOCATION = 0;
	public static final int ROTATION = 1;
	
	public default double[][][] getValues() {
		
		//[LIMB][LOCATION/ROTATION]
		
		double[][][] output = {
					{ getHeadLocation(), getHeadRotation() },
					{ getChestLocation(), getChestRotation() },
					{ getLeftArmLocation(), getLeftArmRotation() },
					{ getRightArmLocation(), getRightArmRotation() },
					{ getLeftLegLocation(), getLeftLegRotation() },
					{ getRightLegLocation(), getRightLegRotation() }
				};
		
		return output;
	}
}
