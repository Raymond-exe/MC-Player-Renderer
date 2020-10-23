package renderer.shapes;

/** 
 * Sorter for Groupable objects
 * @author https://github.com/Raymond-exe/
 * @version 1.0
 * @since 0.1
*/

import java.util.Comparator;

public class GroupableSorter implements Comparator<Groupable> {

	@Override
	/**
	 * Compares the Y Coordinates of each Groupable (greater y values are closer to the camera)
	 * @param o1 A Groupable to compare to o2
	 * @param o2 A Groupable to be compared to o1
	 * @return 0, 1, or -1 depending of if o1 is closer
	 * 		  or farther from the camera than o2.
	 */
	public int compare(Groupable o1, Groupable o2) {
		if(o1.getYCoordinate() > o2.getYCoordinate()) 
			return 1;
		else if (o2.getYCoordinate() > o1.getYCoordinate())
			return -1;
		else
			return 0;
	}

}
