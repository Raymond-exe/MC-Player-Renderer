package playerSkin;

import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;

public class PlayerSkinGrabber {

	private static String URL_PREFIX = "https://crafatar.com/skins/"; 
	//append a UUID to the end of this to get the player's skin file
	
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