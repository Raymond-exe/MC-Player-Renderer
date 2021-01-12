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
		if(o1.getYAverage()>o2.getYAverage())
			return 1;
		else if (o2.getYAverage()>o1.getYAverage())
			return -1;
		return 0;
	}
	
	/*
	private boolean isBetween(double comparison, double lowerLimit, double upperLimit, boolean inclusive) {
		if(inclusive)
			return (comparison >= lowerLimit && comparison <= upperLimit);
		return (comparison > lowerLimit && comparison < upperLimit);
	} //*/

}
