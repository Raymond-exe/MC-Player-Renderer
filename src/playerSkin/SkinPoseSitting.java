package playerSkin;

public class SkinPoseSitting implements SkinPose {

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
		double[] output = {1.5, 0.838776, 0.350786};
		return output; 
	}

	@Override
	public double[] getLeftArmRotation() {
		double[] output = {40, 0, 0};
		return output; 
	}

	@Override
	public double[] getRightArmLocation() {
		double[] output = {-1.5, 0.838776, 0.350786};
		return output; 
	}

	@Override
	public double[] getRightArmRotation() {
		double[] output = {40, 0, 0};
		return output; 
	}

	@Override
	public double[] getLeftLegLocation() {
		double[] output = {-0.75, 1.14952, -1.88823};
		return output; 
	}

	@Override
	public double[] getLeftLegRotation() {
		double[] output = {75, 0, 15};
		return output; 
	}

	@Override
	public double[] getRightLegLocation() {
		double[] output = {0.75, 1.14952, -1.88823};
		return output; 
	}

	@Override
	public double[] getRightLegRotation() {
		double[] output = {75, 0, -15};
		return output; 
	}
}
