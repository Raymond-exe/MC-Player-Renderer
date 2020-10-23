package renderer;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import renderer.point.Point3d;
import renderer.shapes.Polygon3d;
import renderer.shapes.Tetrahedron;

public class ImageConverter {

	public static BufferedImage render(Graphics g, int width, int height) {
		//TODO figure out how to convert a graphics obj to a BufferedImage
		return null;
	}
	
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
