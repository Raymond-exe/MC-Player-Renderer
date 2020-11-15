package playerSkin;

public class SkinPoseStanding implements SkinPose {

	@Override
	public double[] getHeadLocation() { 
		double[] output = {0, 0, 2.5};
		return output; 
	}

	@Override
	public double[] getHeadRotation() {
		double[] output = {0, 0, 0};
		return output; 
	}

	@Override
	public double[] getChestLocation() {
		double[] output = {0, 0, 0};
		return output; 
	}

	@Override
	public double[] getChestRotation() {
		double[] output = {0, 0, 0};
		return output; 
	}

	@Override
	public double[] getLeftArmLocation() {
		double[] output = {1.5, 0, 0};
		return output; 
	}

	@Override
	public double[] getLeftArmRotation() {
		double[] output = {0, 0, 0};
		return output; 
	}

	@Override
	public double[] getRightArmLocation() {
		double[] output = {-1.5, 0, 0};
		return output; 
	}

	@Override
	public double[] getRightArmRotation() {
		double[] output = {0, 0, 0};
		return output; 
	}

	@Override
	public double[] getLeftLegLocation() {
		double[] output = {-0.5, 0, -3};
		return output; 
	}

	@Override
	public double[] getLeftLegRotation() {
		double[] output = {0, 0, 0};
		return output; 
	}

	@Override
	public double[] getRightLegLocation() {
		double[] output = {0.5, 0, -3};
		return output; 
	}

	@Override
	public double[] getRightLegRotation() {
		double[] output = {0, 0, 0};
		return output; 
	}
	
}
