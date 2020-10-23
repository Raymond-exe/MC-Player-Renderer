package playerSkin;

/** 
 * Represents an player's skin.
 * @author https://github.com/Raymond-exe/
 * @version 1.0
 * @since 0.1
*/

import java.awt.image.BufferedImage;

import renderer.ImageConverter;
import renderer.shapes.ObjectGroup;
import renderer.shapes.Tetrahedron;

public class PlayerSkin {
	public static enum skinConfig {STEVE, ALEX};	//TODO implement skinConfig usage
	public static enum facing {FRONT, BACK, LEFT, RIGHT, TOP, BOTTOM};
	public static enum bodyPart {HEAD, CHEST, L_ARM, R_ARM, L_LEG, R_LEG};
	public static enum layer {BASE, OVERLAY, BOTH};
	
	private BufferedImage skinFile;
	private skinConfig skinType;
	
	/** 
	 * Creates a playerSkin object for the specified player
	 * @param uuid The player's 32-character unique identifier
	 */
	public PlayerSkin(String uuid) {
		this(uuid, skinConfig.STEVE);
	}
	
	/** 
	 * Creates a playerSkin object for the specified player
	 * @param uuid The player's 32-character unique identifier
	 * @param type The configuration of the skin (steve or alex)
	 */
	public PlayerSkin(String uuid, skinConfig type) {
		this.skinFile = PlayerSkinGrabber.getSkin(uuid);
		this.skinType = type;
	}
	
	/** 
	 * Creates a playerSkin object for the specified player
	 * @param skinFile an image of the pre-existing skin
	 */
	public PlayerSkin(BufferedImage skinFile) {
		this(skinFile, skinConfig.STEVE);
	}
	
	/** 
	 * Creates a playerSkin object for the specified player
	 * @param skinFile an image of the pre-existing skin
	 * @param type The configuration of the skin (steve or alex)
	 */		
	public PlayerSkin(BufferedImage skinFile, skinConfig type) {
		this.skinFile = skinFile;
		this.skinType = type;
	}
	
	/** 
	 * Gets a BufferedImage of the specified part of the players skin.
	 * @param limb Specifies which limb to focus in on.
	 * @param side Signifies which side of the specified limb should be returned.
	 * @param layers Signifies which layer to return. 
	 * 		  If the given layer is layer.BOTH, the base layer will be returned.
	 * @return The specified section of this player's skin.
	*/
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
	
	/** 
	 * Gets an image of the player's head.
	 * @param side Specifies which side to return
	 * @param layers Specifies which layer to return.
	 * 		  If the given layer is layer.BOTH, the base layer will be returned.
	 * @return An image of a given side of the player's head
	 */
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

	
	/** 
	 * Gets an image of the player's chest.
	 * @param side Specifies which side to return
	 * @param layers Specifies which layer to return.
	 * 		  If the given layer is layer.BOTH, the base layer will be returned.
	 * @return An image of a given side of the player's chest
	 */
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

	
	/** 
	 * Gets an image of the player's leg.
	 * @param side Specifies which side to return
	 * @param layers Specifies which layer to return.
	 * 		  If the given layer is layer.BOTH, the base layer will be returned.
	 * @param leftSide Whether or not to refer to the left leg. 
	 * 		  TRUE will return a specified image of the left leg.
	 * 		  FALSE will return a specified image of the right leg.
	 * @return An image of a given side of the player's leg.
	 */
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
	
	
	/** 
	 * Gets an image of the player's arm.
	 * @param side Specifies which side to return
	 * @param layers Specifies which layer to return.
	 * 		  If the given layer is layer.BOTH, the base layer will be returned.
	 * @param leftSide Whether or not to refer to the left arm. 
	 * 		  TRUE will return a specified image of the left arm.
	 * 		  FALSE will return a specified image of the right arm.
	 * @return An image of a given side of the player's arm.
	 */
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
		
		image = skinFile.getSubimage(x, y, width, height);
		return image;
	}
	
	
	/** 
	 * Gets a constructed model of a given bodypart (made from Tetrahedrons (renderer.shapes.Tetrahedron))
	 * @param limb Specifies which limb to get a model of
	 * @param scale Specifies the scale the Tetrahedron limb should be
	 * @param layers Specifies which layers should be present (layer.BASE, layer.OVERLAY, or layer.BOTH)
	 * @return An ObjectGroup of Tetrahedrons
	 */
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

	/** 
	 * Gets the player's skin as a full, 3d model made up of Tetrahedrons (renderer.shapes.Tetrahedron)
	 * @param scale Specifies the scale the Tetrahedron model should be
	 * @return An ObjectGroup made up of ObjectGroups of Tetrahedrons, making up a figure of the given player's skin
	 */
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
		
		//set identifiers
		head.identifier = "HEAD";
		chest.identifier = "CHEST";
		lArm.identifier = "LEFT_ARM";
		rArm.identifier = "RIGHT_ARM";
		lLeg.identifier = "LEFT_LEG";
		rLeg.identifier = "RIGHT_LEG";
		
		ObjectGroup output = new ObjectGroup(rLeg, lLeg, rArm, lArm, chest, head);
		output.identifier = "FIGURE";
		return output;
	}
	
}
