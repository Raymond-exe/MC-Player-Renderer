package me.raymondexe.mcplayerrenderer.renderer.shapes;

/** 
 * Sorter for the Polygon class.
 * @author https://github.com/Raymond-exe/
 * @version 1.0
 * @since 0.1
*/

import java.util.Comparator;

public class PolygonSorter implements Comparator<Polygon3d> {

	@Override
	/**
	 * Compares two Polygon3d objects.
	 * @param o1 a Polygon3d to compare to o2.
	 * @param o2 a Polygon3d to be compared to o1.
	 * @return 0, 1, or -1 depending on if o1 is closer 
	 * 		   or farther from the camera than o2.
	 */
	public int compare(Polygon3d o1, Polygon3d o2) {
		
		if((isBetween(o1.getYMin(), o2.getYMin(), o2.getYMax())
				|| isBetween(o1.getYMax(), o2.getYMin(), o2.getYMax()))
				&&(isBetween(o1.getXMin(), o2.getXMin(), o2.getXMax())
				|| isBetween(o1.getXMax(), o2.getXMin(), o2.getXMax())
				|| isBetween(o1.getZMin(), o2.getZMin(), o2.getZMax())
				|| isBetween(o1.getZMax(), o2.getZMin(), o2.getZMax()))) {
			
		}
		return (int)Math.round((o1.getYAverage()-o2.getYAverage())*1000000);
	}
	
	private boolean isBetween(double comparison, double lowerLimit, double upperLimit) {
		return isBetween(comparison, lowerLimit, upperLimit, true);
	}
	
	private boolean isBetween(double comparison, double lowerLimit, double upperLimit, boolean inclusive) {
		if(inclusive)
			return (comparison >= lowerLimit && comparison <= upperLimit);
		return (comparison > lowerLimit && comparison < upperLimit);
	}

}
