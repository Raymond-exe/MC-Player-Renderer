package renderer;

/** 
 * Handles importing and exporting images to the 3D space
 * @author https://github.com/Raymond-exe/
 * @version 1.0
 * @since 0.1
*/

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import renderer.point.Point3d;
import renderer.shapes.Polygon3d;
import renderer.shapes.Tetrahedron;

public class ImageConverter {

	/**
	 * Outputs a BufferedImage of the given graphics view.
	 * @param g The graphics object to export to a BufferedImage.
	 * @param width The width the exported BufferedImage should be.
	 * @param height The height the exported BufferedImage should be.
	 * @return A BufferedImage of the graphics drawn in g.
	 */
	public static BufferedImage render(Graphics2D g, int width, int height) {
		//TODO figure out how to convert a graphics obj to a BufferedImage
		return null;
	}
	
	/**
	 * Turns a given image into a Tetrahedron, each colored pixel being a single Polygon.
	 * @param image The image to convert into a Tetrahedron.
	 * @param scale The scale the exported Tetrahedron should be
	 * @return A Tetrahedron with each pixel being its own Polygon
	 */
	public static Tetrahedron imageToTetrahedron(BufferedImage image, double scale) {
		List<Polygon3d> polygons = new ArrayList<>();
		
		boolean hasAlpha = image.getColorModel().hasAlpha();
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
	
}
