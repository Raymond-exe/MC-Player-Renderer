package renderer;

import java.awt.image.BufferedImage;

import playerSkin.PlayerSkin;
import playerSkin.SkinPose;
import renderer.shapes.Tetrahedron;

/** 
 * Represents a Camera looking in 3D space
 * @author https://github.com/Raymond-exe/
 * @version 0.0
 * @since 0.1
*/

public class PlayerRenderer {
	
	PlayerSkin playerFigure;
	
	public PlayerRenderer(String playerUUID) {
		PlayerSkin playerFigure = new PlayerSkin(playerUUID);
	}
	
	public BufferedImage render(SkinPose pose) {
		//Tetrahedron = playerFigure.getFigure(scale);
		return null;
	}
	
}
