package renderer.shapes;

/** 
 * Sorter for the Tetrahedron class
 * @author https://github.com/Raymond-exe/
 * @version 1.0
 * @since 0.1
*/

import java.util.Comparator;

public class TetrahedronSorter implements Comparator<Tetrahedron> {

	@Override
	/**
	 * Compares two Tetrahedron objects
	 * @param o1 a Tetrahedron to compare to o2
	 * @param o2 a Tetrahedron to be compared to o1
	 * @return 0, 1, or -1 depending on if o1 is closer 
	 * 		   or farther from the camera than o2.
	 */
	public int compare(Tetrahedron o1, Tetrahedron o2) {
		Tetrahedron tetra1 = (Tetrahedron)o1;		
		Tetrahedron tetra2 = (Tetrahedron)o2;
		
		if(tetra1.getYCoordinate() > tetra2.getYCoordinate()) 
			return 1;
		else if (tetra2.getYCoordinate() > tetra1.getYCoordinate())
			return -1;
		else
			return 0;
	}
	
}
