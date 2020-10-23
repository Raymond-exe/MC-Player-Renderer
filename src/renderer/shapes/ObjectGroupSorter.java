package renderer.shapes;

import java.util.Comparator;

public class ObjectGroupSorter implements Comparator<ObjectGroup> {

	@Override
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
