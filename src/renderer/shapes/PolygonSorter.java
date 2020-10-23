package renderer.shapes;

import java.util.Comparator;

public class PolygonSorter implements Comparator<Polygon3d> {

	@Override
	public int compare(Polygon3d o1, Polygon3d o2) {
		if(!(o1 instanceof Polygon3d && o2 instanceof Polygon3d))
		return 0;
		
		Polygon3d poly1 = (Polygon3d)o1;		
		Polygon3d poly2 = (Polygon3d)o2;
		
		if(poly1.getYAverage() > poly2.getYAverage()) 
			return 1;
		else if (poly2.getYAverage() > poly1.getYAverage())
			return -1;
		else
			return 0;
	}

}
