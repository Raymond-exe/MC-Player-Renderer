package playerSkin;

/** 
 * static class used to retrieve a player's skin as a BufferedImage
 * @author https://github.com/Raymond-exe/
 * @version 1.0
 * @since 0.1
*/

import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;

public class PlayerSkinGrabber {

	private static String URL_PREFIX = "https://crafatar.com/skins/"; 
	//append a UUID to the end of this to get the player's skin file

	/**
	 * Retrieves a player's skin given their UUID
	 * @param uuid A player's 32-character unique identifier
	 * @return the current skin of the player affiliated with the UUID,
	 * 		   or null if the UUID is invalid.
	 */
	public static BufferedImage getSkin(String uuid) {
		try {
			URL url = new URL(URL_PREFIX + uuid);	  //makes a new URL object
			BufferedImage imgOut = ImageIO.read(url); //reads the image at the given URL
			return imgOut;							  //returns the image
		} catch (Exception e) {
			return null;							  //if the URL did not exist or
		}											  //give an image, return null
	}

}