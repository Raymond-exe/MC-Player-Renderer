package renderer.shapes;

import java.util.Comparator;

public class GroupableSorter implements Comparator<Groupable> {

	@Override
	public int compare(Groupable o1, Groupable o2) {
		if(o1.getYCoordinate() > o2.getYCoordinate()) 
			return 1;
		else if (o2.getYCoordinate() > o1.getYCoordinate())
			return -1;
		else
			return 0;
	}

}
