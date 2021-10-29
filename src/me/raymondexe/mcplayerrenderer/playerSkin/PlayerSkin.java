package me.raymondexe.mcplayerrenderer.playerSkin;

/** 
 * Represents an player's skin.
 * @author https://github.com/Raymond-exe/
 * @version 1.0
 * @since 0.1
*/

import java.awt.image.BufferedImage;

import me.raymondexe.mcplayerrenderer.imager.ImageConverter;
import me.raymondexe.mcplayerrenderer.renderer.shapes.ObjectGroup;
import me.raymondexe.mcplayerrenderer.renderer.shapes.Tetrahedron;

public class PlayerSkin {
	public static enum SkinConfig {STEVE, ALEX};
	public static enum Facing {FRONT, BACK, LEFT, RIGHT, TOP, BOTTOM};
	public static enum BodyPart {HEAD, CHEST, L_ARM, R_ARM, L_LEG, R_LEG};
	public static enum Layer {BASE, OVERLAY, BOTH};
	
	private BufferedImage skinFile;
	private SkinConfig skinType;
	
	private static final double DEFAULT_OVERLAY_SCALE = 1.125;
	
	/** 
	 * Creates a playerSkin object for the specified player
	 * @param uuid The player's 32-character unique identifier
	 */
	public PlayerSkin(String uuid) {
		this.skinFile = PlayerSkinGrabber.getSkin(uuid);
		// Look into using Mojang API for that
		/*
		if(string .contains("\"model\": \"slim\""))
			skinType = SkinConfig.ALEX;
		else */
			skinType = SkinConfig.STEVE;
	}
	
	
	/** 
	 * Creates a playerSkin object for the specified player
	 * @param uuid The player's 32-character unique identifier
	 * @param type The configuration of the skin (steve or alex)
	 */
	public PlayerSkin(String uuid, SkinConfig type) {
		this.skinFile = PlayerSkinGrabber.getSkin(uuid);
		this.skinType = type;
		
		if(this.skinFile==null)
			System.out.println("Player " + uuid + " not found!");
	}
	
	
	/** 
	 * Creates a playerSkin object for the specified player
	 * @param skinFile an image of the pre-existing skin
	 */
	public PlayerSkin(BufferedImage skinFile) {
		this(skinFile, SkinConfig.STEVE);
	}
	
	
	/** 
	 * Creates a playerSkin object for the specified player
	 * @param skinFile an image of the pre-existing skin
	 * @param type The configuration of the skin (steve or alex)
	 */		
	public PlayerSkin(BufferedImage skinFile, SkinConfig type) {
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
	public BufferedImage get(BodyPart limb, Facing side, Layer layers) {
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
	private BufferedImage getHead(Facing side, Layer layers) {
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
		
		if(layers == Layer.OVERLAY) {
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
	private BufferedImage getChest(Facing side, Layer layers) {
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
		
		if(layers == Layer.OVERLAY) {
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
	private BufferedImage getLeg(Facing side, Layer layers, boolean leftSide) {
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
		
		if(leftSide) {
			x-=16;
			y-=32;
			if(layers == Layer.OVERLAY) {
				y+= 16;
			}
		} else if (layers == Layer.OVERLAY) {
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
	private BufferedImage getArm(Facing side, Layer layers, boolean leftSide) {
		BufferedImage image;
		boolean steve = skinType == SkinConfig.STEVE;
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
			if(layers == Layer.OVERLAY) {
				y+=16;
			}
		} else if (layers == Layer.OVERLAY) {
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
	public ObjectGroup getModel(BodyPart limb, double scale, Layer layers, double overlayScale) {
		boolean hasAlpha = false;
		if(layers == Layer.BOTH) {
			return getModel(limb, scale, Layer.BASE, overlayScale).merge(getModel(limb, scale, Layer.OVERLAY, overlayScale));
		} else if (layers == Layer.OVERLAY) {
			hasAlpha = true;
			scale*=overlayScale; // default value 1.125
		}
		
		double xScale = get(limb, Facing.FRONT, layers).getWidth()/2.0;
		double yScale = get(limb, Facing.TOP, layers).getHeight()/2.0;
		double zScale = get(limb, Facing.FRONT, layers).getHeight()/2.0;
		
		Tetrahedron front =  ImageConverter.imageToTetrahedron(get(limb, Facing.FRONT, 	layers), scale, hasAlpha);
		Tetrahedron back  =  ImageConverter.imageToTetrahedron(get(limb, Facing.BACK, 	layers), scale, hasAlpha);
		Tetrahedron left  =	 ImageConverter.imageToTetrahedron(get(limb, Facing.LEFT, 	layers), scale, hasAlpha);
		Tetrahedron right =  ImageConverter.imageToTetrahedron(get(limb, Facing.RIGHT, 	layers), scale, hasAlpha);
		Tetrahedron top   =	 ImageConverter.imageToTetrahedron(get(limb, Facing.TOP, 	layers), scale, hasAlpha);
		Tetrahedron bottom = ImageConverter.imageToTetrahedron(get(limb, Facing.BOTTOM, layers), scale, hasAlpha);
		
		front.rotate(true, 270, 0, 0);
		back.rotate(true, 90, 180, 0);
		left.rotate(true, 270, 0, 270);
		right.rotate(true, 270, 0, 90);
		top.rotate(true, 0, 0, 0);
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
	 * @param overlayScale The scale of the overlay relative to the base layer. Set to 1 to disable.
	 * @return An ObjectGroup made up of ObjectGroups of Tetrahedrons, making up a figure of the given player's skin
	 */
	public ObjectGroup getFigure(double scale, double overlayScale, Float headPitch, Float headYaw, SkinPose skinPose) {
		Layer layer = Layer.BOTH; //for debug use
		
		if(skinPose == null)
			skinPose = SkinPose.standing();
		
		double[][][] pose = skinPose.getValues();
		
		ObjectGroup head = getModel(BodyPart.HEAD,  scale, layer, overlayScale);
		ObjectGroup chest= getModel(BodyPart.CHEST, scale, layer, overlayScale);
		ObjectGroup lArm = getModel(BodyPart.L_ARM, scale, layer, overlayScale);
		ObjectGroup rArm = getModel(BodyPart.R_ARM, scale, layer, overlayScale);
		ObjectGroup lLeg = getModel(BodyPart.L_LEG, scale, layer, overlayScale);
		ObjectGroup rLeg = getModel(BodyPart.R_LEG, scale, layer, overlayScale);
		
		//LEAVE THIS HERE
		scale*=4;

		//SET ROTATIONS
		head.setRotation(pose[SkinPose.HEAD][SkinPose.ROTATION][0], pose[SkinPose.HEAD][SkinPose.ROTATION][1], pose[SkinPose.HEAD][SkinPose.ROTATION][2]);
		chest.setRotation(pose[SkinPose.CHEST][SkinPose.ROTATION][0], pose[SkinPose.CHEST][SkinPose.ROTATION][1], pose[SkinPose.CHEST][SkinPose.ROTATION][2]);
		lArm.setRotation(pose[SkinPose.LEFT_ARM][SkinPose.ROTATION][0], pose[SkinPose.LEFT_ARM][SkinPose.ROTATION][1], pose[SkinPose.LEFT_ARM][SkinPose.ROTATION][2]);
		rArm.setRotation(pose[SkinPose.RIGHT_ARM][SkinPose.ROTATION][0], pose[SkinPose.RIGHT_ARM][SkinPose.ROTATION][1], pose[SkinPose.RIGHT_ARM][SkinPose.ROTATION][2]);
		lLeg.setRotation(pose[SkinPose.LEFT_LEG][SkinPose.ROTATION][0], pose[SkinPose.LEFT_LEG][SkinPose.ROTATION][1], pose[SkinPose.LEFT_LEG][SkinPose.ROTATION][2]);
		rLeg.setRotation(pose[SkinPose.RIGHT_LEG][SkinPose.ROTATION][0], pose[SkinPose.RIGHT_LEG][SkinPose.ROTATION][1], pose[SkinPose.RIGHT_LEG][SkinPose.ROTATION][2]);
		
		
		//SET LOCATIONS
		head.setLocation(pose[SkinPose.HEAD][SkinPose.LOCATION][0]*scale, pose[SkinPose.HEAD][SkinPose.LOCATION][1]*scale, pose[SkinPose.HEAD][SkinPose.LOCATION][2]*scale); 
		chest.setLocation(pose[SkinPose.CHEST][SkinPose.LOCATION][0]*scale, pose[SkinPose.CHEST][SkinPose.LOCATION][1]*scale, pose[SkinPose.CHEST][SkinPose.LOCATION][2]*scale);
		lArm.setLocation(pose[SkinPose.LEFT_ARM][SkinPose.LOCATION][0]*scale, pose[SkinPose.LEFT_ARM][SkinPose.LOCATION][1]*scale, pose[SkinPose.LEFT_ARM][SkinPose.LOCATION][2]*scale); 
		rArm.setLocation(pose[SkinPose.RIGHT_ARM][SkinPose.LOCATION][0]*scale, pose[SkinPose.RIGHT_ARM][SkinPose.LOCATION][1]*scale, pose[SkinPose.RIGHT_ARM][SkinPose.LOCATION][2]*scale);
		lLeg.setLocation(pose[SkinPose.LEFT_LEG][SkinPose.LOCATION][0]*scale, pose[SkinPose.LEFT_LEG][SkinPose.LOCATION][1]*scale, pose[SkinPose.LEFT_LEG][SkinPose.LOCATION][2]*scale);
		rLeg.setLocation(pose[SkinPose.RIGHT_LEG][SkinPose.LOCATION][0]*scale, pose[SkinPose.RIGHT_LEG][SkinPose.LOCATION][1]*scale, pose[SkinPose.RIGHT_LEG][SkinPose.LOCATION][2]*scale);

		//Adjust head pitch & yaw ONLY IF values present
		if(headPitch != null || headYaw != null) {
			head.setLocation(0, 0, scale/2);
			head.setRotation((headPitch==null? 0 : headPitch), 0, 0);
			head.rotate(true, 0, 0, (headYaw==null? 0 : headYaw));
			head.setLocation(pose[SkinPose.HEAD][SkinPose.LOCATION][0]*scale, pose[SkinPose.HEAD][SkinPose.LOCATION][1]*scale, pose[SkinPose.HEAD][SkinPose.LOCATION][2]*scale);
		}
		
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

	public ObjectGroup getFigure(double scale, Float headPitch, Float headYaw, SkinPose skinPose) {
		return getFigure(scale, DEFAULT_OVERLAY_SCALE, headPitch, headYaw, skinPose);
	}
	
	public ObjectGroup getFigure(double scale, double overlayScale, SkinPose skinPose) {
		return getFigure(scale, overlayScale, null, null, skinPose);
	}
	
	/** 
	 * Gets the player's skin as a full, 3d model made up of Tetrahedrons (renderer.shapes.Tetrahedron)
	 * @param scale Specifies the scale the Tetrahedron model should be.
	 * @param pose The SkinPose the returned figure should take.
	 * @return An ObjectGroup made up of ObjectGroups of Tetrahedrons, making up a figure of the given player's skin.
	 */
	public ObjectGroup getFigure(double scale, SkinPose pose) {
		return getFigure(scale, DEFAULT_OVERLAY_SCALE, pose);
	}
	
	/** 
	 * Gets the player's skin as a full, 3d model made up of Tetrahedrons (renderer.shapes.Tetrahedron)
	 * @param scale Specifies the scale the Tetrahedron model should be.
	 * @return An ObjectGroup made up of ObjectGroups of Tetrahedrons, making up a figure of the given player's skin.
	 */
	public ObjectGroup getFigure(double scale) {
		return getFigure(scale, DEFAULT_OVERLAY_SCALE, null);
	}
	
}
