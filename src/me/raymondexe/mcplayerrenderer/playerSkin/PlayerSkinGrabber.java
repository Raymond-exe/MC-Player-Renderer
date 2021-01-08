package me.raymondexe.mcplayerrenderer.playerSkin;

/** 
 * static class used to retrieve a player's skin as a BufferedImage
 * @author https://github.com/Raymond-exe/
 * @version 1.1
 * @since 0.1
*/

import java.awt.image.BufferedImage;
import java.net.URL;
//import java.util.UUID;

import javax.imageio.ImageIO;

import me.raymondexe.mcplayerrenderer.playerSkin.PlayerSkin.SkinConfig;

public class PlayerSkinGrabber {

	private static final String SKIN_URL_PREFIX = "https://crafatar.com/skins/";	//append a UUID to the end of this link to get the player's skin file.
	private static final String CAPE_URL_PREFIX = "https://crafatar.com/capes/";	//append a UUID to the end of this link to get the player's cape file.
	
	private static final String DEFAULT_SKIN_STEVE = "https://cdn.discordapp.com/attachments/774125506210693121/780156711281098762/default_steve.png";
	private static final String DEFAULT_SKIN_ALEX  = "https://cdn.discordapp.com/attachments/774125506210693121/780156699133870090/default_alex.png";
	
	
	/**
	 * Retrieves a player's skin given their UUID
	 * @param uuid A player's 32-character unique identifier
	 * @return the current skin of the player affiliated with the UUID,
	 * 		   or the ALEX/STEVE default skin if the UUID is invalid.
	 */
	public static BufferedImage getSkin(String uuid) {
		BufferedImage img = retrieveImage(SKIN_URL_PREFIX + uuid);
		
		if(img == null) {
			//img = getDefaultSkin(getSkinConfig(uuid));
			img = getDefaultSkin(SkinConfig.STEVE); //TODO finish the getSkinConfig() method then remove this line
		}
		
		return img;
	}
	
	/**
	 * Retrieves a player's cape given their UUID
	 * @param uuid A player's 32-character unique identifier
	 * @return the current cape of the player affiliated with the UUID,
	 * 		   or null if the player doesn't have a cape OR the UUID is invalid.
	 */
	public static BufferedImage getCape(String uuid) {
		return retrieveImage(CAPE_URL_PREFIX + uuid);
	}
	
	/**
	 * Retrieves either an ALEX or STEVE default skin.
	 * @param skinType Specifies either the STEVE or ALEX skin.
	 * @return A STEVE or ALEX default skin.
	 */	
	public static BufferedImage getDefaultSkin(SkinConfig skinType) {
		switch (skinType) {
			case STEVE:
				return retrieveImage(DEFAULT_SKIN_STEVE);
			case ALEX:
				return retrieveImage(DEFAULT_SKIN_ALEX);
			default:
				return null;
		}
	}
	
	private static BufferedImage retrieveImage(String urlAddress) {
		try {
			URL url = new URL(urlAddress);	  //makes a new URL object
			BufferedImage imgOut = ImageIO.read(url); //reads the image at the given URL
			return imgOut;							  //returns the image
		} catch (Exception e) {
			return null;							  //if the URL did not exist or
		}											  //give an image, return null
	}

}