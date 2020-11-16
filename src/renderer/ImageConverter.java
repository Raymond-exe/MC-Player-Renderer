package renderer;

/** 
 * Handles importing and exporting images to the 3D space
 * @author https://github.com/Raymond-exe/
 * @version 1.1
 * @since 0.1
*/

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import playerSkin.PlayerSkin;
import playerSkin.SkinPose;
import renderer.point.Point3d;
import renderer.point.PointConverter;
import renderer.shapes.Polygon3d;
import renderer.shapes.Tetrahedron;

public class ImageConverter {

	/**
	 * Outputs a render of the given PlayerSkin as a BufferedImage.
	 * @param playerSkin The PlayerSkin object to render.
	 * @param skinPose the SkinPose that the playerSkin should be rendered as (Null will force the player skin to stand).
	 * @param xRotation The rotation of the playerSkin along the x-axis.
	 * @param yRotation The rotation of the playerSkin along the y-axis.
	 * @param zRotation The rotation of the playerSkin along the z-axis.
	 * @param width The width the exported BufferedImage should be.
	 * @param height The height the exported BufferedImage should be.
	 * @param background The background image for the playerSkin to be rendered over.
	 * @return A BufferedImage of the rendered playerSkin.
	 */
	public static BufferedImage renderSkin(PlayerSkin playerSkin, SkinPose skinPose, double xRotation, double yRotation, double zRotation, int width, int height, BufferedImage background) {
		//Assign width and height to the PointConverter class
		PointConverter.WIDTH = width;
		PointConverter.HEIGHT = height;
		
		// Preparing image & graphics to draw on
		BufferedImage bImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		
		// Draw the background image
		if(background != null)
			bImage.getGraphics().drawImage(background, 0, 0, bImage.getWidth(), bImage.getHeight(), null);
		
		// Check to make sure the skinPose exists
		if(skinPose == null) {
			skinPose = SkinPose.standing();
		}
		
		// Preparing the player model
		Tetrahedron playerModel = playerSkin.getFigure(10, 1, skinPose).mergeAll();
		playerModel.rotate(true, xRotation, yRotation, zRotation);
		
		//draw on graphics object
		playerModel.render(bImage.getGraphics());
		
		//return image
		return bImage;
		
	}
	
	/**
	 * Outputs a render of the given PlayerSkin as a BufferedImage.
	 * @param playerSkin The PlayerSkin object to render.
	 * @param skinPose the SkinPose that the playerSkin should be rendered as (Null will force the player skin to stand).
	 * @param xRotation The rotation of the playerSkin along the x-axis.
	 * @param yRotation The rotation of the playerSkin along the y-axis.
	 * @param zRotation The rotation of the playerSkin along the z-axis.
	 * @param width The width the exported BufferedImage should be.
	 * @param height The height the exported BufferedImage should be.
	 * @return A BufferedImage of the rendered playerSkin.
	 */
	public static BufferedImage renderSkin(PlayerSkin playerSkin, SkinPose skinPose, double xRotation, double yRotation, double zRotation, int width, int height) {
		return renderSkin(playerSkin, skinPose, xRotation, yRotation, zRotation, width, height, null);
	}
	
	/**
	 * Outputs a render of the given PlayerSkin as a BufferedImage.
	 * @param playerSkin The PlayerSkin object to render.
	 * @param width The width the exported BufferedImage should be.
	 * @param height The height the exported BufferedImage should be.
	 * @return A BufferedImage of the rendered playerSkin.
	 */
	public static BufferedImage renderSkin(PlayerSkin playerSkin, int width, int height) {
		return renderSkin(playerSkin, null, 0, 0, 0, width, height, null);
	}
	
	/**
	 * Turns a given image into a Tetrahedron, each colored pixel being a single Polygon.
	 * @param image The image to convert into a Tetrahedron.
	 * @param scale The scale the exported Tetrahedron should be.
	 * @param hasAlpha Whether or not the image should have an alpha value.
	 * @return A Tetrahedron with each pixel being its own Polygon
	 */
	public static Tetrahedron imageToTetrahedron(BufferedImage image, double scale, boolean hasAlpha) {
		List<Polygon3d> polygons = new ArrayList<>();
		double startX = -image.getWidth()*scale/2;
		double startY = image.getHeight()*scale/2;
		
		//y must decrease to reach endY, but
		//x must increase to reach endX
		for(int y = 0; y < image.getHeight(); y++) {
			for(int x = 0; x < image.getWidth(); x++) {
				//gets the color of the current pixel
				Color color = new Color(image.getRGB(x, y), hasAlpha);
				
				//adds the pixel as a new polygon.
				//IMPORTANT: adds invis pixels, to balance out the average position
				polygons.add(
						new Polygon3d(color,
								new Point3d(startX+(x*scale), startY+(y*scale), 0),
								new Point3d(startX+((x+1)*scale), startY+(y*scale), 0),
								new Point3d(startX+((x+1)*scale), startY+((y+1)*scale), 0),
								new Point3d(startX+(x*scale), startY+((y+1)*scale), 0)
						)
				);
			}
		}
		
		return new Tetrahedron(polygons);		
	}
	
	
	// main method to test stuff
	public static void main(String[] args) {
		try {
			String imageName = "image.png";
			File outputfile = new File(imageName);
			BufferedImage packPng = ImageIO.read(new URL("https://packpng.com/static/pack.png"));
			
			PointConverter.FOV_SCALE = 200;
			PointConverter.CAM_DISTANCE = 100;
			
			BufferedImage img = renderSkin(new PlayerSkin("7db73360529c4728893540e62334226c"), SkinPose.sitting(), 0, 0, 0, 256, 256, packPng);
			
			ImageIO.write(img, "png", outputfile);
			
			System.out.println("Wrote image \"" + imageName + "\" successfully.");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
}
