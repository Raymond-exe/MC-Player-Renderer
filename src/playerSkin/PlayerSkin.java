package playerSkin;

import java.awt.image.BufferedImage;

import renderer.ImageConverter;
import renderer.shapes.ObjectGroup;
import renderer.shapes.Tetrahedron;

public class PlayerSkin {
	public static enum skinConfig {STEVE, ALEX};
	public static enum facing {FRONT, BACK, LEFT, RIGHT, TOP, BOTTOM};
	public static enum bodyPart {HEAD, CHEST, L_ARM, R_ARM, L_LEG, R_LEG};
	public static enum layer {BASE, OVERLAY, BOTH};
	
	private BufferedImage skinFile;
	private skinConfig skinType;
	

	public PlayerSkin(String uuid) {
		this(uuid, skinConfig.STEVE);
	}
	
	public PlayerSkin(String uuid, skinConfig type) {
		this.skinFile = PlayerSkinGrabber.getSkin(uuid);
		this.skinType = type;
	}
	
	public PlayerSkin(BufferedImage skinFile) {
		this(skinFile, skinConfig.STEVE);
	}
	
	public PlayerSkin(BufferedImage skinFile, skinConfig type) {
		this.skinFile = skinFile;
		this.skinType = type;
	}
	
	public BufferedImage get(bodyPart limb, facing side, layer layers) {
		switch(limb) {
		case HEAD:
			return getHead(side, layers);
		case CHEST:
			return getChest(side, layers);
		case L_ARM:
			return getArm(side, layers, true);
		case R_ARM:
			return getArm(side, layers, false);
		case L_LEG:
			return getLeg(side, layers, true);
		case R_LEG:
			return getLeg(side, layers, false);
		default:
			return null;
		}
	}
	
	private BufferedImage getHead(facing side, layer layers) {
		BufferedImage image;
		int x;
		int y;
		int width = 8;	 //width of a minecraft head in pixels
		int height = 8;  //height of a minecraft head in pixels;
		
		switch(side) {
		case FRONT:
			x = 8;
			y = 8;
			break;
		case BACK:
			x = 24;
			y = 8;
			break;
		case LEFT:
			x = 16;
			y = 8;
			break;
		case RIGHT:
			x = 0;
			y = 8;
			break;
		case TOP:
			x = 8;
			y = 0;
			break;
		case BOTTOM:
			x = 16;
			y = 0;
			break;
		default:
			return null;
		}
		
		if(layers == layer.OVERLAY) {
			x+=32;
		}
		
		image = skinFile.getSubimage(x, y, width, height);
		return image;
	}

	private BufferedImage getChest(facing side, layer layers) {
		BufferedImage image;
		int x;
		int y;
		int width = 8;	 //width of a minecraft chest in pixels
		int height = 12;  //height of a minecraft chest in pixels;
		
		switch(side) {
		case FRONT:
			x = 20;
			y = 20;
			break;
		case BACK:
			x = 32;
			y = 20;
			break;
		case LEFT:
			x = 28;
			y = 20;
			width = 4;
			break;
		case RIGHT:
			x = 16;
			y = 20;
			width = 4;
			break;
		case TOP:
			x = 20;
			y = 16;
			height = 4;
			break;
		case BOTTOM:
			x = 28;
			y = 16;
			height = 4;
			break;
		default:
			return null;
		}
		
		if(layers == layer.OVERLAY) {
			y+=16;
		}
		
		image = skinFile.getSubimage(x, y, width, height);
		return image;
	}
	
	private BufferedImage getLeg(facing side, layer layers, boolean leftSide) {
		BufferedImage image;
		int x;
		int y;
		int width = 4;	 //width of a minecraft leg in pixels
		int height = 12;  //height of a minecraft leg in pixels;
		
		switch(side) {
		case FRONT:
			x = 20;
			y = 52;
			break;
		case BACK:
			x = 28;
			y = 52;
			break;
		case LEFT:
			x = 24;
			y = 52;
			break;
		case RIGHT:
			x = 16;
			y = 52;
			break;
		case TOP:
			x = 20;
			y = 48;
			height = 4;
			break;
		case BOTTOM:
			x = 24;
			y = 48;
			height = 4;
			break;
		default:
			return null;
		}
		
		if(!leftSide) {
			x-=16;
			y-=32;
			if(layers == layer.OVERLAY) {
				y+= 16;
			}
		} else if (layers == layer.OVERLAY) {
			x-=16;
		}
		
		image = skinFile.getSubimage(x, y, width, height);
		return image;
	}

	private BufferedImage getArm(facing side, layer layers, boolean leftSide) {
		BufferedImage image;
		boolean steve = skinType == skinConfig.STEVE;
		int x;
		int y;
		int width = (steve? 4 : 3);	 //width of a minecraft arm in pixels
		int height = 12;  //height of a minecraft arm in pixels;
		
		switch(side) {
		case FRONT:
			x = (steve? 36 : 35);
			y = 52;
			break;
		case BACK:
			x = (steve? 44 : 42);
			y = 52;
			break;
		case LEFT:
			x = (steve? 40 : 39);
			y = 52;
			width = 4;
			break;
		case RIGHT:
			x = 32;
			y = 52;
			width = 4;
			break;
		case TOP:
			x = 36;
			y = 48;
			height = 4;
			break;
		case BOTTOM:
			x = (steve? 40:39);
			y = 48;
			height = 4;
			break;
		default:
			return null;
		}
		
		if(!leftSide) {
			y-=32;
			x+=8;
			if(layers == layer.OVERLAY) {
				y+=16;
			}
		} else if (layers == layer.OVERLAY) {
			x+=16;
		}
		
		image = skinFile.getSubimage(x, y, width, height); //TODO fix this? getting out of bounds (raster) exception
		return image;
	}

	public ObjectGroup getModel(bodyPart limb, double scale, layer layers) {
		if(layers == layer.BOTH) {
			return getModel(limb, scale, layer.BASE).merge(getModel(limb, scale, layer.OVERLAY));
		} else if (layers == layer.OVERLAY) {
			scale*=1.125;
		}
		
		int xScale = get(limb, facing.TOP, layers).getWidth()/2;
		int yScale = get(limb, facing.LEFT, layers).getWidth()/2;
		int zScale = get(limb, facing.FRONT, layers).getHeight()/2;
		
		Tetrahedron front = ImageConverter.imageToTetrahedron(get(limb, facing.FRONT, layers), scale);
		Tetrahedron back = ImageConverter.imageToTetrahedron(get(limb, facing.BACK, layers), scale);
		Tetrahedron left = ImageConverter.imageToTetrahedron(get(limb, facing.LEFT, layers), scale);
		Tetrahedron right = ImageConverter.imageToTetrahedron(get(limb, facing.RIGHT, layers), scale);
		Tetrahedron top = ImageConverter.imageToTetrahedron(get(limb, facing.TOP, layers), scale);
		Tetrahedron bottom = ImageConverter.imageToTetrahedron(get(limb, facing.BOTTOM, layers), scale);
		
		front.rotate(true, 270, 0, 0);
		back.rotate(true, 90, 180, 0);
		left.rotate(true, 270, 0, 270);
		right.rotate(true, 270, 0, 90);
		top.rotate(true, 0, 180, 0);
		bottom.rotate(true, 0, 0, 0);
		
		front.setLocation(0, scale*yScale, 0);
		back.setLocation(0, -scale*yScale, 0);
		left.setLocation(scale*xScale, 0, 0);
		right.setLocation(-scale*xScale, 0, 0);
		top.setLocation(0, 0, scale*zScale);
		bottom.setLocation(0, 0, -scale*zScale);
		
		ObjectGroup output = new ObjectGroup(front, back, left, right, top, bottom);
		return output;
		
	}

	public ObjectGroup getFigure(double scale) {
		ObjectGroup head = getModel(bodyPart.HEAD, scale, layer.BOTH);
		ObjectGroup chest = getModel(bodyPart.CHEST, scale, layer.BOTH);
		ObjectGroup lArm = getModel(bodyPart.L_ARM, scale, layer.BOTH);
		ObjectGroup rArm = getModel(bodyPart.R_ARM, scale, layer.BOTH);
		ObjectGroup lLeg = getModel(bodyPart.L_LEG, scale, layer.BOTH);
		ObjectGroup rLeg = getModel(bodyPart.R_LEG, scale, layer.BOTH);
		
		//test
		scale*=4.1;
		
		//SET LOCATIONS
		head.setLocation(0*scale, 0*scale, 2.5*scale); 
		chest.setLocation(0*scale, 0*scale, 0*scale);
		lArm.setLocation(0.838776*scale, -1.5*scale, -0.350786*scale); 
		rArm.setLocation(0.838776*scale, 1.5*scale, -0.350786*scale);
		lLeg.setLocation(-1.14952*scale, 0.75*scale, -1.88823*scale);
		rLeg.setLocation(-1.14952*scale, -0.75*scale, -1.88823*scale);

		//SET ROTATIONS
		head.setRotation(0, 0, 0);
		chest.setRotation(0, 0, 0);
		lArm.setRotation(0, -40, 90);
		rArm.setRotation(0, -40, 90);
		lLeg.setRotation(0, -75, 105);
		rLeg.setRotation(0, -75, 75);
		
		ObjectGroup output = new ObjectGroup(rLeg, lLeg, rArm, lArm, chest, head);
		return output;
	}
	
}
