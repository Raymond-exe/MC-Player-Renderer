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
		ObjectGroup obj1 = (ObjectGroup)o1;		
		ObjectGroup obj2 = (ObjectGroup)o2;
		
		//System.out.println("Comparing " + obj1.getGlobalYCoordinate() + " vs. " + obj2.getGlobalYCoordinate());
		//*
		if(obj1.getGlobalYCoordinate() > obj2.getGlobalYCoordinate()) 
			return 1;
		else if (obj2.getGlobalYCoordinate() > obj1.getGlobalYCoordinate())
			return -1;
		else
			return 0;
			 //*/
	}

}
