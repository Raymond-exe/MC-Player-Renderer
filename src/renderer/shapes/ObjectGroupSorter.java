package renderer.shapes;

/** 
 * Sorter for the ObjectGroup class
 * @author https://github.com/Raymond-exe/
 * @version 1.0
 * @since 0.1
*/

import java.util.Comparator;

public class ObjectGroupSorter implements Comparator<ObjectGroup> {

	@Override
	/**
	 * Compares two ObjectGroups.
	 * @param o1 an ObjectGroup to compare to o2.
	 * @param o2 an ObjectGroup to be compared to o1.
	 * @return 0, 1, or -1 depending on if o1 is closer
	 * 		   or farther from the camera than o2.
	 */
	public int compare(ObjectGroup o1, ObjectGroup o2) {
		int output = 0;
		
		if(o1.getGlobalYCoordinate() > o2.getGlobalYCoordinate()) 
			output = 1;
		else if (o2.getGlobalYCoordinate() > o1.getGlobalYCoordinate())
			output = -1;
		
		return output;
	}

}
