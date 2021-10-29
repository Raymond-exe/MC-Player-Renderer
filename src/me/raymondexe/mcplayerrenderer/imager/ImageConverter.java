package me.raymondexe.mcplayerrenderer.imager;

/** 
 * Handles importing and exporting images to the 3D space
 * @author https://github.com/Raymond-exe/
 * @version 1.1
 * @since 0.1
*/

import java.awt.Color;
import java.awt.image.BufferedImage;
//import java.io.File;
//import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.raymondexe.mcplayerrenderer.playerSkin.PlayerSkin;
import me.raymondexe.mcplayerrenderer.playerSkin.SkinPose;
import me.raymondexe.mcplayerrenderer.renderer.LightingControl;
import me.raymondexe.mcplayerrenderer.renderer.PointLight;
import me.raymondexe.mcplayerrenderer.renderer.point.Point3d;
import me.raymondexe.mcplayerrenderer.renderer.point.PointConverter;
import me.raymondexe.mcplayerrenderer.renderer.shapes.Polygon3d;
import me.raymondexe.mcplayerrenderer.renderer.shapes.Tetrahedron;

public class ImageConverter {

	public static boolean ATTEMPT_GARBAGE_COLLECTION = false; // Users can set to true if they want to trigger the garbage collection

	/**
	 * Outputs a render of the given PlayerSkin as a BufferedImage.
	 * @param playerSkin The PlayerSkin object to render.
	 * @param skinPose the SkinPose that the playerSkin should be rendered as (Null will force the player skin to stand).
	 * @param xRotation The rotation of the playerSkin along the x-axis.
	 * @param yRotation The rotation of the playerSkin along the y-axis.
	 * @param zRotation The rotation of the playerSkin along the z-axis.
	 * @param subdivisions How many times to subdivide the colors on the player's skin.
	 * @param width The width the exported BufferedImage should be.
	 * @param height The height the exported BufferedImage should be.
	 * @param background The background image for the playerSkin to be rendered over.
	 * @return A BufferedImage of the rendered playerSkin.
	 */
	public static BufferedImage renderSkin(PlayerSkin playerSkin, SkinPose skinPose, Float headPitch, Float headYaw, ArrayList<PointLight> lights, double xRotation, double yRotation, double zRotation, int subdivisions, int width, int height, BufferedImage background, double delay) {
		//Assign width and height to the PointConverter class
		PointConverter.WIDTH = width;
		PointConverter.HEIGHT = height;
		//PointConverter.FOV_SCALE = 300;
		//PointConverter.SCALE = 0.3;
		PointConverter.CAM_DISTANCE = 85000.0*Math.pow(PointConverter.SCALE, 0.25)/height-205;
		PointConverter.CAM_DISTANCE /= (250.0/PointConverter.FOV_SCALE); // wtf is this shit
		
		// Preparing image & graphics to draw on
		BufferedImage outputImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		
		// Draw the background image
		if(background != null)
			outputImage.getGraphics().drawImage(background, 0, 0, outputImage.getWidth(), outputImage.getHeight(), null);
		
		// Check to make sure the skinPose exists
		if(skinPose == null) {
			skinPose = SkinPose.standing();
		}
		
		// Preparing the player model
		Tetrahedron playerModel = playerSkin.getFigure(10, headPitch, headYaw, skinPose).mergeAll();
		playerModel.rotate(true, 0, 0, zRotation, LightingControl.lightVector);
		playerModel.rotate(true, 0, yRotation, 0, LightingControl.lightVector);
		playerModel.rotate(true, xRotation, 0, 0, LightingControl.lightVector);
		
		//draw on graphics object
		playerModel.renderLighting(outputImage.getGraphics(), subdivisions, lights);

		if(ATTEMPT_GARBAGE_COLLECTION) {
			//delete all the shit used to render this
			playerModel.delete();

			//TRY to make the garbage collector clean it up
			System.gc();
		}

		return outputImage; //return image
		
	}

	public static BufferedImage renderSkin(PlayerSkin playerSkin, SkinPose skinPose, Float headPitch, Float headYaw, ArrayList<PointLight> lights, double xRotation, double yRotation, double zRotation, int subdivisions, int width, int height, BufferedImage background) {
		return renderSkin(playerSkin, skinPose, headPitch, headYaw, lights, xRotation, yRotation, zRotation, subdivisions, width, height, background, 0);
	}

	public static BufferedImage renderSkin(PlayerSkin playerSkin, SkinPose skinPose, Float headPitch, Float headYaw, ArrayList<PointLight> lights, double xRotation, double yRotation, double zRotation, int width, int height, BufferedImage background) {
		return renderSkin(playerSkin, skinPose, headPitch, headYaw, lights, xRotation, yRotation, zRotation, 0, width, height, background);
	}
	
	public static BufferedImage renderSkin(PlayerSkin playerSkin, SkinPose skinPose, ArrayList<PointLight> lights, double xRotation, double yRotation, double zRotation, int width, int height, BufferedImage background) {
		return renderSkin(playerSkin, skinPose, null, null, lights, xRotation, yRotation, zRotation, width, height, background);
	}
	
	public static BufferedImage renderSkin(PlayerSkin playerSkin, SkinPose skinPose, double xRotation, double yRotation, double zRotation, int width, int height, BufferedImage background) {
		PointLight light = new PointLight(Point3d.createPoint(0,0,125), 10000, 5);
		ArrayList<PointLight> lights = new ArrayList<>();
		lights.add(light);
		return renderSkin(playerSkin, skinPose, lights, xRotation, yRotation, zRotation, width, height, background);
	}
	
	public static BufferedImage renderSkin(PlayerSkin playerSkin, SkinPose skinPose, ArrayList<PointLight> lights, double xRotation, double yRotation, double zRotation, int width, int height) {
		return renderSkin(playerSkin, skinPose, lights, xRotation, yRotation, zRotation, width, height, null);
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
	 * Applies a post-proccessing fix that should resolve
	 * any overlapping faces and better smooths shading.
	 * Note: currently works best with renders with a transparent background
	 * @return
	 */
	private static BufferedImage postProcessRender(Tetrahedron model, BufferedImage render) {
		
		// for every pixel...
		for(int y = 0; y < render.getHeight(); y++) {
			for(int x = 0; x < render.getWidth(); x++) {
				
				// if pixel is transparent, skip
				if(new Color(render.getRGB(x, y)).getAlpha() == 0) { continue; }

				// generate line in 3d space representing pixel's view

			}
		}

		return null; // TODO-RAY finish later lol
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
		
		/*
		Point3d[][] pointGrid = new Point3d[image.getHeight()+1][image.getWidth()+1];
		for(int y = 0; y < pointGrid.length; y++) {
			for(int x = 0; x < pointGrid[y].length; x++) {
				pointGrid[y][x] = new Point3d(startX+(x*scale), startY+(y*scale), 0);
			}
		} //*/
		
		Color color;
		//y must decrease to reach endY, but
		//x must increase to reach endX
		for(int y = 0; y < image.getHeight(); y++) {
			for(int x = 0; x < image.getWidth(); x++) {
				//gets the color of the current pixel
				color = new Color(image.getRGB(x, y), hasAlpha);
				
				/*
				if(color.getAlpha() == 0)
					continue; //*/
				
				//adds the pixel as a new polygon.
				//IMPORTANT: adds invis pixels, to balance out the average position
				polygons.add(
						new Polygon3d(color,
								Point3d.createPoint(startX+(x*scale), startY+(y*scale), 0),
								Point3d.createPoint(startX+((x+1)*scale), startY+(y*scale), 0),
								Point3d.createPoint(startX+((x+1)*scale), startY+((y+1)*scale), 0),
								Point3d.createPoint(startX+(x*scale), startY+((y+1)*scale), 0)
						)
				);
			}
		}
		
		return new Tetrahedron(polygons);
	}
	
	
	/*
	// main method to test stuff
	public static void main(String[] args) {
		try {
			for(int i = 500; i <= 5000; i+=500) {
				int width = i;
				int height = i;
				String imageName = "renders/img_" + width + "x" + height + ".png";
				
				BufferedImage img = renderSkin(new PlayerSkin("85f27c00d8a746e8bc9c68cd34b149a9"), SkinPose.sitting(), -15, 0, -30, width, height);
				
				ImageIO.write(img, "png", new File(imageName));
				
				System.out.println("Wrote image \"" + imageName + "\" successfully.");
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//*/
}
